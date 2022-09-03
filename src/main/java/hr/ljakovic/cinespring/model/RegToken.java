package hr.ljakovic.cinespring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RegToken {

    @Id
    private UUID id;
    private LocalDateTime confirmed;

    @ManyToOne
    @ToString.Exclude
    private AppUser appUser;
}
