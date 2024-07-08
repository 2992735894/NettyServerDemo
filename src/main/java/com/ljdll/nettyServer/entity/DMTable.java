package com.ljdll.nettyServer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

@Data
@Table(value = "DM_TABLE", dataSource = "dm8")
public class DMTable {

    @Id(keyType = KeyType.Generator,value = KeyGenerators.flexId)
    private Long id;

    private String text;
}
