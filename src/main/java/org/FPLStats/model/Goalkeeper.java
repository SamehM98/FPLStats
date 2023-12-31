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
public class Goalkeeper extends PlayerStats{
    @JsonProperty("goals_conceded")
    private Integer goalsConceded;

    @JsonProperty("expected_goals_conceded")
    private Double xGConceded;

    @JsonProperty("expected_goals_conceded_per_90")
    private Double xGConcededPer90;

    @JsonProperty("clean_sheets")
    private Integer cleanSheets;

    @JsonProperty("saves_per_90")
    private Double savesPer90;

    @JsonProperty("saves")
    private Integer saves;

    public Goalkeeper addStats(Goalkeeper goalkeeper){
        addData(this,goalkeeper);

        this.setCleanSheets(this.getCleanSheets()+goalkeeper.getCleanSheets());
        this.setGoalsConceded(this.getGoalsConceded()+ goalkeeper.getGoalsConceded());
        this.setSaves(this.getSaves()+goalkeeper.getSaves());
        this.setXGConceded(HelperService.doubleFormatter(this.getXGConceded()+goalkeeper.getGoalsConceded()));

        return this;
    }

    public Goalkeeper calculatePer90(){
        if(this.getMinutes() > 0) {
            this.setSavesPer90(HelperService.doubleFormatter((double) (this.getSaves() / this.getMinutes() * 90)));
            this.setXGConcededPer90(HelperService.doubleFormatter(this.getXGConceded() / this.getMinutes() * 90));
        }
        return this;
    }
}
