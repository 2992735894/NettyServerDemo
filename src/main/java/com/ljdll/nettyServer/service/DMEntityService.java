package com.ljdll.nettyServer.service;

import com.ljdll.nettyServer.entity.DMEntity;
import com.mybatisflex.core.service.IService;

public interface DMEntityService extends IService<DMEntity> {

    boolean transactionSaveTest();
}
