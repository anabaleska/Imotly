package mk.imotly.service.selenium;

import mk.imotly.config.SpringConfig;
import mk.imotly.model.Ad;
import mk.imotly.service.SupabaseService;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeleniumWebScraperPazar3 {


    public static Optional<Integer> extractIntFromText(String text) {
        String cleanedText = text.replaceAll("(\\d)\\s+(\\d)", "$1$2");

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cleanedText);

        if (matcher.find()) {
            try {
                return Optional.of(Integer.parseInt(matcher.group()));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }


    private final SupabaseService supabaseService;

    @Autowired
    public SeleniumWebScraperPazar3(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    private static LocalDate parseDate(String datePosted) {
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("јан.", "Jan");
        monthMap.put("феб.", "Feb");
        monthMap.put("мар.", "Mar");
        monthMap.put("апр.", "Apr");
        monthMap.put("мај", "May");
        monthMap.put("јуни", "Jun");
        monthMap.put("јули", "Jul");
        monthMap.put("авг.", "Aug");
        monthMap.put("септ.", "Sep");
        monthMap.put("окт.", "Oct");
        monthMap.put("ноем.", "Nov");
        monthMap.put("дек.", "Dec");

        for (Map.Entry<String, String> entry : monthMap.entrySet()) {
            if (datePosted.toLowerCase().contains(entry.getKey())) {
                datePosted = datePosted.replace(entry.getKey(), entry.getValue());
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

        try {
            return LocalDate.parse(datePosted, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + datePosted);
            return null;
        } }

    public void scrapePazar3() {
        boolean checkOnlyFirstPage = true;
        WebDriverManager.chromedriver().driverVersion("135.0.7049.85").setup();
        WebDriver driver = new ChromeDriver();

        try {
            int pageCount = 1;
            String baseUrl = "https://www.pazar3.mk/oglasi/zivealista/prodazba-kupuvanje-izdavanje-baram-za-iznajmuvanje-zamena?Page=";
            while (pageCount <= 20) {
                String pageUrl = baseUrl + pageCount;
                driver.get(pageUrl);
                Thread.sleep(5000);

                List<WebElement> listings = driver.findElements(By.className("row-listing"));
                List<String> links = new ArrayList<>();
                for (WebElement listing : listings) {
                    try {
                        String link = listing.findElement(By.className("Link_vis")).getAttribute("href");
                        if (link != null && !link.isEmpty()) {
                            links.add(link);
                        }
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale element while collecting links. Skipping...");
                    }
                }

                for (String link : links) {
                    String location = null;
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

                    String title = driver.findElement(By.tagName("h1")).getText();
                    List<WebElement> priceElements = driver.findElements(By.cssSelector(".new-price"));
                    String priceText = null;
                    Integer price=null;
                    if (!priceElements.isEmpty()) {
                        priceText = priceElements.get(0).getText();
                        price=extractIntFromText(priceText).get();
                        if(priceText.toLowerCase().contains("mkd")||priceText.toLowerCase().contains("мкд")||priceText.toLowerCase().contains("ден")) {
                        price/=61;
                        }

                    } else {
                        System.out.println("Price not found for this ad.");
                    }


                    List<WebElement> dateElements = driver.findElements(By.className("published-date"));
                    LocalDate date = null;

                    if (!dateElements.isEmpty()) {
                        String datePosted = dateElements.get(0).getText();
                        if (datePosted.toLowerCase().contains("денес")) {
                            date = LocalDate.now();
                        } else if (datePosted.toLowerCase().contains("вчера")) {
                            date = LocalDate.now().minusDays(1);
                        } else {
                            date = parseDate(datePosted);
                        }
                    } else {
                        System.out.println("No published-date found for ad: " + link);
                        continue;
                    }
                    String typeOfObj = driver.findElement(By.className("breadcrumbs")).getText().toLowerCase().contains("станови") ? "Стан" : "Куќа/Вила";
                    List<WebElement> imageElements = driver.findElements(By.className("custom-photo-link"));
                    String imageUrl;
                    if(imageElements.isEmpty()){
                        imageUrl = "https://img.freepik.com/premium-vector/no-photo-available-vector-icon-default-image-symbol-picture-coming-soon-web-site-mobile-app_87543-18055.jpg";
                    } else {
                        imageUrl = imageElements.get(0).getAttribute("href");
                    }


                    List<WebElement> details = driver.findElements(By.className("tag-item"));
                    for (WebElement detail : details) {
                        String label = detail.findElement(By.tagName("span")).getText().toLowerCase();
                        String value = detail.findElement(By.tagName("bdi")) != null ? detail.findElement(By.tagName("bdi")).getText().toLowerCase() : "";
                        if (label.contains("број на соби:")) {
                            numRooms = extractIntFromText(value).get();
                        } else if (label.contains("површина:")) {
                            size = extractIntFromText(value).get();
                        } else if (label.contains("греење:")) {
                            heating = value;
                        } else if (label.contains("состојба:")) {
                            state = value;
                        } else if (label.contains("спрат:")) {
                            floor = extractIntFromText(value).get();
                        } else if (label.contains("број на спратови:")) {
                            numFloors = extractIntFromText(value).isPresent()? extractIntFromText(value).get():null;
                        } else if (label.contains("локација:")) {
                            location = value;
                        } else if (label.contains("вид на оглас:")) {
                            forSale = value.contains("се продава");
                        } else if (label.contains("за живеалиштето:")) {
                            if (value.contains("паркинг простор")) {
                                parking = true;
                            } else if (value.contains("нова градба")) {
                                newBuilding = true;
                            } else if (value.contains("реновиран")) {
                                renovated = true;
                            } else if (value.contains("наместен")) {
                                furnished = true;
                            } else if (value.contains("балкон")) {
                                terrace = true;
                            } else if (value.contains("лифт")) {
                                lift = true;
                            } else if (value.contains("подрум")) {
                                basement = true;
                            } else if (value.contains("дуплекс")) {
                                duplex = true;
                            }

                        }


                    }

                Ad existingAd = supabaseService.getAdByUrl(link);
                if (existingAd != null) {
                    System.out.println("Ad already exists: " + title);
                } else {
                    try {
                        Ad ad = new Ad(title, price, location, date, link, "Pazar3.mk", imageUrl, numRooms, floor, numFloors, size, heating, typeOfObj, state, forSale, terrace, parking, furnished, basement, newBuilding, duplex, renovated, lift);
                        supabaseService.addAd(ad);
                        System.out.println("Ad successfully saved: " + title);
                    } catch (Exception e) {
                        if (e.getMessage().contains("409")) {
                            System.out.println("Conflict (409): Ad already exists in the database: " + title);
                        } else {
                            System.out.println("Failed to save ad: " + title);
                            e.printStackTrace();
                        }
                    }
                }

                Thread.sleep(5000);
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

                if (checkOnlyFirstPage) {
                    System.out.println("Checking only the first page. Stopping here.");
                    break;
                }

                driver.get(pageUrl);
                List<WebElement> pageNumbers = driver.findElements(By.cssSelector(".pagination ul li.prevnext a"));
                if (pageNumbers.size() > 0) {
                    WebElement nextPage = pageNumbers.get(0);
                    nextPage.click();
                    System.out.println("Going to page " + (pageCount + 1));
                    pageCount++;
                } else {
                    System.out.println("No more pages to scrape.");
                    checkOnlyFirstPage=true;
                    break;
                }

                Thread.sleep(5000);
            }
        } catch (InterruptedException | NoSuchElementException e ) {
            e.printStackTrace();
        } finally
        {
            driver.quit();
        }
    }



    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SeleniumWebScraperPazar3 scraper = context.getBean(SeleniumWebScraperPazar3.class);
        scraper.scrapePazar3();
    }
}
