package org.FPLStats.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.model.Attacker;
import org.FPLStats.model.Defender;
import org.FPLStats.model.Goalkeeper;
import org.FPLStats.model.TeamStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Comparators {
    ObjectMapper objectMapper = new ObjectMapper();

    public static ArrayList<String> defenceStats(){
        return new ArrayList<>(Arrays.asList("goalsConceded", "xGC", "cleanSheets", "xGCPer90"));
    }

    public static ArrayList<String> defenceSort(){
        ArrayList<String> defenceStats =  defenceStats();
        defenceStats.addAll(attackerSort());
        return defenceStats;
    }

    public static ArrayList<String> attackerSort(){
        return new ArrayList<>(Arrays.asList("xGPer90","xGIPer90","xGI","goals",
                "assists","xA","xAPer90","assists","minutes","starts","bonus"));
    }

    public static ArrayList<String> goalkeeperSort(){
        return new ArrayList<>(Arrays.asList("goalsConceded", "xGC", "cleanSheets", "bonus",
                "saves","savesPer90","xGCPer90"));
    }
    public static Comparator<Attacker> attackerComparator(String sort){
        return (o1, o2) -> switch (sort) {
            case "xGPer90" -> o2.getXGper90().compareTo(o1.getXGper90());
            case "xGIPer90" -> o2.getXGIPer90().compareTo(o1.getXGIPer90());
            case "xGI" -> o2.getXGI().compareTo(o1.getXGI());
            case "goals" -> o2.getGoals().compareTo(o1.getGoals());
            case "xA" -> o2.getXA().compareTo(o1.getXA());
            case "xAPer90" -> o2.getXAper90().compareTo(o1.getXAper90());
            case "assists" -> o2.getAssists().compareTo(o1.getAssists());
            case "minutes" -> o2.getMinutes().compareTo(o1.getMinutes());
            case "starts" -> o2.getStarts().compareTo(o1.getStarts());
            case "bonus" -> o2.getBonus().compareTo(o1.getBonus());
            default -> o2.getXG().compareTo(o1.getXG());
        };
    }

    public static Comparator<Defender> defenderComparator(String sort){
        return (o1, o2) -> switch (sort) {
            case "goalsConceded" -> o1.getGoalsConceded().compareTo(o2.getGoalsConceded());
            case "cleanSheets" -> o2.getCleanSheets().compareTo(o1.getCleanSheets());
            case "xGC" -> o1.getXGConceded().compareTo(o2.getXGConceded());
            default -> o1.getXGConcededPer90().compareTo(o2.getXGConcededPer90());
        };
    }

    public static Comparator<Goalkeeper> goalkeeperComparator(String sort){
        return (o1, o2) -> switch (sort) {
            case "goalsConceded" -> o1.getGoalsConceded().compareTo(o2.getGoalsConceded());
            case "xGC" -> o1.getXGConceded().compareTo(o2.getXGConceded());
            case "cleanSheets" -> o2.getCleanSheets().compareTo(o1.getCleanSheets());
            case "saves" -> o2.getSaves().compareTo(o1.getSaves());
            case "savesPer90" -> o2.getSavesPer90().compareTo(o1.getSavesPer90());
            case "bonus" -> o2.getBonus().compareTo(o1.getBonus());
            default -> o1.getXGConcededPer90().compareTo(o2.getXGConcededPer90());
        };
    }

    public static Comparator<TeamStats> teamStatsComparator(String sort){
        return (o1, o2) -> {
            if(sort.equals("xGC"))
                return o1.getXGConceded().compareTo(o2.getXGConceded());
            return o2.getXG().compareTo(o1.getXG());
        };
    }
}
