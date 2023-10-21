package org.FPLStats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FplFixture {
    @JsonProperty("team_h")
    private Integer homeTeam;

    @JsonProperty("team_a")
    private Integer awayTeam;

    @JsonProperty("team_h_difficulty")
    private Integer homeTeamDifficulty;

    @JsonProperty("team_a_difficulty")
    private Integer awayTeamDifficulty;

    @JsonProperty("event")
    private Integer gameweek;
}
