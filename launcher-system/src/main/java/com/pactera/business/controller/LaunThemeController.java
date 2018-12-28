package com.pactera.business.controller;

import com.github.pagehelper.PageInfo;
import com.pactera.business.service.LaunFontService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.domain.LaunFont;
import com.pactera.result.ResultData;
import com.pactera.vo.LaunThemeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 主题管理
 * @author:xukj
 * @since:2018年12月24日
 */
@RestController
@RequestMapping("/theme")
public class LaunThemeController {

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunFontService launFontService;

	/**
	 * v2
	 * 根据条件去查询主题列表
     * @param tenantId 主题分类
	 * @param type 主题分类
	 * @param title 主题名称
	 * @param status 主题状态
	 * @param pageNum 第几页
	 * @param pageSize 每页条数
	 * @return
	 */
	@GetMapping("/query")
	public ResultData query(Long tenantId, Long type, String title, Integer status,
                            @RequestParam(defaultValue = "1") int pageNum,
			                @RequestParam(defaultValue = "10") int pageSize) {
		PageInfo<LaunThemeVo> pageInfo =
                launThemeService.query(tenantId, type, title, status, pageNum, pageSize);
		return new ResultData(pageInfo);
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
	 * @param id
	 * @param status
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/status")
	public ResultData modifyStatus(String id, Integer status) {
		launThemeService.changeStatus(id, status);
		return new ResultData();
	}

    /**
     * v2
     * 修改车机端主题排序权重
     * @param id 主题id
     * @param num 权重数
     * @return
     */
    @PostMapping("/sort")
    public ResultData sort(@NotNull String id, @NotNull Integer num) {
        launThemeService.sort(id, num);
        return new ResultData();
    }

    /**
     * zip上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResultData upload(MultipartFile file) {
		return new ResultData(launThemeService.upload(file));
    }



	/**
     *
     * v2
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param baseJson 底屏json
	 * @param widgetJson 组件json
	 * @param themeJson 主题内容 json
	 * @param saveType 保存类型:0暂存;1保存
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/saveTheme")
	public ResponseEntity<ResultData> saveTheme(String baseJson, String widgetJson, String themeJson, Integer saveType) {

		String i = launThemeService.saveTheme(baseJson, widgetJson, themeJson, saveType);

		return ResponseEntity.ok(new ResultData(i));
	}

	/**
     *
     * v2
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param baseJson 底屏json
	 * @param widgetJson 组件json
	 * @param themeJson 主题内容json
	 * @param saveType 保存类型:0暂存;1保存
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("/updateThem")
	public ResponseEntity<ResultData> updateTheme(String baseJson, String widgetJson, String themeJson,
			Integer saveType) {

		String i = launThemeService.updateTheme(baseJson, widgetJson, themeJson, saveType);
		/*
		 * if (null != administration.getId()) { i =
		 * launThemeService.updateOrSaveTheme(administration); } else { }
		 */
		return ResponseEntity.ok(new ResultData(i));
	}

}
