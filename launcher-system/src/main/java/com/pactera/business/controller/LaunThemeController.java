package com.pactera.business.controller;

import com.pactera.business.service.LaunFontService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.constant.ValidMessage;
import com.pactera.domain.LaunFont;
import com.pactera.result.ResultData;
import com.pactera.valid.ThemeSaveValidator;
import com.pactera.valid.annotation.ThemeStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 主题管理
 * @author:xukj
 * @since:2018年12月24日
 */
@RestController
@RequestMapping("/theme")
@Validated
public class LaunThemeController {

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunFontService launFontService;

	/**
	 * v2
	 * 根据条件去查询主题列表
	 * @param type 主题分类
	 * @param title 主题名称
	 * @param status 主题状态
	 * @param pageNum 第几页
	 * @param pageSize 每页条数
	 * @return
	 */
	@GetMapping("/query")
	public ResultData query(String type, String title, Integer status,
                            @RequestParam(defaultValue = "1") int pageNum,
			                @RequestParam(defaultValue = "10") int pageSize) {
		return new ResultData(launThemeService.query(type, title, status, pageNum, pageSize));
	}


	/**
	 * 添加字体
	 * 
	 * @author LL
	 * @date 2018年7月10日 上午10:29:25
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("addFont")
	@ApiOperation("添加字体")
	@ApiImplicitParams({ @ApiImplicitParam(name = "fontName", value = "字体名称"),
			@ApiImplicitParam(name = "filePath", value = "文件地址") })
	public ResponseEntity<ResultData> addaFont(String fontName, String filePath) {
		launFontService.addFont(fontName, filePath);
		return ResponseEntity.ok(new ResultData());
	}

	/**
	 * 查询字体列表
	 * 
	 * @author LL
	 * @date 2018年7月10日 上午10:31:54
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@GetMapping("selectFont")
	@ApiOperation("查询字体列表")
	public ResponseEntity<ResultData> selectFont(Long id) {
		List<LaunFont> list = launFontService.getList();
		return ResponseEntity.ok(new ResultData(list));
	}

    /**
	 * v2
     * 根据id查询主题
     * @param id
     * @return
     */
	@GetMapping("/detail/{id}")
	public ResultData detail(@PathVariable String id) {
		return new ResultData(launThemeService.selectById(id));
	}

	/**
     * v2
	 * @description 修改状态
	 * 状态类型有：上架/下架（2，3）  删除（-1） 禁用/启用【未上架】（0，1）
	 *
	 * @author xukj
	 * @param ids
	 * @param status
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/status")
	public ResultData modifyStatus(@NotNull(message = ValidMessage.ID_NOT_NULL) String[] ids, @ThemeStatus Integer status) {
		return new ResultData(launThemeService.changeStatus(Arrays.asList(ids), status));
	}

    /**
     * v2
     * 修改车机端主题排序权重
     * @param id 主题id
     * @param num 权重数
     * @return
     */
    @PostMapping("/sort")
    public ResultData sort(@NotNull(message = ValidMessage.FILE_NOT_NULL) String id, @NotNull(message = ValidMessage.SORT_NOT_NULL) @Min(1) Long num) {
		return new ResultData(launThemeService.sort(id, num));
    }

    /**
     * v2
     * zip上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResultData upload(@NotNull(message = ValidMessage.FILE_NOT_NULL)MultipartFile file) {
		return new ResultData(launThemeService.upload(file));
    }

	/**
     * v2
	 * 主题推荐
	 * @param id 主题id
     * @param value 0:不推荐 1:推荐
	 * @return
	 */
	@PostMapping("/recommend")
	public ResultData recommend(@NotNull(message = ValidMessage.ID_NOT_NULL) String id, @NotNull(message = ValidMessage.RECOMMEND_NOT_NULL) boolean value) {
		return new ResultData(launThemeService.recommend(id, value));
	}

    /**
     * v2
     * 修改车机端主题排序权重
     * @param id 主题id
     * @param num 权重数
     * @return
     */
    @PostMapping("/recommend/sort")
    public ResultData recommentSort(@NotNull(message = ValidMessage.ID_NOT_NULL) String id, @NotNull(message = ValidMessage.SORT_NOT_NULL) @Min(1) Integer num) {
        return new ResultData(launThemeService.recommendSort(id, num));
    }

	@Autowired
	private ThemeSaveValidator validator;

	/**
     *
     * v2
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param themeJson 主题内容 json
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/saveTheme")
	public ResultData saveTheme(@NotNull(message = "themeJson不可为空") String themeJson) {
		return new ResultData(launThemeService.saveTheme(null, null, themeJson, 0));
	}

	/**
     *
     * v2
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param themeJson 主题内容json
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/updateThem")
	public ResponseEntity<ResultData> updateTheme(@NotNull(message = "themeJson不可为空") String themeJson ) {
	    //2019/1/4 xukj change start
		//String i = launThemeService.updateTheme(baseJson, widgetJson, themeJson, saveType);
        String i = launThemeService.saveTheme(null, null, themeJson, 0);
        //2019/1/4 xukj change end

		/*
		 * if (null != administration.getId()) { i =
		 * launThemeService.updateOrSaveTheme(administration); } else { }
		 */
		return ResponseEntity.ok(new ResultData(i));
	}

	@PostMapping("/autoUpDown")
    public void autoUpDown(String timestamp){
        launThemeService.themeAutoUpDown(timestamp);
    }

}
