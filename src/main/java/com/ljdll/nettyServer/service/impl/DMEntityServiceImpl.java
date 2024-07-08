package com.ljdll.nettyServer.service.impl;

import com.ljdll.nettyServer.entity.DMEntity;
import com.ljdll.nettyServer.mapper.DMEEntityMapper;
import com.ljdll.nettyServer.mapper.DMTableMapper;
import com.ljdll.nettyServer.service.DMEntityService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DMEntityServiceImpl extends ServiceImpl<DMEEntityMapper, DMEntity> implements DMEntityService {
    private final DMTableMapper dmTableMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transactionSaveTest() {
        DMEntity dmEntity = new DMEntity();
        dmEntity.setMessage("transactionTest");
        this.save(dmEntity);
        int a = 1 / 0;
        return true;
    }
}
