package mk.imotly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.imotly.model.enumerations.Role;


import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Long id;
    private String email;
    private String name;
    private String surname;
    private Role role;
    @JsonIgnore
    private List<Ad> savedAds;

    public User( String email, String name, String surname) {

        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = Role.USER;
    }
}
