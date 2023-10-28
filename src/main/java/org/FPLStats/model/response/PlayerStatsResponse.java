package org.FPLStats.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.FPLStats.model.PlayerStats;
import org.FPLStats.model.Team;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class PlayerStatsResponse extends ResponseDto{
    private ArrayList<Team> teams;

    public PlayerStatsResponse(ArrayList<? extends PlayerStats> playerStats, ArrayList<String> comparators, Integer currentGameweek, ArrayList<Team> teams) {
        this.setStats(playerStats);
        this.setComparators(comparators);
        this.setCurrentGameweek(currentGameweek);
        this.setTeams(teams);
    }
}
