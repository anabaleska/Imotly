package mk.imotly.service;

import mk.imotly.service.selenium.SeleniumWebScraperPazar3;
import mk.imotly.service.selenium.SeleniumWebScraperReklama5;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

    private final SeleniumWebScraperReklama5 scraperReklama5;
    private final SeleniumWebScraperPazar3 scraperPazar3;

    public ScraperService(SupabaseService supabaseService) {
        this.scraperReklama5 = new SeleniumWebScraperReklama5(supabaseService);
        this.scraperPazar3 = new SeleniumWebScraperPazar3(supabaseService);
    }


    @Scheduled(fixedRate = 300000)
    public void runScrapers() {
        System.out.println("Starting scraper 1...");
       // scraperReklama5.scrapeReklama5();
        System.out.println("Scraper 1 completed.");

        System.out.println("Starting scraper 2...");
        scraperPazar3.scrapePazar3();
        System.out.println("Scraper 2 completed.");
    }
}
