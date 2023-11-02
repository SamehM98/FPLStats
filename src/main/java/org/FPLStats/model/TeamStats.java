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
public class TeamStats extends Team{
    @JsonProperty("expected_goals")
    private Double xG = 0.00;

    @JsonProperty("expected_goals_conceded")
    private Double xGConceded = 0.00;
}
