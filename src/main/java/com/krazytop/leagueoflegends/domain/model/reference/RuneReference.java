package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RuneReference extends Reference {

    private List<List<RunePerkReference>> perks = new ArrayList<>();
}
