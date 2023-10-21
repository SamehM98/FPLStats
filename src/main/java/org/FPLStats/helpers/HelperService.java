package org.FPLStats.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class HelperService {

    ObjectMapper objectMapper = new ObjectMapper();
    public HashMap<Integer, Object> listToMap(ArrayList<LinkedHashMap<String, Object>> objects, Class<?> t){
        HashMap<Integer,Object> map = new HashMap<>();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objects.forEach(o -> {
           Object temp = objectMapper.convertValue(o,t);
           map.put((Integer) o.get("id"),temp);
        });
        return map;
    }

    public HashMap<Integer, TeamStats> teamStatsHashMap(ArrayList<LinkedHashMap<String, Object>> objects){
        HashMap<Integer,TeamStats> map = new HashMap<>();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objects.forEach(o -> {
            TeamStats temp = objectMapper.convertValue(o,TeamStats.class);
            map.put((Integer) o.get("id"),temp);
        });
        return map;
    }

    public ArrayList<? extends PlayerStats> sortedStats(Integer position,
                                                        String sort,
                                                        ArrayList<Attacker> attackers,
                                                        ArrayList<Defender> defenders,
                                                        ArrayList<Goalkeeper> goalkeepers){
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
}