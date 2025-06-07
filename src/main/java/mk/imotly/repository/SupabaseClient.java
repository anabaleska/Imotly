package mk.imotly.repository;
import mk.imotly.model.Ad;
import mk.imotly.model.SavedSearch;
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

    public long countAds(Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize,
                         Boolean lift, Boolean basement, String title, String location, Integer numRooms, Integer floor,
                         Integer numFloors, String heating, String typeOfObj, String state, Boolean terrace, Boolean parking,
                         Boolean furnished, Boolean newBuilding, Boolean duplex, Boolean renovated) {

        StringBuilder urlBuilder = new StringBuilder(supabaseApiUrl + "/ads?select=id&");

        if (forSale != null) urlBuilder.append("for_sale=eq.").append(forSale).append("&");
        if (minPrice != null) urlBuilder.append("price=gte.").append(minPrice).append("&");
        if (maxPrice != null) urlBuilder.append("price=lte.").append(maxPrice).append("&");
        if (minSize != null) urlBuilder.append("size=gte.").append(minSize).append("&");
        if (maxSize != null) urlBuilder.append("size=lte.").append(maxSize).append("&");
        if (lift != null) urlBuilder.append("lift=is.").append(lift).append("&");
        if (basement != null) urlBuilder.append("basement=is.").append(basement).append("&");
        if (title != null && !title.isEmpty()) urlBuilder.append("title=ilike.%").append(title).append("%&");
        if (location != null && !location.isEmpty()) urlBuilder.append("location=ilike.%").append(location).append("%&");
        if (numRooms != null) urlBuilder.append("num_rooms=eq.").append(numRooms).append("&");
        if (floor != null) urlBuilder.append("floor=eq.").append(floor).append("&");
        if (numFloors != null) urlBuilder.append("num_floors=eq.").append(numFloors).append("&");
        if (heating != null && !heating.isEmpty()) urlBuilder.append("heating=ilike.%").append(heating).append("%&");
        if (typeOfObj != null && !typeOfObj.isEmpty()) urlBuilder.append("type_of_obj=ilike.%").append(typeOfObj).append("%&");
        if (state != null && !state.isEmpty()) urlBuilder.append("state=ilike.%").append(state).append("%&");
        if (terrace != null) urlBuilder.append("terrace=is.").append(terrace).append("&");
        if (parking != null) urlBuilder.append("parking=is.").append(parking).append("&");
        if (furnished != null) urlBuilder.append("furnished=is.").append(furnished).append("&");
        if (newBuilding != null) urlBuilder.append("new_building=is.").append(newBuilding).append("&");
        if (duplex != null) urlBuilder.append("duplex=is.").append(duplex).append("&");
        if (renovated != null) urlBuilder.append("renovated=is.").append(renovated).append("&");

        String url = urlBuilder.toString();

        HttpHeaders headers = createHeaders();
        headers.add("Prefer", "count=exact");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        String countHeader = response.getHeaders().getFirst("Content-Range"); // e.g. "0-9/123"
        if (countHeader != null && countHeader.contains("/")) {
            String totalStr = countHeader.split("/")[1];
            try {
                return Long.parseLong(totalStr);
            } catch (NumberFormatException e) {
                System.err.println("Failed to parse count from Content-Range: " + totalStr);
            }
        }

        return 0;
    }


    public List<Ad> getAds(int page, int size) {
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        String url = supabaseApiUrl + "/ads?order=date_posted.desc&limit=" + size + "&offset=" + (page * size);
        ResponseEntity<List<Ad>> response = restTemplate.exchange(
                url,
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
                supabaseApiUrl + "/users",
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
            System.out.println("User successfully added to database.");
        } else {
            System.out.println("Failed to add user: " + response.getBody());
        }
    }

    public List<Ad> searchAds(int page, int size, Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize,
                              Boolean lift, Boolean basement, String title, String location, Integer numRooms, Integer floor,
                              Integer numFloors, String heating, String typeOfObj, String state, Boolean terrace, Boolean parking,
                              Boolean furnished, Boolean newBuilding, Boolean duplex, Boolean renovated) {

        StringBuilder urlBuilder = new StringBuilder(supabaseApiUrl + "/ads?order=date_posted.desc&");

        if (forSale != null) urlBuilder.append("for_sale=eq.").append(forSale).append("&");
        if (minPrice != null) urlBuilder.append("price=gte.").append(minPrice).append("&");
        if (maxPrice != null) urlBuilder.append("price=lte.").append(maxPrice).append("&");
        if (minSize != null) urlBuilder.append("size=gte.").append(minSize).append("&");
        if (maxSize != null) urlBuilder.append("size=lte.").append(maxSize).append("&");
        if (lift != null) urlBuilder.append("lift=is.").append(lift).append("&");
        if (basement != null) urlBuilder.append("basement=is.").append(basement).append("&");
        if (title != null && !title.isEmpty()) urlBuilder.append("title=ilike.%").append(title).append("%&");
        if (location != null && !location.isEmpty()) urlBuilder.append("location=ilike.%").append(location).append("%&");
        if (numRooms != null) urlBuilder.append("num_rooms=eq.").append(numRooms).append("&");
        if (floor != null) urlBuilder.append("floor=eq.").append(floor).append("&");
        if (numFloors != null) urlBuilder.append("num_floors=eq.").append(numFloors).append("&");
        if (heating != null && !heating.isEmpty()) urlBuilder.append("heating=ilike.%").append(heating).append("%&");
        if (typeOfObj != null && !typeOfObj.isEmpty()) urlBuilder.append("type_of_obj=ilike.%").append(typeOfObj).append("%&");
        if (state != null && !state.isEmpty()) urlBuilder.append("state=ilike.%").append(state).append("%&");
        if (terrace != null) urlBuilder.append("terrace=is.").append(terrace).append("&");
        if (parking != null) urlBuilder.append("parking=is.").append(parking).append("&");
        if (furnished != null) urlBuilder.append("furnished=is.").append(furnished).append("&");
        if (newBuilding != null) urlBuilder.append("new_building=is.").append(newBuilding).append("&");
        if (duplex != null) urlBuilder.append("duplex=is.").append(duplex).append("&");
        if (renovated != null) urlBuilder.append("renovated=is.").append(renovated).append("&");


        urlBuilder.append("limit=").append(size).append("&offset=").append(page * size);

        String url = urlBuilder.toString();

        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<List<Ad>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Ad>>() {}
        );

        return response.getBody();
    }

    public boolean saveSubscription(SavedSearch savedSearch) {
        String url = supabaseApiUrl + "/saved_searches";

        HttpEntity<SavedSearch> request = new HttpEntity<>(savedSearch, createHeaders());
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Subscription saved successfully.");
                return true;
            } else {
                System.out.println("Failed to save subscription: " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error saving subscription: " + e.getMessage());
            return false;
        }
    }

    public Long getUserIdByEmail(String email) {
        try {
            String url = supabaseApiUrl + "/users?email=eq." + email;

            HttpHeaders headers = createHeaders();
            headers.set("Accept", "application/json");

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<User[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    User[].class
            );

            User[] users = response.getBody();
            if (users != null && users.length > 0) {
                return users[0].getId();
            } else {
                System.out.println("User not found for email: " + email);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error fetching user by email: " + e.getMessage());
            return null;
        }
    }

    public List<SavedSearch> getAllSubscriptions() {
        String url = supabaseApiUrl + "/saved_searches";

        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<List<SavedSearch>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<SavedSearch>>() {}
        );

        return response.getBody();
    }


    public String getUserEmailById(Long userId) {
        String url = supabaseApiUrl + "/users?id=eq." + userId;

        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<User>>() {}
        );

        List<User> users = response.getBody();
        if (users != null && !users.isEmpty()) {
            return users.get(0).getEmail();
        } else {
            System.out.println("User not found for ID: " + userId);
            return null;
        }
    }
    public boolean deleteSavedSearchById(Long id) {
        String url = supabaseApiUrl + "/saved_searches?id=eq." + id;

        HttpHeaders headers = createHeaders();

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.NO_CONTENT || response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Saved search deleted successfully, id: " + id);
                return true;
            } else {
                System.out.println("Failed to delete saved search: " + response.getStatusCode() + " - " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error deleting saved search: " + e.getMessage());
            return false;
        }
    }

}
