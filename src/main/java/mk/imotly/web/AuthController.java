package mk.imotly.web;

import mk.imotly.model.RegisterRequest;
import mk.imotly.service.SupabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final SupabaseService supabaseService;

    @Autowired
    public AuthController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        System.out.println("Registering user: " + request.getEmail());
        try {
            String message = supabaseService.registerUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getName(),
                    request.getSurname()
            );
            return ResponseEntity.ok().body(message);
        } catch (HttpClientErrorException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

}
