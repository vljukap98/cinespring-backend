package hr.ljakovic.cinespring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"})
)
public class AppUser {

    @Id
    private UUID id;
    private String email;
    private String username;
    private String password;
    private Boolean isActivated;

    @ManyToMany(mappedBy = "appUsers", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Role> roles;

    @OneToMany(mappedBy = "appUser")
    @ToString.Exclude
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "appUser")
    @ToString.Exclude
    private List<ToWatch> toWatchList;

    @OneToMany(mappedBy = "appUser")
    @ToString.Exclude
    private List<Watched> watchedList;
}
