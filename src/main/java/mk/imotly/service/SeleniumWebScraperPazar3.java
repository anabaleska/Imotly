package mk.imotly.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;

public class SeleniumWebScraper {

    public static void scrapePazar3() {
        // Setup ChromeDriver using WebDriverManager and specify the ChromeDriver version explicitly
        WebDriverManager.chromedriver().driverVersion("135.0.7049.85").setup();  // Specify the version of ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Pazar3.mk website
            driver.get("https://www.pazar3.mk/oglasi/zivealista/prodazba-kupuvanje-izdavanje-baram-za-iznajmuvanje-zamena");

            // Wait for the page to load (can add explicit waits here)
            Thread.sleep(5000); // Wait for 5 seconds (to give the page time to load)

            int pageCount = 1;
            while (pageCount <= 5) {

            // Locate the elements containing the home listings
            List<WebElement> listings = driver.findElements(By.className("row-listing"));  // Replace with actual class name

            // Loop through the listings and extract details
            for (WebElement listing : listings) {
                String title = listing.findElement(By.className("Link_vis")).getText();  // Replace with actual class name
                String price = listing.findElement(By.className("list-price")).getText();   // Replace with actual class name
                String location = listing.findElement(By.className("link-html")).getText(); // Replace with actual class name

                System.out.println("Title: " + title);
                System.out.println("Price: " + price);
                System.out.println("Location: " + location);
                System.out.println("--------------------------");
            }
                try {
                    WebElement cookiesBanner = driver.findElement(By.cssSelector(".cookies-area.active"));
                    if (cookiesBanner.isDisplayed()) {
                        WebElement closeButton = cookiesBanner.findElement(By.cssSelector("button.close-cookies"));  // Assuming the close button has class "close"
                        closeButton.click();
                        System.out.println("Closing cookies banner...");
                    }
                } catch (Exception e) {
                    // If no cookie banner is found, ignore it
                    System.out.println("No cookie banner found.");
                }
                List<WebElement> pageNumbers = driver.findElements(By.cssSelector(".pagination ul li.prevnext a"));
                if (pageNumbers.size() > 0) {
                    // Try to find the next page number (assumes pages are numbered sequentially)
                    //int gotopage=pageNumbers.size()>=12 ? 11:12;
                    WebElement nextPage = pageNumbers.get(0);  // Click on the (currentPage + 1) page number

                    // Ensure the next page number is clickable
                   // wait.until(ExpectedConditions.elementToBeClickable(nextPage));

                    nextPage.click();
                    System.out.println("Going to page " + (pageCount + 1));

                    // Increment the page count
                    pageCount++;
                } else {
                    // If no page numbers are found, break out of the loop (end of pages)
                    System.out.println("No more pages to scrape.");
                    break;
                }

                // Wait for the next page to load
                Thread.sleep(5000);  // Or you can adjust this according to the page load time
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    public static void main(String[] args) {
        // Run the scraper
        scrapePazar3();
    }
}
