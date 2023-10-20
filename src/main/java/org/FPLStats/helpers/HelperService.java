package org.FPLStats.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.model.Player;
import org.FPLStats.model.Team;
import org.FPLStats.model.TeamStats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
