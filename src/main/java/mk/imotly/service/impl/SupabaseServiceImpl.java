package mk.imotly.service.impl;

import mk.imotly.model.SavedSearch;
import mk.imotly.model.User;
import org.springframework.beans.factory.annotation.Value;
import mk.imotly.model.Ad;
import mk.imotly.repository.SupabaseClient;
import mk.imotly.service.SupabaseService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    @Value("${supabase.api.url}")
    private String supabaseApiUrl;

    private final RestTemplate restTemplate;

    private final SupabaseClient supabaseClient;
    private final JavaMailSender mailSender;
    public SupabaseServiceImpl(RestTemplate restTemplate, SupabaseClient supabaseClient, JavaMailSender mailSender) {
        this.restTemplate = restTemplate;
        this.supabaseClient = supabaseClient;
        this.mailSender = mailSender;
    }

    private void sendEmailNotification(String toEmail, Ad ad) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New Ad Matching Your Preferences");
        message.setText("A new ad that matches your preferences has been added: \n\n" + ad.toString() + "\n\nCheck it out!");

        mailSender.send(message);
    }

    @Override
    public List<Ad> getAllAds(int page, int size) {
        return supabaseClient.getAds(page, size);
    }

    @Override
    public void addAd(Ad ad) {
        supabaseClient.addAd(ad);
        List<SavedSearch> subscriptions = getAllSubscriptions();

        for (SavedSearch sub : subscriptions) {
            Long userId = sub.getUserId();
            String userEmail = getUserEmailById(userId);

            if (userEmail != null && doesAdMatchSubscription(ad, sub)) {
                sendEmailNotification(userEmail, ad);
            }
        }
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

    @Override
    public boolean saveSubscription(SavedSearch savedSearch) {
        return supabaseClient.saveSubscription(savedSearch);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return supabaseClient.getUserIdByEmail(email);
    }
    @Override
    public String getUserEmailById(Long userId) {
        return supabaseClient.getUserEmailById(userId);
    }

    @Override
    public List<SavedSearch> getAllSubscriptions() {
        return supabaseClient.getAllSubscriptions();
    }

    private boolean doesAdMatchSubscription(Ad ad, SavedSearch savedSearch) {

        if (savedSearch.getForSale() != null && !savedSearch.getForSale().equals(ad.getForSale())) {
            return false;
        }
        if (savedSearch.getMinPrice() != null && ad.getPrice() < savedSearch.getMinPrice()) {
            return false;
        }
        if (savedSearch.getMaxPrice() != null && ad.getPrice() > savedSearch.getMaxPrice()) {
            return false;
        }
        if (savedSearch.getLocation() != null && !savedSearch.getLocation().equalsIgnoreCase(ad.getLocation())) {
            return false;
        }
        if (savedSearch.getNumRooms() != null && !savedSearch.getNumRooms().equals(ad.getNumRooms())) {
            return false;
        }
        if (savedSearch.getFloor() != null && !savedSearch.getFloor().equals(ad.getFloor())) {
            return false;
        }
        if (savedSearch.getNumFloors() != null && !savedSearch.getNumFloors().equals(ad.getNumFloors())) {
            return false;
        }
        if (savedSearch.getSize() != null && !savedSearch.getSize().equals(ad.getSize())) {
            return false;
        }
        if (savedSearch.getHeating() != null && !savedSearch.getHeating().equalsIgnoreCase(ad.getHeating())) {
            return false;
        }
        if (savedSearch.getTypeOfObj() != null && !savedSearch.getTypeOfObj().equalsIgnoreCase(ad.getTypeOfObj())) {
            return false;
        }
        if (savedSearch.getTerrace() != null && !savedSearch.getTerrace().equals(ad.getTerrace())) {
            return false;
        }
        if (savedSearch.getParking() != null && !savedSearch.getParking().equals(ad.getParking())) {
            return false;
        }
        if (savedSearch.getFurnished() != null && !savedSearch.getFurnished().equals(ad.getFurnished())) {
            return false;
        }
        if (savedSearch.getBasement() != null && !savedSearch.getBasement().equals(ad.getBasement())) {
            return false;
        }
        if (savedSearch.getNewBuilding() != null && !savedSearch.getNewBuilding().equals(ad.getNewBuilding())) {
            return false;
        }
        if (savedSearch.getDuplex() != null && !savedSearch.getDuplex().equals(ad.getDuplex())) {
            return false;
        }
        if (savedSearch.getRenovated() != null && !savedSearch.getRenovated().equals(ad.getRenovated())) {
            return false;
        }
        if (savedSearch.getLift() != null && !savedSearch.getLift().equals(ad.getLift())) {
            return false;
        }
        if (savedSearch.getState() != null && !savedSearch.getState().equalsIgnoreCase(ad.getState())) {
            return false;
        }
        return true;
    }



}
