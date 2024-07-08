package com.ljdll.nettyServer.mapper;

import com.ljdll.nettyServer.entity.DMEntity;
import com.mybatisflex.annotation.UseDataSource;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@UseDataSource("dm8")
public interface DMEEntityMapper extends BaseMapper<DMEntity> {
}
