package com.zh.server.controller;


import com.zh.server.config.BasicConstants;
import com.zh.server.entity.Joblevel;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.JoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Api(tags = "职称接口")
@RestController
@RequestMapping("/joblevel")
public class JoblevelController {

    @Autowired
    private JoblevelService joblevelService;

    @ApiOperation(value = "查询所有职称")
    @GetMapping("/")
    public ResponseBase<Joblevel> getAllJoblevel(){

        return ResponseBase.success(joblevelService.list());
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public ResponseBase addJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)){
            return ResponseBase.success("添加成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code,BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @ApiOperation(value = "修改职称")
    @PutMapping("/")
    public ResponseBase updateJoblevel(@RequestBody Joblevel joblevel){
        System.out.println(joblevel);
        if (joblevelService.updateById(joblevel)){
            return ResponseBase.success("修改成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code,BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public ResponseBase deleteJobLevel(@PathVariable Integer id){
        if (joblevelService.removeById(id)){
            return ResponseBase.success("删除成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code,BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public ResponseBase deleteJoblevelByIds(Integer[] ids){
        if (joblevelService.removeByIds(Arrays.asList(ids))){
            return ResponseBase.success("删除成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code,BasicConstants.HttpStatus.SQL_ERROR.msg);
    }
}
