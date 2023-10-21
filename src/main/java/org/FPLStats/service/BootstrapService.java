package org.FPLStats.service;

import org.FPLStats.helpers.HelperService;
import org.FPLStats.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class BootstrapService {
    @Autowired
    private HelperService helperService;
    public HashMap<Integer, Object> playersInfo(ArrayList<LinkedHashMap<String, Object>> objects){
        return helperService.listToMap(objects,Player.class);
    }

    public HashMap<Integer, Object> teamsInfo(ArrayList<LinkedHashMap<String, Object>> objects){
        return helperService.listToMap(objects,Team.class);
    }

    public HashMap<Integer, Object> positions(ArrayList<LinkedHashMap<String, Object>> objects){
        return helperService.listToMap(objects,Position.class);
    }

    public HashMap<Integer, TeamStats> teamStats(ArrayList<LinkedHashMap<String, Object>> objects){
        return helperService.teamStatsHashMap(objects);
    }

    public HashMap<Integer, TeamFixtures> teamFixtures(ArrayList<LinkedHashMap<String, Object>> objects){
        return helperService.teamFixturesHashMap(objects);
    }

    public Integer currentGameweek(HashMap<String,Object> bootstrap){
        ArrayList<LinkedHashMap<String,Object>> gameweeks = (ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("events");

        for(int i=0;i<gameweeks.size();i++){
            Boolean is_current = (Boolean) gameweeks.get(i).get("is_current");
            if(is_current)
                return i+1;
        }
        return 0;
    }

    public HashMap<String, HashMap<Integer, Object>> bootstrapInfo(HashMap<String,Object> bootstrap){
        HashMap<String, HashMap<Integer, Object>> bootsrapInfo = new HashMap<>();

        bootsrapInfo.put("teams",teamsInfo((ArrayList<LinkedHashMap<String,Object>>) bootstrap.get("teams")));
        bootsrapInfo.put("players",playersInfo((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("elements")));
        bootsrapInfo.put("positions",positions((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("element_types")));

        return bootsrapInfo;
    }
}
