package mk.imotly.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class SeleniumWebScraperPazar3 {

    public static void scrapePazar3() {
        WebDriverManager.chromedriver().driverVersion("135.0.7049.85").setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.pazar3.mk/oglasi/zivealista/prodazba-kupuvanje-izdavanje-baram-za-iznajmuvanje-zamena");

            Thread.sleep(5000);

            int pageCount = 1;
            while (pageCount <= 5) {

            List<WebElement> listings = driver.findElements(By.className("row-listing"));

            for (WebElement listing : listings) {
                String title = listing.findElement(By.className("Link_vis")).getText();
                String price = listing.findElement(By.className("list-price")).getText();
                String location = listing.findElement(By.className("link-html")).getText();
                String link = listing.findElement(By.className("Link_vis")).getAttribute("href");
                System.out.println("Title: " + title);
                System.out.println("Price: " + price);
                System.out.println("Location: " + location);
                System.out.println("Link: " + link);

                System.out.println("--------------------------");
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
        scrapePazar3();
    }
}
