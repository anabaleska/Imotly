package mk.imotly.service.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import mk.imotly.config.SpringConfig;
import mk.imotly.model.Ad;
import mk.imotly.service.SupabaseService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class SeleniumWebScraperReklama5 {
    private final SupabaseService supabaseService;
    private static boolean CHECK_ONLY_FIRST_PAGE = true;

    public SeleniumWebScraperReklama5(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }
    public void scrapeReklama5() {
        WebDriverManager.chromedriver().driverVersion("135.0.7049.85").setup();
        WebDriver driver = new ChromeDriver();

        try {

            String baseUrl = "https://www.reklama5.mk/Search?city=&cat=157&q=";

            Thread.sleep(5000);

            int pageCount = 1;
            while (pageCount <= 20) {
                String pageUrl = baseUrl + "&page=" + pageCount;
                driver.get(pageUrl);
                List<WebElement> listings = driver.findElements(By.className("ad-top-div"));

                for (WebElement listing : listings) {
                    String title = listing.findElement(By.className("SearchAdTitle")).getText();
                    String price = listing.findElement(By.className("search-ad-price")).getText();
                    String location = listing.findElement(By.className("city-span")).getText();
                    String link = listing.findElement(By.className("SearchAdTitle")).getAttribute("href");
                    WebElement dateDiv = listing.findElement(By.className("ad-date-div-1"));
                    String datePosted = "непознато";


                    List<WebElement> spanElements = dateDiv.findElements(By.tagName("span"));
                    if (!spanElements.isEmpty()) {
                        datePosted = spanElements.get(0).getText().trim();
                    } else {
                        String fullText = dateDiv.getText().trim();
                        if (!fullText.isEmpty()) {
                            String[] lines = fullText.split("\n");
                            if (lines.length > 0) {
                                datePosted = lines[0].trim();
                            }
                        }
                    }
                    WebElement imageDiv = listing.findElement(By.className("ad-image"));
                    String styleAttr = imageDiv.getAttribute("style");

                    String imageUrl = "";

                    Pattern pattern = Pattern.compile("url\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(styleAttr);
                    if (matcher.find()) {
                        imageUrl = matcher.group(1).replaceAll("\"", ""); // трга наводници
                        if (imageUrl.startsWith("//")) {
                            imageUrl = "https:" + imageUrl;
                        }
                    }
                    Ad existingAd = supabaseService.getAdByUrl(link);
                    if (existingAd != null) {
                        System.out.println("Ad already exists: " + title);
                    } else {
                        Ad ad = new Ad(title, price, location, datePosted, link, "Reklama5", imageUrl);
                        supabaseService.addAd(ad);
                        System.out.println("Ad successfully saved to database: " + title);
                    }
                }
                try {
                    WebElement cookiesBanner = driver.findElement(By.cssSelector("#cookiePopup"));
                    if (cookiesBanner.isDisplayed()) {
                        WebElement closeButton = cookiesBanner.findElement(By.className("btn-primary"));
                        closeButton.click();
                        System.out.println("Closing cookies banner...");
                    }
                } catch (Exception e) {
                    System.out.println("No cookie banner found.");
                }

                if (CHECK_ONLY_FIRST_PAGE) {
                    System.out.println("Checking only the first page. Stopping here.");
                    CHECK_ONLY_FIRST_PAGE = false;
                    break;
                }

                List<WebElement> pageNumbers = driver.findElements(By.className("page-link"));
                if (pageNumbers.size() > 0) {

                    WebElement nextPage = pageNumbers.get(0);
                    nextPage.click();
                    System.out.println("Going to page " + (pageCount + 1));

                    pageCount++;
                } else {
                    System.out.println("No more pages to scrape.");
                    break;
                }


                Thread.sleep(5000);
          }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SeleniumWebScraperReklama5 scraper = context.getBean(SeleniumWebScraperReklama5.class);
        scraper.scrapeReklama5();
    }
}
