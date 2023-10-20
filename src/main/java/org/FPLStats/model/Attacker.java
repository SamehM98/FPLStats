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
public class Attacker extends PlayerStats{
    @JsonProperty("goals_scored")
    private Integer goals;
    @JsonProperty("assists")
    private Integer assists;

    @JsonProperty("expected_goals")
    private Double xG;
    @JsonProperty("expected_assists")
    private Double xA;
    @JsonProperty("expected_goal_involvements")
    private Double xGI;
    @JsonProperty("expected_goals_per_90")
    private Double xGper90;
    @JsonProperty("expected_assists_per_90")
    private Double xAper90;
    @JsonProperty("expected_goal_involvements_per_90")
    private Double xGIPer90;
}
