package org.FPLStats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SingleFixture {
    private Integer gameweek;

    private String opponent;

    private Integer difficulty;

    private Boolean home;

    public SingleFixture(Integer gameweek, String opponent, Integer difficulty, Boolean home) {
        this.gameweek = gameweek;
        this.opponent = opponent;
        this.difficulty = difficulty;
        this.home = home;
    }
}
