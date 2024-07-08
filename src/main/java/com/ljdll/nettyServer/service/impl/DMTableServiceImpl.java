package com.ljdll.nettyServer.service.impl;

import com.ljdll.nettyServer.entity.DMTable;
import com.ljdll.nettyServer.mapper.DMTableMapper;
import com.ljdll.nettyServer.service.DMTableService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DMTableServiceImpl extends ServiceImpl<DMTableMapper, DMTable> implements DMTableService {
}
