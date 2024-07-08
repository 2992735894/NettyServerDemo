package com.ljdll.nettyServer.service.impl;

import com.ljdll.nettyServer.entity.MySqEntity;
import com.ljdll.nettyServer.mapper.MySqlMapper;
import com.ljdll.nettyServer.service.MySqlService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MySqlServiceImpl extends ServiceImpl<MySqlMapper, MySqEntity> implements MySqlService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transactionSaveTest() {
        MySqEntity entity = new MySqEntity();
        entity.setMessage("transactionTest");
        this.save(entity);
        int a = 1 / 0;
        return true;
    }
}
