package mk.imotly.repository;
import mk.imotly.model.Ad;
import mk.imotly.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

        // Add pagination params at the end
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


//    public List<Ad> searchAds(int page, int size, Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize, Boolean lift, Boolean basement,
//                              String title, String location, Integer numRooms, Integer floor, Integer numFloors,
//                              String heating, String typeOfObj, String state, Boolean terrace, Boolean parking, Boolean furnished,
//                              Boolean newBuilding, Boolean duplex, Boolean renovated) {
//        StringBuilder urlBuilder = new StringBuilder(supabaseApiUrl + "/ads?");
//
//        if (forSale != null) urlBuilder.append("forSale=eq.").append(forSale).append("&");
//        if (minPrice != null) urlBuilder.append("price=gte.").append(minPrice).append("&");
//        if (maxPrice != null) urlBuilder.append("price=lte.").append(maxPrice).append("&");
//        if (minSize != null) urlBuilder.append("size=gte.").append(minSize).append("&");
//        if (maxSize != null) urlBuilder.append("size=lte.").append(maxSize).append("&");
//        if (lift != null) urlBuilder.append("lift=is.").append(lift).append("&");
//        if (basement != null) urlBuilder.append("basement=is.").append(basement).append("&");
//        if (title != null && !title.isEmpty()) {
//            urlBuilder.append("title=ilike.%").append(title).append("%&");
//        }
//        if (location != null && !location.isEmpty()) {
//            urlBuilder.append("location=ilike.%").append(location).append("%&");
//        }
//
//        if (numRooms != null) urlBuilder.append("numRooms=eq.").append(numRooms).append("&");
//        if (floor != null) urlBuilder.append("floor=eq.").append(floor).append("&");
//        if (numFloors != null) urlBuilder.append("numFloors=eq.").append(numFloors).append("&");
//        if (heating != null && !heating.isEmpty()) {
//            urlBuilder.append("heating=ilike.%").append(heating).append("%&");
//        }
//        if (typeOfObj != null && !typeOfObj.isEmpty()) {
//            urlBuilder.append("typeOfObj=ilike.%").append(typeOfObj).append("%&");
//        }
//        if (state != null && !state.isEmpty()) {
//            urlBuilder.append("state=ilike.%").append(state).append("%&");
//        }
//        if (terrace != null) urlBuilder.append("terrace=is.").append(terrace).append("&");
//        if (parking != null) urlBuilder.append("parking=is.").append(parking).append("&");
//        if (furnished != null) urlBuilder.append("furnished=is.").append(furnished).append("&");
//        if (newBuilding != null) urlBuilder.append("newBuilding=is.").append(newBuilding).append("&");
//        if (duplex != null) urlBuilder.append("duplex=is.").append(duplex).append("&");
//        if (renovated != null) urlBuilder.append("renovated=is.").append(renovated).append("&");
//
//
//
//        String url = urlBuilder.toString();
//        if (url.endsWith("&")) {
//            url = url.substring(0, url.length() - 1);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("apikey", supabaseApiKey);
//        headers.set("Authorization", "Bearer " + supabaseApiKey);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<List<Ad>> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<List<Ad>>() {}
//        );
//
//        return response.getBody();
//    }
}
