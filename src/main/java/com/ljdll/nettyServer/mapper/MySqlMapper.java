package com.ljdll.nettyServer.mapper;

import com.ljdll.nettyServer.entity.MySqEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MySqlMapper extends BaseMapper<MySqEntity> {
}
