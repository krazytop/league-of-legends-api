package com.krazytop.leagueoflegends.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "Board")
public class Board {
    private String id;
    private List<String> puuids = new ArrayList<>();
    private Date updateDate;
    private String name;

    public Board() {
        this.setId(String.valueOf(UUID.randomUUID()));
        this.setName("My Board");
        this.setUpdateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }
}
