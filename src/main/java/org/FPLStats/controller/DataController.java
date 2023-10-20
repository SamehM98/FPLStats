package org.FPLStats.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.FPLStats.model.*;
import org.FPLStats.service.GameweekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gameweek")
public class DataController {
    @Autowired
    private GameweekService gameweekService;

    @GetMapping("/players")
    public ArrayList<? extends PlayerStats> gameweek(@RequestParam Integer begin, @RequestParam Integer end,
                                                     @RequestParam Integer position,
                                                     @RequestParam(required = false,defaultValue = "0") Integer team,
                                                     @RequestParam(required = false, defaultValue = "") String sort){
        return gameweekService.gameweekRange(begin,end,position,team,sort);
    }

    @GetMapping("/teams")
    public ArrayList<TeamStats> gameweekTeam(@RequestParam Integer begin,
                                             @RequestParam Integer end,
                                             @RequestParam(required = false) String sort){
        return gameweekService.gameweekRangeTeams(begin,end,sort);
    }
}

