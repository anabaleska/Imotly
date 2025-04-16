package mk.imotly.service.impl;

import mk.imotly.model.Ad;
import mk.imotly.repository.SupabaseClient;
import mk.imotly.service.SupabaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupabaseServiceImpl implements SupabaseService {

    private final SupabaseClient supabaseClient;

    public SupabaseServiceImpl(SupabaseClient supabaseClient) {
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
}
