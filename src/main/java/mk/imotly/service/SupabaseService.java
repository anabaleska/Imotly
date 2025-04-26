package mk.imotly.service;

import mk.imotly.model.Ad;
import java.util.List;

public interface SupabaseService {
    List<Ad> getAllAds(int page, int size);

    void addAd(Ad ad);

    Ad getAdById(Long id);

    Ad getAdByUrl(String url);
    String registerUser(String email, String password, String name, String surname);
    public List<Ad> searchAds(Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize, Boolean lift, Boolean basement);
}
