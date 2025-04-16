package mk.imotly.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class SeleniumWebScraperReklama5 {
    public static void scrapeReklama5() {
        WebDriverManager.chromedriver().driverVersion("135.0.7049.85").setup();
        WebDriver driver = new ChromeDriver();

        try {

            String baseUrl = "https://www.reklama5.mk/Search?city=&cat=157&q=";
            driver.get(baseUrl);

            Thread.sleep(5000);

            int pageCount = 1;
            while (pageCount <= 5) {

                List<WebElement> listings = driver.findElements(By.className("ad-top-div"));

                for (WebElement listing : listings) {
                    String title = listing.findElement(By.className("SearchAdTitle")).getText();
                    String price = listing.findElement(By.className("search-ad-price")).getText();
                    String location = listing.findElement(By.className("city-span")).getText();
                    String link = listing.findElement(By.className("SearchAdTitle")).getAttribute("href");
                    System.out.println("Title: " + title);
                    System.out.println("Price: " + price);
                    System.out.println("Location: " + location);
                    System.out.println("Link: " + link);
                    System.out.println("--------------------------");
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
        scrapeReklama5();
    }
}
