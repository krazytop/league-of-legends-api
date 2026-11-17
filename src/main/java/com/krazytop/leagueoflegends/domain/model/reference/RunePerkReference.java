package com.krazytop.leagueoflegends.domain.model.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RunePerkReference extends Reference {

    private String longDescription;

}