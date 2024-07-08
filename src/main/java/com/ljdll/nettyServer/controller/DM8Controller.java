package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.common.constant.R;
import com.ljdll.nettyServer.entity.DMEntity;
import com.ljdll.nettyServer.service.DMEntityService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dm8")
@RequiredArgsConstructor
public class DM8Controller {
    private final DMEntityService dmEntityService;

    @PostMapping("/save")
    public R<Boolean> save(@RequestBody DMEntity entity) {
        return R.ok(dmEntityService.save(entity));
    }

    @GetMapping("/page")
    public R<Page<DMEntity>> page(Page<DMEntity> page, DMEntity entity) {
        return R.ok(dmEntityService.page(page, QueryWrapper.create().eq(entity.getMessage(), entity.getMessage(), StringUtils.hasText(entity.getMessage()))));
    }

    @PutMapping("/update")
    public R<Boolean> update(@RequestBody DMEntity dmEntity){
        return R.ok(dmEntityService.updateById(dmEntity));
    }

    @PostMapping("/transactionTest")
    public R<Boolean> transactionTest(){
        return R.ok(dmEntityService.transactionSaveTest());
    }
}
