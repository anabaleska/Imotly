package mk.imotly.web;

import mk.imotly.model.Ad;
import mk.imotly.service.SupabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final SupabaseService supabaseService;

    public AdController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    @GetMapping
    public List<Ad> getAllAds() {
        return supabaseService.getAllAds();
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
}
