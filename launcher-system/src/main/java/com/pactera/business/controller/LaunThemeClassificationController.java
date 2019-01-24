package com.pactera.business.controller;
import com.pactera.business.service.LauncThemeClassificationV2Service;
import com.pactera.result.ResultData;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.List;

/**
 *  主题分类管理
 * @author:zhaodong
 * @since:2018年12月19日 上午14:29:39
 */
@RestController
@Api(description = "主题分类管理")
@RequestMapping("themeclass")
public class LaunThemeClassificationController {

    @Resource
    private LauncThemeClassificationV2Service launcThemeClassificationService;

    /**
     * 主题分类添加
     * @Author zhaodong
     * @Date 12:07 2018/12/20
     * @Param themeClassName,themeClassName,coverImage
     * @return ResponseEntity<ResultData>
     **/
    @PostMapping("addthemeclass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "themeClassName", value = "主题分类名",paramType="query"),
    })
    public ResponseEntity<ResultData> addthemeClass(String themeClassName, @ApiParam(name = "coverImage",value="上传文件",required=true) MultipartFile coverImage) {

        if (StringUtils.isBlank(themeClassName)){
            ResultData resultData = new ResultData(400,"themeClassName不能为空");
            return  ResponseEntity.ok(resultData);
        }
        if (coverImage == null){
            ResultData resultData = new ResultData(400,"coverImage不能为空");
            return  ResponseEntity.ok(resultData);
        }
        ResponseEntity<ResultData> resultDataResponseEntity = launcThemeClassificationService.addthemeClass(themeClassName, coverImage);
        return  resultDataResponseEntity;
    }


    /**
     * 编辑主题分类
     * @Author zhaodong
     * @Date 11:42 2018/12/20
     * @Param themeClassName,themeClassId,coverImage
     * @return ResponseEntity<ResultData>
     **/
    @PostMapping("upThemeClass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "themeClassName", value = "主题分类名",paramType="query"),
            @ApiImplicitParam(name = "id", value = "分类id",paramType="query"),
            @ApiImplicitParam(name = "coverImage", value = "地址",paramType="query")
    })
    public ResponseEntity<ResultData> upThemeClass(String themeClassName, String id,  String coverImage) {
        ResponseEntity<ResultData> upThemeClass = launcThemeClassificationService.upThemeClass(themeClassName, id, coverImage);
        return  upThemeClass;
    }

    /**
     * 删除主题分类
     * @Author zhaodong
     * @Date 14:22 2018/12/20
     * @Param themeClassId
     * @return
     **/
    @PostMapping("deThemeClass")
    public ResponseEntity<ResultData> deThemeClass(String id) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.deThemeClass(id);
        return  launcThemeClass;
    }

    /**
     * 查看主题分类
     * @Author zhaodong
     * @Date 14:22 2018/12/20
     * @Param themeClassId
     * @return
     **/
    @GetMapping("seThemeClass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主题分类id",paramType="query"),
            @ApiImplicitParam(name = "pageNum", value = "页码",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",paramType="query"),

    })
    public ResponseEntity<ResultData> seThemeClass(String id, @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "4") int pageSize) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.seThemeClass(id, pageNum, pageSize);
        return  launcThemeClass;
    }

    /**
     * 主题分类列表
     * @Author zhaodong
     * @Date 14:22 2018/12/20
     * @Param themeClassId
     * @return
     **/
    @GetMapping("seThemeClassList")
    public ResponseEntity<ResultData> seThemeClassList(String shelfStatus,String classificationName,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "4") int pageSize) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.seThemeClassList(shelfStatus,classificationName,pageNum,pageSize);
        return  launcThemeClass;
    }

     /**
      * 主题上架下架
      * @Author zhaodong
      * @Date 13:34 2018/12/21
      * @Param
      * @return
      **/
    @PostMapping("themeClassUpOrDown")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shelfStatus", value = "1-上架，0下架",paramType="query"),
            @ApiImplicitParam(name = "id", value = "分类id",paramType="query"),
    })
    public ResponseEntity<ResultData> themeClassUpOrDown(String shelfStatus,String id) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.themeClassUpOrDown(shelfStatus,id);
        return  launcThemeClass;
    }

    /**
     * 主题权重排序问题(单个排序)
     * @Author zhaodong
     * @Date 13:45 2018/12/24
     * @Param
     * @return
     **/
    @PostMapping("orderThemeClass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "权重号",paramType="query"),
            @ApiImplicitParam(name = "id", value = "分类id",paramType="query"),
    })
    public ResponseEntity<ResultData> orderThemeClass(String id,int num) {
        ResponseEntity<ResultData> resultDataResponseEntity = launcThemeClassificationService.updateThemTypeOrder(id,num);
        return  resultDataResponseEntity;
    }

   /* *
     * 主题权重排序问题(整体排序)
     * @Author zhaodong
     * @Date 13:45 2018/12/24
     * @Param
     * @return
     **/
    @PostMapping("orderThemeClassNum")
    public ResponseEntity<ResultData> orderThemeClassNum(String sortOrder) {
        ResponseEntity<ResultData> resultDataResponseEntity = launcThemeClassificationService.orderThemeClass(sortOrder);
        return  resultDataResponseEntity;
    }

    /**
     * 该分类是否有上架主题
     * @Author zhaodong
     * @Date 14:57 2018/12/21
     * @Param
     * @return
     **/
    @GetMapping("themeClassIsUpOrDown")
    public ResponseEntity<ResultData> themeClassIsUpOrDown(String id) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.themeClassIsUpOrDown(id);
        return  launcThemeClass;
    }

    /**
     * 更新分类下的主题总数(供删除(批量删除)主题，添加主题等接口调用)
     * @Author zhaodong
     * @Date 15:31 2018/12/21
     * @Param
     * @return
     **/
    @PostMapping("upThemeClassCount")
    public ResponseEntity<ResultData> upThemeClassCount(int status, @RequestParam(value = "idList")List<String> id) {
        String s = launcThemeClassificationService.upThemeClassCount(status, id);
        ResultData date = new ResultData();
        date.setData(s);
        return  ResponseEntity.ok(date);
    }

    /**
     * 更新该分类下的以上架的主题数(供下架(批量下架)主题，上架(批量上架主题)主题等接口调用)
     * @Author zhaodong
     * @Date 15:31 2018/12/21
     * @Param
     * @return
     **/
    @PostMapping("upThemeClassCountUpOrDown")
    public ResponseEntity<ResultData> upThemeClassCountUpOrDown(int status, @RequestParam(value = "idList") @ApiParam(value = "id列表") List<String> id) {
        String s = launcThemeClassificationService.upThemeClassCountUpOrDown(status, id);
        ResultData date = new ResultData();
        date.setData(s);
        return  ResponseEntity.ok(date);
    }

    /**
     * 主题应用的下拉主题分类列表
     * @Author zhaodong
     * @Date 17:13 2019/1/14
     * @Param
     * @return
     **/
    @GetMapping("themeClassByTid")
    public ResponseEntity<ResultData> themeClassByTid(){
        ResponseEntity<ResultData> resultDataResponseEntity = launcThemeClassificationService.themeClassByTid();
        return resultDataResponseEntity;
    }


}
