package com.pactera.business.controller;
import com.pactera.business.service.LauncThemeClassificationV2Service;
import com.pactera.result.ResultData;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;


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
    @ApiOperation("添加主题分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "themeClassName", value = "主题名称",paramType="query",required = true),
            @ApiImplicitParam(name = "apiKey", value = "apiKey",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> addthemeClass(String themeClassName, String apiKey, @ApiParam(name = "coverImage",value="上传文件",required=true) MultipartFile coverImage) {
        ResultData ResultData = new ResultData();
        if (themeClassName == null){
            ResultData resultData = new ResultData(500,"themeClassName不能为空");
            return  ResponseEntity.ok(ResultData);
        }
        if (apiKey == null){
            ResultData resultData = new ResultData(500,"apiKey不能为空");
            return  ResponseEntity.ok(ResultData);
        }
        if (coverImage == null){
            ResultData resultData = new ResultData(500,"coverImage不能为空");
            return  ResponseEntity.ok(ResultData);
        }
        ResponseEntity<ResultData> resultDataResponseEntity = launcThemeClassificationService.addthemeClass(themeClassName, apiKey, coverImage);
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
    @ApiOperation("编辑主题分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "themeClassName", value = "主题名称",paramType="query",required = true),
            @ApiImplicitParam(name = "id", value = "主题Id",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> upThemeClass(String themeClassName, String id, @ApiParam(name = "coverImage",value="上传文件",required=true) MultipartFile coverImage) {

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
    @ApiOperation("删除主题分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主题Id",paramType="query",required = true),
            @ApiImplicitParam(name = "apieKey", value = "租户Id",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> deThemeClass(String id,String apieKey) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.deThemeClass(id,apieKey);
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
    @ApiOperation("查看主题分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主题Id",paramType="query",required = true),
    })
    public ResponseEntity<ResultData> seThemeClass(String id) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.seThemeClass(id);
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
    @ApiOperation("查看主题分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey",paramType="query",required = true),
            @ApiImplicitParam(name = "shelfStatus", value = "是否上架，(1-上架，0-下架)",paramType="query"),
            @ApiImplicitParam(name = "classificationName", value = "查询主题名",paramType="query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小",paramType="query")
    })
    public ResponseEntity<ResultData> seThemeClassList(String apiKey,String shelfStatus,String classificationName,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "4") int pageSize) {
        if (apiKey==null){
            ResultData resultData = new ResultData(500,"apiKey");
            return  ResponseEntity.ok(resultData);
        }
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.seThemeClassList(apiKey,shelfStatus,classificationName,pageNum,pageSize);
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
    @ApiOperation("主题上架或下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiKey", value = "apiKey",paramType="query",required = true),
            @ApiImplicitParam(name = "shelfStatus", value = "是否上架，(1-上架，0-下架)",paramType="query",required = true),
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> themeClassUpOrDown(String apiKey,String shelfStatus,String id) {
        ResponseEntity<ResultData> launcThemeClass = launcThemeClassificationService.themeClassUpOrDown(apiKey,shelfStatus,id);
        return  launcThemeClass;
    }

    /**
     * 主题权重排序问题
     * @Author zhaodong
     * @Date 13:45 2018/12/24
     * @Param
     * @return
     **/
    /**
     * 该分类是否有上架主题
     * @Author zhaodong
     * @Date 14:57 2018/12/21
     * @Param
     * @return
     **/
    @PostMapping("orderThemeClass")
    @ApiOperation("排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortOrder", value = "排序Json",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> orderThemeClass(String sortOrder) {
        return  null;
    }

    /**
     * 该分类是否有上架主题
     * @Author zhaodong
     * @Date 14:57 2018/12/21
     * @Param
     * @return
     **/
    @GetMapping("themeClassIsUpOrDown")
    @ApiOperation("主题是否有上架的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true)
    })
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
    @ApiOperation("更新分类下的主题总数(仅内部开发使用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "操作主题状态，(1-添加，0-删除)",paramType="query",required = true),
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true),
            @ApiImplicitParam(name = "num", value = "更新的数量",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> upThemeClassCount(int status,String id,int num) {
        String s = launcThemeClassificationService.upThemeClassCount(status, id, num);
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
    @ApiOperation("更新分类下的以上架的主题数(仅内部开发使用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "操作主题状态，(1-上架，0-下架)",paramType="query",required = true),
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required = true),
            @ApiImplicitParam(name = "num", value = "上下架的数量",paramType="query",required = true)
    })
    public ResponseEntity<ResultData> upThemeClassCountUpOrDown(int status,String id,int num) {
        String s = launcThemeClassificationService.upThemeClassCountUpOrDown(status, id, num);
        ResultData date = new ResultData();
        date.setData(s);
        return  ResponseEntity.ok(date);
    }


}
