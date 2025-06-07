package mk.imotly.web;

import mk.imotly.model.Ad;
import mk.imotly.model.DTO.PagedAdResponse;
import mk.imotly.model.Filters;
import mk.imotly.model.SavedSearch;
import mk.imotly.model.SavedSearchRequest;
import mk.imotly.service.SupabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdController {

    private final SupabaseService supabaseService;

    public AdController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }
    @GetMapping
    public ResponseEntity<PagedAdResponse>getAds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean forSale,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize,
            @RequestParam(required = false) Boolean lift,
            @RequestParam(required = false) Boolean basement,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer numRooms,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) Integer numFloors,
            @RequestParam(required = false) String heating,
            @RequestParam(required = false) String typeOfObj,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean terrace,
            @RequestParam(required = false) Boolean parking,
            @RequestParam(required = false) Boolean furnished,
            @RequestParam(required = false) Boolean newBuilding,
            @RequestParam(required = false) Boolean duplex,
            @RequestParam(required = false) Boolean renovated
    ) {
        List<Ad> ads = supabaseService.searchAds(
                page, size,
                forSale, minPrice, maxPrice, minSize, maxSize, lift, basement, title, location, numRooms, floor, numFloors,
                heating, typeOfObj, state, terrace, parking, furnished, newBuilding, duplex, renovated
        );
        long total=supabaseService.countAds( forSale, minPrice, maxPrice, minSize, maxSize, lift, basement, title, location, numRooms, floor, numFloors,
                heating, typeOfObj, state, terrace, parking, furnished, newBuilding, duplex, renovated);
        total = (int) Math.ceil((double) total / size);
        return ResponseEntity.ok(new PagedAdResponse(ads, total));
    }

    @PostMapping
    public ResponseEntity<String> addAd(@RequestBody Ad ad) {
        supabaseService.addAd(ad);
        return new ResponseEntity<>("Ad successfully added", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ad> getAdById(@PathVariable Long id) {
        Ad ad = supabaseService.getAdById(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/saved/{id}")
    public ResponseEntity<List<SavedSearch>> getSavedById(@PathVariable String id) {
        List<SavedSearch> ad=supabaseService.getSearchesByUser(id);
        if (ad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/saved/{id}")
    public ResponseEntity<Void> deleteSavedSearch(@PathVariable Long id) {
        boolean deleted = supabaseService.deleteSavedSearchById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private SavedSearch mapToSavedSearch(SavedSearchRequest request) {
        Filters f = request.getFilters();
        SavedSearch s = new SavedSearch();
        s.setUserId(getUserIdFromEmail(request.getEmail()));

        s.setBasement(f.getBasement());
        s.setDuplex(f.getDuplex());
        s.setFloor(f.getFloor());
        s.setForSale(f.getForSale());
        s.setFurnished(f.getFurnished());
        s.setHeating(f.getHeating());
        s.setLift(f.getLift());
        s.setLocation(f.getLocation());
        s.setMaxPrice(f.getMaxPrice());
        s.setSize(f.getMaxSize());
        s.setNewBuilding(f.getNewBuilding());
        s.setNumFloors(f.getNumFloors());
        s.setNumRooms(f.getNumRooms());
        s.setParking(f.getParking());
        s.setRenovated(f.getRenovated());
        s.setState(f.getState());
        s.setTerrace(f.getTerrace());

        s.setTypeOfObj(f.getTypeOfObj());
        System.out.println("Sending to Supabase: " + s);

        return s;
    }

    private Long getUserIdFromEmail(String email) {
        return supabaseService.getUserIdByEmail(email);
    }



    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SavedSearchRequest request) {
        System.out.println("Received SavedSearchRequest: " + request);
        System.out.println("Filters: " + request.getFilters());

        SavedSearch savedSearch = mapToSavedSearch(request);
        boolean success = supabaseService.saveSubscription(savedSearch);

        if (success) {
            return ResponseEntity.ok().body(Map.of("message", "Subscription saved."));
        }
        return ResponseEntity.status(500).body(Map.of("message", "Failed to save subscription."));
    }




}
