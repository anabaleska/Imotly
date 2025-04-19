package mk.imotly.repository;
import mk.imotly.model.Ad;
import mk.imotly.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Repository
public class SupabaseClient {

    private final RestTemplate restTemplate;

    @Value("${supabase.api.url}")
    private String supabaseApiUrl;

    @Value("${supabase.api.key}")
    private String supabaseApiKey;

    public SupabaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseApiKey);
        headers.set("Authorization", "Bearer " + supabaseApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public List<Ad> getAds() {
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<List<Ad>> response = restTemplate.exchange(
                supabaseApiUrl + "/ads",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Ad>>() {}
        );

        return response.getBody();
    }

    public void addAd(Ad ad) {

        HttpEntity<Ad> request = new HttpEntity<>(ad, createHeaders());
        ResponseEntity<String> response = restTemplate.postForEntity(
                supabaseApiUrl + "/ads",
                request,
                String.class
        );


        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Ad successfully added to database.");
        } else {
            System.out.println("Failed to add ad: " + response.getBody());
        }
    }


    public Ad getAdById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<List<Ad>> response = restTemplate.exchange(
                supabaseApiUrl + "/ads?id=eq." + id,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Ad>>() {}
        );

        List<Ad> results = response.getBody();
        return (results != null && !results.isEmpty()) ? results.get(0) : null;
    }

    public Ad getAdByUrl(String url) {
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        String fullUrl = supabaseApiUrl + "/ads?url=eq." + url;
        System.out.println("Requesting Supabase with URL: " + fullUrl);

        ResponseEntity<List<Ad>> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Ad>>() {}
        );

        List<Ad> results = response.getBody();
        System.out.println("Supabase returned: " + results);
        return (results != null && !results.isEmpty()) ? results.get(0) : null;
    }

    public void addUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user, createHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity(
                supabaseApiUrl + "/users", // your users table
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
            System.out.println("User successfully added to database.");
        } else {
            System.out.println("Failed to add user: " + response.getBody());
        }
    }



}
