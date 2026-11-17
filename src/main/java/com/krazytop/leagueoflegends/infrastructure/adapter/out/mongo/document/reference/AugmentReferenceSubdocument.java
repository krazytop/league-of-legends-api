package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class AugmentReferenceSubdocument extends ReferenceSubdocument {

    private Map<String, List<Float>> dataValues = new HashMap<>();
}
