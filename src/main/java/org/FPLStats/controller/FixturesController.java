package org.FPLStats.controller;

import org.FPLStats.model.response.ResponseDto;
import org.FPLStats.service.FixturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FixturesController {
    @Autowired
    private FixturesService fixturesService;

    @GetMapping("/fixtures")
    public ResponseDto fixtures(@RequestParam Integer begin, @RequestParam Integer end){
        return fixturesService.fixturesRange(begin,end);
    }
}
