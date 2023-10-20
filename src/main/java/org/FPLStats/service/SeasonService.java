package org.FPLStats.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.client.FplClient;
import org.FPLStats.helpers.Comparators;
import org.FPLStats.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeasonService {
    @Autowired
    private FplClient fplClient;
    @Autowired
    private BootstrapService bootstrapService;

    ObjectMapper objectMapper = new ObjectMapper();

    public ArrayList<?> playerSeasonData(String sort, Integer position, Integer team){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String, Object> bootstrap = fplClient.bootstrap();
        Integer currentGameweek = bootstrapService.currentGameweek(bootstrap);
        Integer minimumMinutes = (currentGameweek-1)*25;

        ArrayList<Attacker> attackers = new ArrayList<>();
        ArrayList<Defender> defenders = new ArrayList<>();
        ArrayList<Goalkeeper> goalkeepers = new ArrayList<>();

        ArrayList<LinkedHashMap<String, Object>> playerObjects = (ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("elements");
        playerObjects.forEach(p -> {
            if((Integer) p.get("minutes") >= minimumMinutes && p.get("element_type").equals(position) && (team == 0 || (team > 0 && p.get("team").equals(team)))){
                if(position.equals(3) || position.equals(4))
                    attackers.add(objectMapper.convertValue(p, Attacker.class));
                else if(position.equals(2))
                    defenders.add(objectMapper.convertValue(p, Defender.class));
                else
                    goalkeepers.add(objectMapper.convertValue(p, Goalkeeper.class));
            }
        });

        Comparator<Attacker> attackerComparator = Comparators.attackerComparator(sort);
        Comparator<Defender> defenderComparator = Comparators.defenderComparator(sort);
        Comparator<Goalkeeper> goalkeeperComparator = Comparators.goalkeeperComparator(sort);

        if(position.equals(1)) {
            goalkeepers.sort(goalkeeperComparator);
            return goalkeepers;
        }
        else if(position.equals(2)){
            if(Comparators.defenceStats().contains(sort))
                defenders.sort(defenderComparator);
            else
                defenders.sort(attackerComparator);

            return defenders;
        }

        attackers.sort(attackerComparator);
        return attackers;
    }

    public ArrayList<TeamStats> teamSeasonData(String sort){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String, Object> bootstrap = fplClient.bootstrap();

        HashMap<Integer, TeamStats> teamStatsMap = bootstrapService.teamStats((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("teams"));
        ArrayList<LinkedHashMap<String, Object>> playerObjects = (ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("elements");

        playerObjects.forEach(p ->{
            TeamStats teamStats;
            Integer team = (Integer) p.get("team");
            if(p.get("element_type").equals(1)){
                Goalkeeper goalkeeper = objectMapper.convertValue(p,Goalkeeper.class);
                teamStats = teamStatsMap.get(team);
                if(teamStats.getXGConceded() == null)
                    teamStats.setXGConceded(goalkeeper.getXGConceded());
                else
                    teamStats.setXGConceded(teamStats.getXGConceded() + goalkeeper.getXGConceded());
            }
            else{
                Attacker attacker = objectMapper.convertValue(p,Attacker.class);
                teamStats = teamStatsMap.get(team);
                if(teamStats.getXG() == null)
                    teamStats.setXG(attacker.getXG());
                else
                    teamStats.setXG(teamStats.getXG() + attacker.getXG());
            }
            teamStatsMap.put(team,teamStats);
        });

        ArrayList<TeamStats> teams = new ArrayList<>(teamStatsMap.values());
        teams.sort(Comparators.teamStatsComparator(sort));

        return teams;
    }
}
