package com.zh.server.controller;


import com.zh.server.config.BasicConstants;
import com.zh.server.entity.Position;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Api(tags = "职位管理")
@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation(value = "获取职位信息")
    @GetMapping("/")
    private ResponseBase<Position> getAllPosition(){
        return new ResponseBase().success(positionService.listALL());
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    private ResponseBase addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)){
            return new ResponseBase().success("添加成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,
                BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }

    @ApiOperation(value = "修改职位信息")
    @PutMapping("/")
    private ResponseBase updatePosition(@RequestBody Position position){
        if (positionService.updateById(position)){
            return new ResponseBase().success("修改成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,
                BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    private ResponseBase deletePosition(@PathVariable Integer id){
        if (positionService.removeById(id)){
            return new ResponseBase().success("删除成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,
                BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseBase deletePositionByIds(Integer[] ids){
        if (positionService.removeByIds(Arrays.asList(ids))){
            return new ResponseBase().success("批量删除成功");
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,
                BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }
}
