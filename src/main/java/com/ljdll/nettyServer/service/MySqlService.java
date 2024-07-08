package com.ljdll.nettyServer.service;

import com.ljdll.nettyServer.entity.MySqEntity;
import com.mybatisflex.core.service.IService;

public interface MySqlService extends IService<MySqEntity> {

    boolean transactionSaveTest();
}
