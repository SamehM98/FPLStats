package org.FPLStats.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.client.FplClient;
import org.FPLStats.helpers.Comparators;
import org.FPLStats.helpers.HelperService;
import org.FPLStats.model.FplFixture;
import org.FPLStats.model.SingleFixture;
import org.FPLStats.model.TeamFixtures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Service
public class FixturesService {
    @Autowired
    private FplClient fplClient;
    @Autowired
    private BootstrapService bootstrapService;

    ObjectMapper objectMapper = new ObjectMapper();

    public ArrayList<TeamFixtures> fixturesRange(Integer begin, Integer end){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HashMap<String, Object> bootstrap = fplClient.bootstrap();
        HashMap<Integer, TeamFixtures> teamFixturesHashMap = bootstrapService.teamFixtures((ArrayList<LinkedHashMap<String, Object>>) bootstrap.get("teams"));

        ArrayList<Object> fixtures = fplClient.fixtures();
        fixtures.forEach(f -> {
            FplFixture fplFixture = objectMapper.convertValue(f,FplFixture.class);
            if(fplFixture.getGameweek() != null && fplFixture.getGameweek() >= begin && fplFixture.getGameweek() <= end){
                Integer homeTeam = fplFixture.getHomeTeam();
                Integer awayTeam = fplFixture.getAwayTeam();
                Integer homeTeamDifficulty = fplFixture.getHomeTeamDifficulty();
                Integer awayTeamDifficulty = fplFixture.getAwayTeamDifficulty();

                SingleFixture homeTeamFixture = new SingleFixture(fplFixture.getGameweek(),teamFixturesHashMap.get(awayTeam).getShortName(),homeTeamDifficulty,true);
                SingleFixture awayTeamFixture = new SingleFixture(fplFixture.getGameweek(),teamFixturesHashMap.get(homeTeam).getShortName(),awayTeamDifficulty,false);
                teamFixturesHashMap.put(homeTeam,teamFixturesHashMap.get(homeTeam).addFixture(homeTeamFixture));
                teamFixturesHashMap.put(awayTeam,teamFixturesHashMap.get(awayTeam).addFixture(awayTeamFixture));
            }
        });

        return teamFixturesHashMap.values().stream().sorted(Comparators.teamFixturesComparator())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
