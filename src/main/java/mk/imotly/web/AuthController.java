package mk.imotly.web;

import mk.imotly.model.RegisterRequest;
import mk.imotly.service.SupabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SupabaseService supabaseService;

    @Autowired
    public AuthController(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }


    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {

        System.out.println("Registering user: " + request.getEmail());
        return supabaseService.registerUser(request.getEmail(), request.getPassword(), request.getName(), request.getSurname());
    }
}
