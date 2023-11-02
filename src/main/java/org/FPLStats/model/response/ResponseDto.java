package org.FPLStats.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private ArrayList<?> stats;

    private ArrayList<String> comparators;

    private Integer currentGameweek;

    private Integer begin;

    private Integer end;
}
