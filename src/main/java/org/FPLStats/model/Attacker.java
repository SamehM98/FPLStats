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

    public Attacker addStats(Attacker player){
        addData(this,player);

        this.setGoals(this.getGoals()+player.getGoals());
        this.setAssists(this.getAssists()+ player.getAssists());
        this.setXG(this.getXG()+ player.getXG());
        this.setXA(this.getXA()+player.getXA());

        return this;
    }

    public Attacker calculatePer90(){
        this.setXGper90((this.getXG()/this.getMinutes()) * 90);
        this.setXGIPer90((this.getXGI()/this.getMinutes()) * 90);
        this.setXAper90((this.getXA()/this.getMinutes() * 90));

        return this;
    }
}
