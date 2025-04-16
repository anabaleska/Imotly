package mk.imotly.service;

import mk.imotly.model.Ad;
import java.util.List;

public interface SupabaseService {
    List<Ad> getAllAds();

    void addAd(Ad ad);

    Ad getAdById(Long id);

    Ad getAdByUrl(String url);
}
