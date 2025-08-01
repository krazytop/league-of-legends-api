package com.krazytop.leagueoflegends.nomenclature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueNomenclature {

    private String id;
    private String name;
    private String description;

}
