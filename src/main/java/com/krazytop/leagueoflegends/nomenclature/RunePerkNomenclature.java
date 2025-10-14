package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RunePerkNomenclature extends Nomenclature {

    private String longDescription;

}