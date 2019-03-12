package com.pactera.business.controller;
import com.pactera.business.service.LaunCarouselService;
import com.pactera.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @Author zhaodong
 * @Date 2019/2/28 10:05
 */
@RestController
@Api(description = "轮播图管理")
@RequestMapping("carousel")
public class LaunCarouselController {

    @Resource
    private LaunCarouselService launCarouselService;

    /**
     * 添加轮播图
     * @Author zhaodong
     * @Date 13:44 2019/2/28
     * @Param carouselJson
     * @return
     **/
    @ApiOperation("添加轮播图")
    @PostMapping("addCarousel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carouselJsonAdd", value = "添加参数",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> addCarousel(@NotNull String carouselJsonAdd){
        ResponseEntity<ResultData> resultData = launCarouselService.addCarousel(carouselJsonAdd,0);
        return resultData;
    }

    /**
     * 编辑轮播图
     * @Author zhaodong
     * @Date 15:47 2019/2/28
     * @Param carouselJsonUp
     * @return
     **/
    @ApiOperation("编辑轮播图")
    @PostMapping("upCarousel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carouselJsonUp", value = "编辑参数",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> upCarousel(@NotNull String carouselJsonUp){
        ResponseEntity<ResultData> resultData = launCarouselService.addCarousel(carouselJsonUp,1);
        return resultData;
    }

    /**
     * 轮播图列表
     * @Author zhaodong
     * @Date 17:02 2019/2/28
     * @Param 
     * @return 
     **/
    @ApiOperation("轮播图列表")
    @GetMapping("carouselList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formatId", value = "格式id",paramType="query"),
            @ApiImplicitParam(name = "version", value = "最低试用版本",paramType="query"),
            @ApiImplicitParam(name = "status", value = "状态，1-上架，0-下架",paramType="query"),
            @ApiImplicitParam(name = "position", value = "轮播位",paramType="query"),
            @ApiImplicitParam(name = "title", value = "轮播名",paramType="query"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",paramType="query")
    })
    public ResponseEntity<ResultData> carouselList(int formatId, double version, String status, String position, String title, @RequestParam(defaultValue = "1") int pageIndex,@RequestParam(defaultValue = "10")int pageSize){
        ResponseEntity<ResultData> resultData = launCarouselService.carouselList(formatId, version, status, position, title, pageIndex, pageSize);
        return resultData;
    }
    /**
     * 删除轮播图
     * @Author zhaodong
     * @Date 16:48 2019/2/28
     * @Param id
     * @return
     **/
    @ApiOperation("删除轮播图")
    @PostMapping("deCarousel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> deCarousel(@NotNull String id){
        ResponseEntity<ResultData> resultData = launCarouselService.deCarousel(id);
        return resultData;
    }

    /**
     * 轮播图详情
     * @Author zhaodong
     * @Date 10:01 2019/3/1
     * @Param
     * @return
     **/
    @ApiOperation("轮播图详情")
    @GetMapping("carouselInfoById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> carouselInfoById(@NotNull String id){
        ResponseEntity<ResultData> resultData = launCarouselService.carouselInfoById(id);
        return resultData;
    }

    /**
     * 轮播图上线下线（status=1为上线，status=0下线）
     * @Author zhaodong
     * @Date 10:08 2019/3/1
     * @Param
     * @return
     **/
    @ApiOperation("轮播图上线下线")
    @PostMapping("carouselUpOrDown")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true),
            @ApiImplicitParam(name = "status", value = "状态1-上线，2-下线",paramType="query",required = true),
    })
    public ResponseEntity<ResultData> carouselUpOrDown(String id,String status){
        ResponseEntity<ResultData> resultData = launCarouselService.carouselUpOrDown(id, status);
        return resultData;
    }

    /**
     * 创建副本
     * @Author zhaodong
     * @Date 10:41 2019/3/1
     * @Param
     * @return
     **/
    @ApiOperation("创建副本")
    @PostMapping("carouselCopy")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> carouselCopy(String id){
        return null;
    }

    
}
