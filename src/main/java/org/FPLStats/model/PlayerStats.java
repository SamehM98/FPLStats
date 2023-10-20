package org.FPLStats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats extends Player{
    @JsonProperty("total_points")
    private Integer totalPoints;
    @JsonProperty("minutes")
    private Integer minutes;
    @JsonProperty("starts")
    private Integer starts;
    @JsonProperty("bonus")
    private Integer bonus;
}
