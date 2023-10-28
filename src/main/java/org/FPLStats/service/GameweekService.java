package org.FPLStats.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.client.FplClient;
import org.FPLStats.helpers.Comparators;
import org.FPLStats.helpers.HelperService;
import org.FPLStats.model.*;
import org.FPLStats.model.response.PlayerStatsResponse;
import org.FPLStats.model.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameweekService {
    @Autowired
    private FplClient fplClient;
    @Autowired
    private BootstrapService bootstrapService;
    @Autowired
    private HelperService helperService;

    HashMap<Integer, Attacker> attackers = new HashMap<>();
    HashMap<Integer, Defender> defenders = new HashMap<>();
    HashMap<Integer, Goalkeeper> goalkeepers = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();
    public void oneGameweek(Integer gw, HashMap<Integer, Object> playerHashMap, Integer position, Integer team){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String, Object> gameweek = fplClient.gameweek(gw);

        ArrayList<LinkedHashMap<String, Object>> gameweekPlayers = (ArrayList<LinkedHashMap<String, Object>>) gameweek.get("elements");

        gameweekPlayers.forEach(p -> {
            Integer playerId = (Integer) p.get("id");
            Player player = objectMapper.convertValue(playerHashMap.get(playerId),Player.class);

            if(player.getPosition().equals(position) && (team == 0 || (team > 0 && p.get("team").equals(team)))) {
                if (player.getPosition().equals(3) || player.getPosition().equals(4)) {
                    Attacker attacker = objectMapper.convertValue(p.get("stats"), Attacker.class);

                    if (attackers.containsKey(playerId)) {
                        attackers.put(playerId, attackers.get(playerId).addStats(attacker).calculatePer90());
                    } else {
                        attacker.setPlayerInfo(player);
                        attackers.put(playerId, attacker.calculatePer90());
                    }
                }
                else if(player.getPosition().equals(2)){
                    Defender defender = objectMapper.convertValue(p.get("stats"), Defender.class);

                    if(defenders.containsKey(playerId)){
                        defenders.put(playerId, (Defender) defenders.get(playerId).addStats(defender).calculatePer90());
                    }
                    else{
                        defender.setPlayerInfo(player);
                        defenders.put(playerId, (Defender) defender.calculatePer90());
                    }
                }
                else{
                    Goalkeeper goalkeeper = objectMapper.convertValue(p.get("stats"),Goalkeeper.class);

                    if(goalkeepers.containsKey(playerId)){
                        goalkeepers.put(playerId, goalkeepers.get(playerId).addStats(goalkeeper).calculatePer90());
                    }
                    else{
                        player.setPlayerInfo(player);
                        goalkeepers.put(playerId,goalkeeper.calculatePer90());
                    }
                }
            }
        });
    }

    public PlayerStatsResponse gameweekRange(Integer begin, Integer end, Integer position, Integer team, String sort){
        HashMap<String, Object> bootstrap = fplClient.bootstrap();
        HashMap<Integer, Object> playerHashMap = bootstrapService.playersInfo((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("elements"));
        Integer currentGameweek = bootstrapService.currentGameweek(bootstrap);
        Integer minimumMinutes = (end-begin+1)*15;
        ArrayList<Team> teams = helperService.teamArrayList((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("teams"));

        for(int i=begin;i<=end;i++){
            oneGameweek(i,playerHashMap,position,team);
        }

        return new PlayerStatsResponse(helperService.sortedStats(position,
                sort,attackers.values().stream().filter(
                        o -> o.getMinutes() >= minimumMinutes
                ).collect(Collectors.toCollection(ArrayList::new)),
                defenders.values().stream().filter(
                        o -> o.getMinutes() >= minimumMinutes
                ).collect(Collectors.toCollection(ArrayList::new)),
                goalkeepers.values().stream().filter(
                        o -> o.getMinutes() >= minimumMinutes
                ).collect(Collectors.toCollection(ArrayList::new))),Comparators.positionComparators(position),currentGameweek,teams);
    }

    public ResponseDto gameweekRangeTeams(Integer begin, Integer end, String sort){
        HashMap<String, Object> bootstrap = fplClient.bootstrap();
        Integer currentGameweek = bootstrapService.currentGameweek(bootstrap);
        HashMap<Integer, Object> playerHashMap = bootstrapService.playersInfo((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("elements"));
        HashMap<Integer, TeamStats> teamStatsMap = bootstrapService.teamStats((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("teams"));

        for(int i=begin;i<=end;i++){
            oneGameweekTeams(i,teamStatsMap,playerHashMap);
        }

        Comparator<TeamStats> comparator = Comparators.teamStatsComparator(sort);
        ArrayList<TeamStats> teamStats = teamStatsMap.values().stream().sorted(comparator).collect(Collectors.toCollection(ArrayList::new));
        return new ResponseDto(teamStats,Comparators.teamsComparator(),currentGameweek);
    }

    private void oneGameweekTeams(int gw, HashMap<Integer, TeamStats> teamStatsMap, HashMap<Integer, Object> playerHashMap) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String, Object> gameweek = fplClient.gameweek(gw);

        ArrayList<LinkedHashMap<String, Object>> gameweekPlayers = (ArrayList<LinkedHashMap<String, Object>>) gameweek.get("elements");
        gameweekPlayers.forEach(p -> {
            Integer playerId = (Integer) p.get("id");
            Player player = objectMapper.convertValue(playerHashMap.get(playerId),Player.class);
            Integer team = player.getTeam();
            TeamStats stats = teamStatsMap.get(team);

            if(player.getPosition().equals(1)){
                Goalkeeper goalkeeper = objectMapper.convertValue(p.get("stats"),Goalkeeper.class);
                if(stats.getXGConceded() == null){
                    stats.setXGConceded(goalkeeper.getXGConceded());
                }
                else{
                    stats.setXGConceded(stats.getXGConceded()+goalkeeper.getXGConceded());
                }
            }
            else{
                Attacker attacker = objectMapper.convertValue(p.get("stats"),Attacker.class);
                if(stats.getXG() == null){
                    stats.setXG(attacker.getXG());
                }
                else{
                    stats.setXG(stats.getXG()+attacker.getXG());
                }
            }

            teamStatsMap.put(stats.getId(),stats);
        });
    }
}
