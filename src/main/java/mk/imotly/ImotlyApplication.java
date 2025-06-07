package mk.imotly;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableScheduling
public class ImotlyApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("SUPABASE_URL", dotenv.get("SUPABASE_URL"));
        System.setProperty("SUPABASE_API_URL", dotenv.get("SUPABASE_API_URL"));
        System.setProperty("SUPABASE_KEY", dotenv.get("SUPABASE_KEY"));
        System.setProperty("APP_PASSWORD", dotenv.get("APP_PASSWORD"));
        System.out.println("DB URL: " + System.getenv("DB_URL"));
        SpringApplication.run(ImotlyApplication.class, args);
    }
}
