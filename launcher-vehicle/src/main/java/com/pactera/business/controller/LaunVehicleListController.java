package com.pactera.business.controller;
import com.pactera.business.service.LaunListService;
import com.pactera.result.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhaodong
 * @Date 2019/1/7
 */
@Api(description = "车机接口")
@RestController
public class LaunVehicleListController  {

    @Autowired
    private LaunListService launListService;

    /**
     * 主题分类列表
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    @GetMapping("/themeclass/themeclasslist2")
    @ApiOperation("主题分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey",paramType="query"),
            @ApiImplicitParam(name = "layoutName", value = "layoutName",paramType="query"),
    })
    public ResponseEntity<ResultData> themeclasslist2(String apiKey,String layoutName){
        ResponseEntity<ResultData> resultDataResponse = launListService.themeclasslist2(apiKey, layoutName);
        return resultDataResponse;
    }

    /**
     * 1-全部主题，2-主题排行，3-推荐主题
     * @Author zhaodong
     * @Date 10:06 2019/1/7
     * @Param
     * @return
     **/
    @GetMapping("/them/themTopAndAll")
    @ApiOperation("主题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey",paramType="query",required = true),
            @ApiImplicitParam(name = "status", value = "1-全部主题，2-主题排行，3-推荐主题",paramType="query",required = true),
            @ApiImplicitParam(name = "pageNum", value = "当前页",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "页码大小",paramType="query"),
            @ApiImplicitParam(name = "version", value = "主题商店版本号",paramType="query" ),
            @ApiImplicitParam(name = "layoutName", value = "格式",paramType="query" ),

    })
    public ResponseEntity<ResultData> themTopAndAll(String apiKey, int status, @RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize, double version, String layoutName){
        ResponseEntity<ResultData> resultDataResponse = launListService.themTopAndAll(apiKey, status, pageNum, pageSize,version,layoutName);
        return resultDataResponse;
    }

    /**
     * 分类下的主题列表
     * @Author zhaodong
     * @Date 10:10 2019/1/7
     * @Param
     * @return
     **/
    @GetMapping("/them/themTopAndByClassId")
    @ApiOperation("分类下的主题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey",paramType="query",required = true),
            @ApiImplicitParam(name = "id", value = "分类id",paramType="query",required = true),
            @ApiImplicitParam(name = "pageNum", value = "当前页",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "页码大小",paramType="query"),
            @ApiImplicitParam(name = "version", value = "主题商店版本号",paramType="query")
    })
    public ResponseEntity<ResultData> themTopAndByClassId(String apiKey, String id, @RequestParam(defaultValue = "1")int pageNum, @RequestParam(defaultValue = "10")int pageSize,double version){
        ResponseEntity<ResultData> responseEntity = launListService.themTopAndByClassId(apiKey, id, pageNum, pageSize,version);
        return responseEntity;
    }


}
