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
}
