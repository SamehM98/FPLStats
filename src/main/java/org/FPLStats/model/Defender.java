package org.FPLStats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.FPLStats.helpers.HelperService;

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

    @Override
    public Attacker addStats(Attacker player) {
        super.addStats(player);
        Defender tempDefender = (Defender) player;
        this.setCleanSheets(this.getCleanSheets()+tempDefender.getCleanSheets());
        this.setXGConceded(HelperService.doubleFormatter(this.getXGConceded()+tempDefender.getGoalsConceded()));
        this.setGoalsConceded(this.getGoalsConceded()+tempDefender.getGoalsConceded());
        return this;
    }

    @Override
    public Attacker calculatePer90() {
        if(this.getMinutes() > 0) {
            super.calculatePer90();
            this.setXGConcededPer90(HelperService.doubleFormatter((this.getXGConceded() / this.getMinutes()) * 90));
        }
        return this;
    }
}
