package com.ljdll.nettyServer.config.mybatisFlex;

import com.mybatisflex.core.audit.AuditManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MyBatisFlexConfiguration {

    public MyBatisFlexConfiguration() {
        AuditManager.setAuditEnable(true);
        AuditManager.setMessageCollector(auditMessage -> log.info("{},{}ms",auditMessage.getFullSql(),auditMessage.getElapsedTime()));
    }
}
