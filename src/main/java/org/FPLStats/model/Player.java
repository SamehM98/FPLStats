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
public class Player extends BasicInfo {
    @JsonProperty("web_name")
    private String name;
    @JsonProperty("element_type")
    private Integer position;

    @JsonProperty("team")
    private Integer team;

    @JsonProperty("now_cost")
    private Integer price;
    @JsonProperty("selected_by_percent")
    private Double selection;

    public void setPlayerInfo(Player player){
        this.setName(player.getName());
        this.setTeam(player.getTeam());
        this.setPosition(player.getPosition());
        this.setSelection(player.getSelection());
        this.setPrice(player.getPrice());
        this.setId(player.getId());
    }

    public static Attacker addOutfield(Attacker originalObject,Attacker tempObject){
        originalObject.setGoals(originalObject.getGoals()+tempObject.getGoals());
        originalObject.setAssists(originalObject.getAssists()+ tempObject.getAssists());
        originalObject.setXG(originalObject.getXG()+ tempObject.getXG());
        originalObject.setXA(originalObject.getXA()+tempObject.getXA());

        originalObject.setMinutes(originalObject.getMinutes()+tempObject.getMinutes());
        originalObject.setBonus(originalObject.getBonus()+tempObject.getBonus());
        originalObject.setStarts(originalObject.getStarts()+tempObject.getStarts());
        originalObject.setTotalPoints(originalObject.getTotalPoints()+ tempObject.getTotalPoints());

        if(originalObject.getClass().equals(Defender.class)){
            Defender originalDefender = (Defender) originalObject;
            Defender tempDefender = (Defender) tempObject;

            originalDefender.setCleanSheets(originalDefender.getCleanSheets()+tempDefender.getCleanSheets());
            originalDefender.setXGConceded(originalDefender.getXGConceded()+tempDefender.getGoalsConceded());
            originalDefender.setGoalsConceded(originalDefender.getGoalsConceded()+tempDefender.getGoalsConceded());
            return originalDefender;
        }

        return originalObject;
    }

    public static Attacker calculatePer90Outfield(Attacker player){
        player.setXGper90((player.getXG()/player.getMinutes()) * 90);
        player.setXGIPer90((player.getXGI()/player.getMinutes()) * 90);
        player.setXAper90((player.getXA()/player.getMinutes() * 90));

        if(player.getClass().equals(Defender.class)){
            Defender defender = (Defender) player;
            defender.setXGConcededPer90((defender.getXGConceded()/defender.getMinutes() * 90));
            return defender;
        }

        return player;
    }

    public static Goalkeeper addKeeper(Goalkeeper originalObject, Goalkeeper tempObject){
        originalObject.setMinutes(originalObject.getMinutes()+tempObject.getMinutes());
        originalObject.setBonus(originalObject.getBonus()+tempObject.getBonus());
        originalObject.setStarts(originalObject.getStarts()+tempObject.getStarts());
        originalObject.setTotalPoints(originalObject.getTotalPoints()+ tempObject.getTotalPoints());

        originalObject.setCleanSheets(originalObject.getCleanSheets()+tempObject.getCleanSheets());
        originalObject.setGoalsConceded(originalObject.getGoalsConceded()+ tempObject.getGoalsConceded());
        originalObject.setSaves(originalObject.getSaves()+tempObject.getSaves());
        originalObject.setXGConceded(originalObject.getXGConceded()+tempObject.getGoalsConceded());

        return originalObject;
    }

    public static Goalkeeper calculatePer90Keeper(Goalkeeper goalkeeper){
        goalkeeper.setSavesPer90((double) (goalkeeper.getSaves() / goalkeeper.getMinutes() * 90));
        goalkeeper.setXGConcededPer90(goalkeeper.getXGConceded()/goalkeeper.getMinutes() * 90);
        return goalkeeper;
    }
}
