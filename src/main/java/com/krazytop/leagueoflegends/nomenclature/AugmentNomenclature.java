package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class AugmentNomenclature extends Nomenclature {

    private Map<String, Float> dataValues;
}
