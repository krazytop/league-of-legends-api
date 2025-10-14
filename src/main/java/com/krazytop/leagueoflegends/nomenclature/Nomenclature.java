package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;

@Data
public abstract class Nomenclature {

    private String id;
    private String name;
    private String image;
    private String description;
}
