package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.common.constant.R;
import com.ljdll.nettyServer.entity.MongoEntity;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/mongo")
@RequiredArgsConstructor
public class MongoController {
    private final MongoTemplate mongoTemplate;

    @PostMapping("/insert")
    public R<Boolean> insert(@RequestBody MongoEntity entity) {
//        mongoTemplate.insert(entity);
        mongoTemplate.insert(Collections.singleton(entity), "ljdll");
        return R.ok(true);
    }

    @PostMapping("/save")
    public R<Boolean> save(@RequestBody MongoEntity entity) {
//        mongoTemplate.save(entity);
        mongoTemplate.save(Collections.singleton(entity), "ljdll");
        return R.ok(true);
    }

    @PutMapping("/update")
    public R<UpdateResult> update(@RequestBody MongoEntity entity) {
        Query query = new Query(Criteria.where("id").in(entity.getId()));
        Update update = new Update().set("message", entity.getMessage());
//        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, MongoEntity.class);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, MongoEntity.class);
        if (updateResult.getMatchedCount() == 0) {
            return R.fail("未找到该条数据");
        }
        return R.ok(updateResult);
    }

    @DeleteMapping("/deleteById/{id}")
    public R<DeleteResult> deleteById(@PathVariable String id) {
        Query query = new Query(Criteria.where("id").in(id));
        DeleteResult deleteResult = mongoTemplate.remove(query, MongoEntity.class);
//        MongoEntity entity = mongoTemplate.findAndRemove(query, MongoEntity.class);
        if (deleteResult.getDeletedCount() == 0) {
            return R.fail("未找到该条数据");
        }
        return R.ok(deleteResult);
    }

    @GetMapping("/list")
    public R<List<MongoEntity>> list(String id) {
        if (StringUtils.hasText(id)) {
            return R.ok(mongoTemplate.find(new Query(Criteria.where("id").in(id)), MongoEntity.class));
        }
        return R.ok(mongoTemplate.findAll(MongoEntity.class));
    }

    @GetMapping("/getOne")
    public R<MongoEntity> getOne(String id) {
        return R.ok(mongoTemplate.findOne(new Query(Criteria.where("id").in(id)), MongoEntity.class));
    }

    @GetMapping("/page")
    public R<List<MongoEntity>> page(Integer pageNum, Integer pageSize) {
        return R.ok(mongoTemplate.find(new Query().skip((long) (pageNum - 1) * pageSize).limit(pageSize), MongoEntity.class));
    }

}
