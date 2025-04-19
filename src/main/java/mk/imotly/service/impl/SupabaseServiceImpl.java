package mk.imotly.service.impl;

import mk.imotly.model.User;
import org.springframework.beans.factory.annotation.Value;
import mk.imotly.model.Ad;
import mk.imotly.repository.SupabaseClient;
import mk.imotly.service.SupabaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class SupabaseServiceImpl implements SupabaseService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String supabaseApiKey;

    private final RestTemplate restTemplate;

    private final SupabaseClient supabaseClient;

    public SupabaseServiceImpl(RestTemplate restTemplate, SupabaseClient supabaseClient) {
        this.restTemplate = restTemplate;
        this.supabaseClient = supabaseClient;
    }

    @Override
    public List<Ad> getAllAds() {
        return supabaseClient.getAds();
    }

    @Override
    public void addAd(Ad ad) {
        supabaseClient.addAd(ad);
    }

    @Override
    public Ad getAdById(Long id) {
        return supabaseClient.getAdById(id);
    }

    @Override
    public Ad getAdByUrl(String url) {
        return supabaseClient.getAdByUrl(url);
    }

    @Override
    public String registerUser(String email, String password, String name, String surname) {
        String signUpUrl = supabaseUrl + "/auth/v1/signup";

        String signUpBody = "{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }";

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseApiKey);
        headers.set("Authorization", "Bearer " + supabaseApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> signUpEntity = new HttpEntity<>(signUpBody, headers);
        ResponseEntity<String> signUpResponse = restTemplate.exchange(signUpUrl, HttpMethod.POST, signUpEntity, String.class);

        if (!signUpResponse.getStatusCode().is2xxSuccessful()) {
            return "Error registering user: " + signUpResponse.getBody();
        }



        // Save user to users table
        User user = new User(email, name, surname);
        supabaseClient.addUser(user); // <-- assuming SupabaseClient is injected

        return "User registered and saved successfully.";
    }

}
