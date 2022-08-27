package hr.ljakovic.cinespring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchedReq {

    private Long movieId;
    private String username;
    private Double stars;
}
