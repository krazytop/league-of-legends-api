package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper;

import com.krazytop.leagueoflegends.domain.model.reference.Patch;
import com.krazytop.leagueoflegends.domain.model.reference.RunePerkReference;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference.PatchDocument;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document.reference.RunePerkReferenceSubdocument;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PatchMongoMapper {

    Patch toModel(PatchDocument document);

    List<RunePerkReference> toModelList(List<RunePerkReferenceSubdocument> perkList);

    List<List<RunePerkReference>> toModelListList(List<List<RunePerkReferenceSubdocument>> perkList);
}
