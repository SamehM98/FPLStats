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
public class Defender extends Attacker{
    @JsonProperty("goals_conceded")
    private Integer goalsConceded;

    @JsonProperty("expected_goals_conceded")
    private Double xGConceded;

    @JsonProperty("expected_goals_conceded_per_90")
    private Double xGConcededPer90;

    @JsonProperty("clean_sheets")
    private Integer cleanSheets;
}
