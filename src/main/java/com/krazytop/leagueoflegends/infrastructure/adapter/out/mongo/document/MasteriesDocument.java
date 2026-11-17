package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Mastery")
public class MasteriesDocument {

    @Id
    private String puuid;
    private List<MasterySubdocument> champions = new ArrayList<>();
}
