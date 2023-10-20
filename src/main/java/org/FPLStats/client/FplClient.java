package org.FPLStats.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class FplClient {
    public HashMap<String,Object> bootstrap(){
        RestTemplate restTemplate = new RestTemplate();
        String url =  "https://fantasy.premierleague.com/api/bootstrap-static";
        ResponseEntity<HashMap> res = restTemplate.getForEntity(url, HashMap.class);
        HashMap<String,Object> bootstrap = res.getBody();
        return bootstrap;
    }

    public HashMap<String,Object> gameweek(Integer gw){
        RestTemplate restTemplate = new RestTemplate();
        String url =  "https://fantasy.premierleague.com/api/event/" + gw + "/live";
        ResponseEntity<HashMap> res = restTemplate.getForEntity(url, HashMap.class);
        HashMap<String,Object> gameweek = res.getBody();
        return gameweek;
    }
}
