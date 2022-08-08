package hr.ljakovic.cinespring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ToWatch {

    @EmbeddedId
    private ToWatchId toWatchId;

    @ManyToOne
    @MapsId("id")
    @JsonIgnore
    private AppUser appUser;
}
