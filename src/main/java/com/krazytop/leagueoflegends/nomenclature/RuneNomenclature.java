package com.krazytop.leagueoflegends.nomenclature;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuneNomenclature extends Nomenclature {

    private List<List<RunePerkNomenclature>> perks = new ArrayList<>();
}
