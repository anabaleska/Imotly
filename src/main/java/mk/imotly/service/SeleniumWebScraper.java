package mk.imotly.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

public class SeleniumWebScraper {

    public static void scrapePazar3() {
        // Setup ChromeDriver using WebDriverManager and specify the ChromeDriver version explicitly
        WebDriverManager.chromedriver().driverVersion("134.0.6998.166").setup();  // Specify the version of ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open the Pazar3.mk website
            driver.get("https://www.pazar3.mk/oglasi/zivealista/prodazba-kupuvanje-izdavanje-baram-za-iznajmuvanje-zamena");

            // Wait for the page to load (can add explicit waits here)
            Thread.sleep(5000); // Wait for 5 seconds (to give the page time to load)

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
