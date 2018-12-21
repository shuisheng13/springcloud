package com.pactera.business.controller;

import com.github.pagehelper.PageInfo;
import com.pactera.business.service.LaunFontService;
import com.pactera.business.service.LaunThemeClassificationService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.domain.LaunFont;
import com.pactera.result.ResultData;
import com.pactera.vo.LaunThemeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 主题管理有关的controller
 * @author:woqu
 * @since:2018年4月26日 上午10:51:39
 */
@RestController
@RequestMapping("theme")
public class LaunThemeController {

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunThemeClassificationService launThemeClassificationService;

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	@Autowired
	private LaunFontService launFontService;

	/**
	 * 根据条件去查询主题列表
     * @param tenantId 主题分类
	 * @param type 主题分类
	 * @param title 主题名称
	 * @param status 主题状态
	 * @param pageNum 第几页
	 * @param pageSize 每页条数
	 * @return
	 */
	@PostMapping("query")
	public ResultData query(Long tenantId, Long type, String title, Integer status, @RequestParam(defaultValue = "1") int pageNum,
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
	 * @description 根据id去查看主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:47:33
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@GetMapping("selectById")
	@ApiOperation("根据id去查看主题")
	@ApiImplicitParam(name = "主题的id", value = "id")
	public ResponseEntity<ResultData> selectById(Long id) {
		Map<String, Object> map = launThemeService.selectById(id);
		return ResponseEntity.ok(new ResultData(map));
	}

	/**
	 * @description 根据id去删除主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:47:21
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("delectById")
	@ApiOperation("根据id去删除")
	@ApiImplicitParam(name = "主键id", value = "id")
	public ResponseEntity<ResultData> delectById(Long id) {
		int delectById = launThemeService.delectById(id);
		return ResponseEntity.ok(new ResultData(delectById));
	}

	/**
	 * 根据id去删除主题
	 * 
	 * @author LL
	 * @date 2018年5月15日 下午9:47:12
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("delectTypeById")
	@ApiOperation("根据id去删除分类")
	@ApiImplicitParam(name = "ids", value = "主键ids")
	public ResponseEntity<ResultData> delectTypeById(String ids) {
		launThemeClassificationService.delectById(ids);
		return ResponseEntity.ok(new ResultData());
	}

	/**
	 * @description 修改上下架状态
	 * @author liudawei
	 * @since 2018年4月26日 下午2:47:07
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("modifyStatus")
	@ApiOperation("根据id去修改上下架状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ids", value = "主键ids,已逗号分隔"),
			@ApiImplicitParam(name = "status", value = "上下架状态") })
	public ResponseEntity<ResultData> modifyStatus(String ids, Integer status) {
		launThemeService.modifyStatus(ids, status);
		return ResponseEntity.ok(new ResultData());
	}


	/**
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param baseJson 底屏json
	 * @param widgetJson 组件json
	 * @param themeJson 主题内容 json
	 * @param saveType 保存类型:0暂存;1保存
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("saveTheme")
	@ApiOperation("保存主题")
	public ResponseEntity<ResultData> saveTheme(String baseJson, String widgetJson, String themeJson,
			Integer saveType) {

		Long i = launThemeService.saveTheme(baseJson, widgetJson, themeJson, saveType);
		/*
		 * if (null != administration.getId()) { i =
		 * launThemeService.updateOrSaveTheme(administration); } else { }
		 */
		return ResponseEntity.ok(new ResultData(i));
	}

	/**
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param baseJson 底屏json
	 * @param widgetJson 组件json
	 * @param themeJson 主题内容json
	 * @param saveType 保存类型:0暂存;1保存
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("updateThem")
	@ApiOperation("修改主题")
	@ApiImplicitParams({ @ApiImplicitParam(name = "baseJson", value = "主题底屏参数json"),
			@ApiImplicitParam(name = "widgetJson", value = "组件json"),
			@ApiImplicitParam(name = "themeJson", value = "主题数据json"),
			@ApiImplicitParam(name = "saveType", value = "存储状态:0暂存;1保存") })
	public ResponseEntity<ResultData> updateTheme(String baseJson, String widgetJson, String themeJson,
			Integer saveType) {

		Long i = launThemeService.updateTheme(baseJson, widgetJson, themeJson, saveType);
		/*
		 * if (null != administration.getId()) { i =
		 * launThemeService.updateOrSaveTheme(administration); } else { }
		 */
		return ResponseEntity.ok(new ResultData(i));
	}

}
