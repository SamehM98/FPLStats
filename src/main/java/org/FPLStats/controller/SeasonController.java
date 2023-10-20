package org.FPLStats.controller;

import org.FPLStats.model.Attacker;
import org.FPLStats.model.TeamStats;
import org.FPLStats.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/season")
public class SeasonController {
    @Autowired
    private SeasonService seasonService;

    @GetMapping("/players")
    public ArrayList<?> players(@RequestParam(required = false,defaultValue = "") String sort,
                                         @RequestParam Integer position,
                                         @RequestParam(required = false,defaultValue = "0") Integer team){
        return seasonService.playerSeasonData(sort,position,team);
    }

    @GetMapping("/teams")
    public ArrayList<TeamStats> teams(@RequestParam(required = false,defaultValue = "") String sort){
        return seasonService.teamSeasonData(sort);
    }
}
