package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class AugmentNomenclature extends Nomenclature {

    private Map<String, List<Float>> dataValues;
}
