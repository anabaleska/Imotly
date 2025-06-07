package mk.imotly.service;

import mk.imotly.model.Ad;
import mk.imotly.model.SavedSearch;

import java.util.List;

public interface SupabaseService {
    List<Ad> getAllAds(int page, int size);

    void addAd(Ad ad);

    Ad getAdById(Long id);

    Ad getAdByUrl(String url);
    String registerUser(String email, String password, String name, String surname);
    public List<Ad> searchAds(int page, int size,Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize, Boolean lift, Boolean basement,
                              String title, String location, Integer numRooms, Integer floor, Integer numFloors,
                              String heating, String typeOfObj, String state, Boolean terrace, Boolean parking, Boolean furnished,
                              Boolean newBuilding, Boolean duplex, Boolean renovated);

    public long countAds(Boolean forSale, Integer minPrice, Integer maxPrice, Integer minSize, Integer maxSize, Boolean lift, Boolean basement,
                                          String title, String location, Integer numRooms, Integer floor, Integer numFloors,
                                          String heating, String typeOfObj, String state, Boolean terrace, Boolean parking, Boolean furnished,
                                          Boolean newBuilding, Boolean duplex, Boolean renovated);
    boolean saveSubscription(SavedSearch savedSearch);
    boolean deleteSavedSearchById(Long id);

    public List<SavedSearch> getSearchesByUser(String email);

    Long getUserIdByEmail(String email);

    String getUserEmailById(Long userId);

    List<SavedSearch> getAllSubscriptions();
}
