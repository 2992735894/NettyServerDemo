package com.ljdll.nettyServer.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ljdll")
@Data
public class MongoEntity {

    @Id
    private String id;

    private String message;
}
