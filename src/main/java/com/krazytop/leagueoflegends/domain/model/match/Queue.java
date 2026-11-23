package com.krazytop.leagueoflegends.domain.model.match;

import com.krazytop.leagueoflegends.domain.model.enums.EnumQueue;
import lombok.Data;

@Data
public class Queue {

    private String id;
    private EnumQueue type;
}
