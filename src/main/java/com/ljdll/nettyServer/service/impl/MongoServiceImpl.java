package com.ljdll.nettyServer.service.impl;

import com.ljdll.nettyServer.entity.MongoEntity;
import com.ljdll.nettyServer.service.MongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService {
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionTest() {
        MongoEntity entity = new MongoEntity();
        entity.setMessage("mess");
        mongoTemplate.save(entity);
        int a = 1 / 0;
    }
}
