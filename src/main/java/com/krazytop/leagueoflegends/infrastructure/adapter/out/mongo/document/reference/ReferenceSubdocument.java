package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference;

import lombok.Data;

@Data
public abstract class ReferenceSubdocument {

    private String id;
    private String name;
    private String image;
    private String description;
}
