package org.FPLStats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamFixtures extends Team{
    private Integer totalDifficulty = 0;

    private ArrayList<SingleFixture> fixtures = new ArrayList<>();

    public TeamFixtures addFixture(SingleFixture singleFixture){
        ArrayList<SingleFixture> singleFixtures = this.getFixtures();
        singleFixtures.add(singleFixture);
        this.setFixtures(singleFixtures);
        this.setTotalDifficulty(this.getTotalDifficulty()+singleFixture.getDifficulty());

        return this;
    }
}
