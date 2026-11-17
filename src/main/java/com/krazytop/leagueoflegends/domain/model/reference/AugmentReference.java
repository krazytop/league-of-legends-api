package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class AugmentReference extends Reference {

    private Map<String, List<Float>> dataValues = new HashMap<>();
}
