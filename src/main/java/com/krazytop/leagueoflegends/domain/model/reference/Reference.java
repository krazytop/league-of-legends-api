package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;

@Data
public abstract class Reference {

    private String id;
    private String name;
    private String image;
    private String description;
}
