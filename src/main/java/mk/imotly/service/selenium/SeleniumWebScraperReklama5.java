package mk.imotly.service.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import mk.imotly.config.SpringConfig;
import mk.imotly.model.Ad;
import mk.imotly.service.SupabaseService;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class SeleniumWebScraperReklama5 {
    private final SupabaseService supabaseService;
    public SeleniumWebScraperReklama5(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    private static LocalDate parseDate(String datePosted) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
        try {
            return LocalDate.parse(datePosted, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + datePosted);
            return null;
        }
    }

    private Optional<Integer> extractIntFromText(String text) {
        Matcher matcher = Pattern.compile("(\\d{1,3}(?:\\.\\d{3})*|\\d+)").matcher(text);
        if (matcher.find()) {
            String matchedNumber = matcher.group(1).replace(".", "");
            try {
                return Optional.of(Integer.parseInt(matchedNumber));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }


    public void scrapeReklama5() {
        boolean checkOnlyFirstPage = true;
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
                List<String> links = new ArrayList<>();
                for (WebElement listing : listings) {
                    try {
                        String link = listing.findElement(By.className("SearchAdTitle")).getAttribute("href");
                        if (link != null && !link.isEmpty()) {
                            links.add(link);
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale element while collecting links. Skipping...");
                    }
                }

                for (String link : links) {
                    Integer numRooms = null;
                    Integer floor = null;
                    Integer numFloors = null;
                    Integer size = null;
                    String heating = null;
                    String state = null;

                    Boolean forSale = false;
                    Boolean terrace = null;
                    Boolean lift = null;
                    Boolean parking = null;
                    Boolean furnished = null;
                    Boolean basement = null;
                    Boolean newBuilding = null;
                    Boolean duplex = null;
                    Boolean renovated = null;
                    driver.get(link);
                    Thread.sleep(3000);

                    String title = driver.findElement(By.className("card-title")).getText();
                    List<WebElement> priceElements = driver.findElements(By.className("defaultBlue"));
                    String priceText = null;
                    Integer price=null;
                    if (!priceElements.isEmpty()) {
                        priceText = priceElements.get(0).getText();
                        if(priceText.equals("По Договор"))
                            price=0;
                        else
                            price=extractIntFromText(priceText).get();
                        if(priceText.toLowerCase().contains("mkd")||priceText.toLowerCase().contains("мкд")||priceText.toLowerCase().contains("ден")) {
                            price/=61;
                        }

                    } else {
                        System.out.println("Price not found for this ad.");
                    }

                    WebElement dateElement = driver.findElement(By.xpath("//div[@class='col-4 align-self-center']//div[@style='float:left']//small"));
                    String datePosted = dateElement.getText().split(" ")[0];
                    WebElement cityElement = driver.findElement(By.xpath("(//div[@class='col-4 align-self-center'])[3]//p"));
                    String location = cityElement.getText();
                    LocalDate date;
                    if (datePosted.toLowerCase().contains("денес")) date = LocalDate.now();
                    else if (datePosted.toLowerCase().contains("вчера")) date = LocalDate.now().minusDays(1);
                    else date = parseDate(datePosted);
                    String typeOfObj = driver.findElement(By.id("categoryDiv")).getText().toLowerCase().contains("станови") ? "Стан" : "Куќа/Вила";
                    List<WebElement> imageElements = driver.findElements(By.cssSelector(".ad-image img"));
                    String imageUrl;
                    if(imageElements.isEmpty()){
                        imageUrl = "https://img.freepik.com/premium-vector/no-photo-available-vector-icon-default-image-symbol-picture-coming-soon-web-site-mobile-app_87543-18055.jpg";
                    } else {
                     imageUrl = imageElements.get(0).getAttribute("src");}


                    WebElement card = driver.findElement(By.className("card"));
                    List<WebElement> rowContainers = card.findElements(By.cssSelector(".row.mt-3"));

                    if (rowContainers.isEmpty()) {
                        continue;
                    } else {
                        WebElement rowContainer = rowContainers.get(0);
                        List<WebElement> allCols = rowContainer.findElements(By.xpath("./div"));

                        for (int i = 0; i < allCols.size() - 1; i += 2) {
                            WebElement labelElement = allCols.get(i);
                            WebElement valueElement = allCols.get(i + 1);

                            String label = labelElement.getText().toLowerCase();
                            String value = valueElement.getText().toLowerCase();

                            if (label.contains("број на соби:")) {
                                numRooms = extractIntFromText(value).orElse(null);
                            } else if (label.contains("квадратура:")) {
                                size = extractIntFromText(value).orElse(null);
                            } else if (label.contains("греење:")) {
                                heating = value;
                            } else if (label.contains("состојба:")) {
                                state = value;
                            } else if (label.contains("спрат:")) {
                                floor = extractIntFromText(value).orElse(null);
                            } else if (label.contains("број на спратови:")) {
                                numFloors = extractIntFromText(value).orElse(null);
                            } else if (label.contains("вид на оглас")) {
                                forSale = value.contains("се продава");
                            } else if (label.contains("број на паркинг/гаража:"))
                                parking = !value.equals("нема");
                            else if (label.contains("опрема:"))
                                furnished = value.contains("наместен");
                            else if (label.contains("број на балкони:"))
                                terrace = !value.equals("нема");
                            else if (label.contains("лифт:"))
                                lift = !value.contains("не");
                            else if (label.contains("подрум:"))
                                basement = value.contains("да");
                            if (value.contains("дуплекс")) duplex = true;
                        }
                    }

                    Ad existingAd = supabaseService.getAdByUrl(link);
                    if (existingAd != null) {
                        System.out.println("Ad already exists: " + title);
                    } else {
                        Ad ad = new Ad(title, price, location, date, link, "Reklama5.mk", imageUrl, numRooms, floor, numFloors, size, heating, typeOfObj, state, forSale, terrace, parking, furnished, basement, newBuilding, duplex, renovated, lift);
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

                if (checkOnlyFirstPage) {
                    System.out.println("Checking only the first page. Stopping here.");
                    break;
                }
                driver.get(pageUrl);
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
