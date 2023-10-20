package org.FPLStats.controller;

import org.FPLStats.client.FplClient;
import org.FPLStats.service.BootstrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/bootstrap")
public class BootstrapController {

    @Autowired
    private BootstrapService bootstrapService;
    @Autowired
    private FplClient fplClient;

    @GetMapping("/info")
    public HashMap<String, HashMap<Integer, Object>> bootstrapInfo(){
        return bootstrapService.bootstrapInfo(fplClient.bootstrap());
    }
}
