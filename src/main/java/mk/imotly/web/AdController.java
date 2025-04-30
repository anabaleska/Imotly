package mk.imotly.web;

import mk.imotly.model.Ad;
import mk.imotly.service.SupabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdController {

    private final SupabaseService supabaseService;

    public AdController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }
    @GetMapping
    public ResponseEntity<List<Ad>> getAds(
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
        return ResponseEntity.ok(ads);
    }


//    @GetMapping
//    public ResponseEntity<List<Ad>> getAllAds(@RequestParam(defaultValue = "0") int page,
//                              @RequestParam(defaultValue = "10") int size) {
//        List<Ad> ads = supabaseService.getAllAds(page, size);
//        return ResponseEntity.ok(ads);
//    }

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

//    @GetMapping("/search")
//    public List<Ad> searchAds(
//            @RequestParam(required = false) Boolean forSale,
//            @RequestParam(required = false) Integer minPrice,
//            @RequestParam(required = false) Integer maxPrice,
//            @RequestParam(required = false) Integer minSize,
//            @RequestParam(required = false) Integer maxSize,
//            @RequestParam(required = false) Boolean lift,
//            @RequestParam(required = false) Boolean basement,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String location,
//            @RequestParam(required = false) Integer numRooms,
//            @RequestParam(required = false) Integer floor,
//            @RequestParam(required = false) Integer numFloors,
//            @RequestParam(required = false) String heating,
//            @RequestParam(required = false) String typeOfObj,
//            @RequestParam(required = false) String state,
//            @RequestParam(required = false) Boolean terrace,
//            @RequestParam(required = false) Boolean parking,
//            @RequestParam(required = false) Boolean furnished,
//            @RequestParam(required = false) Boolean newBuilding,
//            @RequestParam(required = false) Boolean duplex,
//            @RequestParam(required = false) Boolean renovated
//    ) {
//        return supabaseService.searchAds(
//                forSale, minPrice, maxPrice, minSize, maxSize, lift, basement, title, location, numRooms, floor, numFloors,
//                heating, typeOfObj, state, terrace, parking, furnished, newBuilding, duplex, renovated
//        );
//    }


}
