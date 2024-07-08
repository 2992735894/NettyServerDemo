package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.common.constant.R;
import com.ljdll.nettyServer.entity.MySqEntity;
import com.ljdll.nettyServer.service.MySqlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysql")
@RequiredArgsConstructor
public class MySqlController {

    private final MySqlService mySqlService;

    @PostMapping("/save")
    public R<Boolean> save(@RequestBody MySqEntity entity){
        return R.ok(mySqlService.save(entity));
    }

    @PostMapping("/transactionTest")
    public R<Boolean> transactionTest(){
        return R.ok(mySqlService.transactionSaveTest());
    }
}
