package mk.imotly.service.impl;

import mk.imotly.model.User;
import org.springframework.beans.factory.annotation.Value;
import mk.imotly.model.Ad;
import mk.imotly.repository.SupabaseClient;
import mk.imotly.service.SupabaseService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupabaseServiceImpl implements SupabaseService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String supabaseApiKey;
    @Value("${supabase.api.url}")
    private String supabaseApiUrl;

    private final RestTemplate restTemplate;

    private final SupabaseClient supabaseClient;

    public SupabaseServiceImpl(RestTemplate restTemplate, SupabaseClient supabaseClient) {
        this.restTemplate = restTemplate;
        this.supabaseClient = supabaseClient;
    }

    @Override
    public List<Ad> getAllAds(int page, int size) {
        return supabaseClient.getAds(page, size);
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

        User user = new User(email, name, surname);
        supabaseClient.addUser(user);

        return "User registered and saved successfully.";
    }

    @Override
    public List<Ad> searchAds(int page, int size,Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize, Boolean lift, Boolean basement,
                              String title, String location, Integer numRooms, Integer floor, Integer numFloors,
                              String heating, String typeOfObj, String state, Boolean terrace, Boolean parking, Boolean furnished,
                              Boolean newBuilding, Boolean duplex, Boolean renovated) {
        return supabaseClient.searchAds(page, size,
                forSale, minPrice, maxPrice, minSize, maxSize, lift, basement, title, location, numRooms, floor, numFloors,
                heating, typeOfObj, state, terrace, parking, furnished, newBuilding, duplex, renovated);
    }


}
