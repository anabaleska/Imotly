package mk.imotly.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.imotly.model.enumerations.Role;


import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private String id;
    private String email;
    private String name;
    private String surname;
    private Role role;

    private List<Ad> savedAds;

    public User(String id, String email, String name, String surname, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}
