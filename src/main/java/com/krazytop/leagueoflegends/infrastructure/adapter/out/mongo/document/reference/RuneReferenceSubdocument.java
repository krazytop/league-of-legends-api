package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RuneReferenceSubdocument extends ReferenceSubdocument {

    private List<List<RunePerkReferenceSubdocument>> perks = new ArrayList<>();
}
