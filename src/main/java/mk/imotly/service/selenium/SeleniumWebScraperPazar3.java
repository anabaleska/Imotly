package mk.imotly.service.selenium;

import mk.imotly.config.SpringConfig;
import mk.imotly.model.Ad;
import mk.imotly.service.SupabaseService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.List;

@Component
public class SeleniumWebScraperPazar3 {

    private final SupabaseService supabaseService;
    private static boolean CHECK_ONLY_FIRST_PAGE = true;

    @Autowired
    public SeleniumWebScraperPazar3(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    public void scrapePazar3() {
        WebDriverManager.chromedriver().driverVersion("135.0.7049.85").setup();
        WebDriver driver = new ChromeDriver();

        try {
            int pageCount = 1;
            String baseUrl = "https://www.pazar3.mk/oglasi/zivealista/prodazba-kupuvanje-izdavanje-baram-za-iznajmuvanje-zamena?Page=";
            while (pageCount <= 20) {
                //https://www.pazar3.mk/oglasi/zivealista/prodazba-kupuvanje-izdavanje-baram-za-iznajmuvanje-zamena?Page=2
                String pageUrl = baseUrl + pageCount;
                driver.get(pageUrl);
                Thread.sleep(5000);

                List<WebElement> listings = driver.findElements(By.className("row-listing"));

                for (WebElement listing : listings) {
                    String title = listing.findElement(By.className("Link_vis")).getText();
                    String price = listing.findElement(By.className("list-price")).getText();
                    String location = listing.findElement(By.className("link-html")).getText();
                    String link = listing.findElement(By.className("Link_vis")).getAttribute("href");
                    String datePosted = listing.findElement(By.className("ci-text-right")).getText();
                    List<WebElement> images = listing.findElements(By.className("ProductionImg"));
                    String imageUrl = images.isEmpty() ? "No image available" : images.get(0).getAttribute("data-src");

                    Ad existingAd = supabaseService.getAdByUrl(link);
                    if (existingAd != null) {
                        System.out.println("Ad already exists: " + title);
                    } else {
                        Ad ad = new Ad(title, price, location, datePosted, link, "Pazar3.mk", imageUrl);
                        supabaseService.addAd(ad);
                        System.out.println("Ad successfully saved to database: " + title);
                    }
                }

                try {
                    WebElement cookiesBanner = driver.findElement(By.cssSelector(".cookies-area.active"));
                    if (cookiesBanner.isDisplayed()) {
                        WebElement closeButton = cookiesBanner.findElement(By.cssSelector("button.close-cookies"));
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

                List<WebElement> pageNumbers = driver.findElements(By.cssSelector(".pagination ul li.prevnext a"));
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
        SeleniumWebScraperPazar3 scraper = context.getBean(SeleniumWebScraperPazar3.class);
        scraper.scrapePazar3();
    }
}
