package org.FPLStats.controller;

import org.FPLStats.model.response.PlayerStatsResponse;
import org.FPLStats.model.response.ResponseDto;
import org.FPLStats.service.GameweekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gameweek")
public class GameweekController {
    @Autowired
    private GameweekService gameweekService;

    @GetMapping("/players")
    public PlayerStatsResponse gameweek(@RequestParam (required = false,defaultValue = "0") Integer begin, @RequestParam (required = false,defaultValue = "0") Integer end,
                                        @RequestParam Integer position,
                                        @RequestParam(required = false,defaultValue = "0") Integer team,
                                        @RequestParam(required = false, defaultValue = "") String sort){
        return gameweekService.gameweekRange(begin,end,position,team,sort);
    }

    @GetMapping("/teams")
    public ResponseDto gameweekTeam(@RequestParam (required = false,defaultValue = "0") Integer begin,
                                    @RequestParam (required = false,defaultValue = "0") Integer end,
                                    @RequestParam(required = false, defaultValue = "") String sort){
        return gameweekService.gameweekRangeTeams(begin,end,sort);
    }
}

