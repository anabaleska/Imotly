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


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeleniumWebScraperPazar3 {


    private Optional<Integer> extractIntFromText(String text) {
        Matcher matcher = Pattern.compile("(\\d+)").matcher(text);
        return matcher.find() ? Optional.of(Integer.parseInt(matcher.group(1))) : Optional.empty();
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String[] parts = datePosted.split(" ");
        datePosted= parts[0] + " " + parts[1]+" "+LocalDate.now().getYear();

        try {
            return LocalDate.parse(datePosted, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + datePosted);
            return null;
        } }

    public void scrapePazar3() {
        boolean checkOnlyFirstPage = false;
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

                for (WebElement listing : listings) {

                    String title = listing.findElement(By.className("Link_vis")).getText();
                    String price = listing.findElement(By.className("list-price")).getText();
                    String location = listing.findElement(By.className("link-html")).getText();
                    String link = listing.findElement(By.className("Link_vis")).getAttribute("href");
                    String datePosted = listing.findElement(By.className("ci-text-right")).getText();
                    LocalDate date;
                    if(datePosted.toLowerCase().contains("денес")) date=LocalDate.now();
                    else if (datePosted.toLowerCase().contains("вчера")) date=LocalDate.now().minusDays(1);
                    else date=parseDate(datePosted);

                    List<WebElement> images = listing.findElements(By.className("ProductionImg"));
                    String imageUrl = images.isEmpty() ? "No image available" : images.get(0).getAttribute("data-src");
                    String typeOfObj = listing.findElement(By.className("link-html5")).getText().equalsIgnoreCase("станови") ? "Стан" : "Куќа/Вила";
                    Integer numRooms = null;
                    Integer floor = null;
                    Integer numFloors = null;
                    Integer size = null;
                    String heating = null;
                    String state = null;

                    Boolean forSale=null;
                    Boolean terrace = null;
                    Boolean lift = null;
                    Boolean parking=null;
                    Boolean furnished=null;
                    Boolean basement=null;
                    Boolean newBuilding=null;
                    Boolean duplex=null;
                    Boolean renovated=null;

                    if (link != null && !link.isEmpty()) {
                        driver.get(link);
                        Thread.sleep(1000);
                        List<WebElement> details= driver.findElements(By.className("tag-item"));
                        for (WebElement detail : details) {
                            String label = detail.findElement(By.tagName("span")).getText().toLowerCase();
                            String value = detail.findElement(By.tagName("bdi")) != null ? detail.findElement(By.tagName("bdi")).getText().toLowerCase() : "";
                            if (label.contains("број на соби:")) {
                                numRooms = extractIntFromText(value).get();
                            } else if (label.contains("површина:")) {
                                size = extractIntFromText(value).get();
                            }  else if (label.contains("греење:")) {
                                heating = value;
                            } else if (label.contains("состојба:")) {
                                state = value;
                            } else if (label.contains("спрат:")) {
                                floor = extractIntFromText(value).get();
                            }else if (label.contains("број на спратови:")) {
                                numFloors = extractIntFromText(value).get();
                            }
                            else if (label.contains("локација:")) {
                                location=value;
                            }
                            else if (label.contains("вид на оглас:")) {
                                forSale= value.contains("се продава");
                            }
                            else if(label.contains("за живеалиштето:")) {
                                if(value.contains("паркинг простор")) {
                                    parking=true;
                                }
                                else if(value.contains("нова градба")) {
                                    newBuilding=true;
                                }
                                else if(value.contains("реновиран")) {
                                    renovated=true;
                                }
                                else if(value.contains("наместен")) {
                                    furnished=true;
                                }
                                else if(value.contains("балкон")) {
                                    terrace=true;
                                }
                                else if(value.contains("лифт")) {
                                    lift=true;
                                }
                                else if(value.contains("подрум")) {
                                    basement=true;
                                }
                                else if(value.contains("дуплекс")) {
                                    duplex=true;
                                }

                            }


                        }
                        }
                    Ad existingAd = supabaseService.getAdByUrl(link);
                    if (existingAd != null) {
                        System.out.println("Ad already exists: " + title);
                    } else {
                        try {
                           Ad ad = new Ad(title,price, location,  date, link, "Pazar3.mk", imageUrl, numRooms,  floor,  numFloors, size,  heating, typeOfObj, state,  forSale,  terrace, parking, furnished,  basement, newBuilding, duplex,  renovated,  lift);
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
                    driver.get(pageUrl);
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
