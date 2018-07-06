package com.pactera.business.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.pactera.business.service.LaunThemeClassificationService;
import com.pactera.business.service.LaunThemeService;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.domain.LaunThemeClassification;
import com.pactera.result.ResultData;
import com.pactera.vo.LaunThemeClassificationVo;
import com.pactera.vo.LaunThemeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 主题管理有关的controller
 * @author:woqu
 * @since:2018年4月26日 上午10:51:39
 */
@RestController
@Api(description = "主题管理")
@RequestMapping("theme")
public class LaunThemeController {

	@Autowired
	private LaunThemeService launThemeService;

	@Autowired
	private LaunThemeClassificationService launThemeClassificationService;

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	@GetMapping("selectByCount")
	@ApiOperation("根据条件去查询主题列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "主题分类"),
			@ApiImplicitParam(name = "version", value = "版本Id"), @ApiImplicitParam(name = "channel", value = "渠道ID"),
			@ApiImplicitParam(name = "status", value = "主题状态"), @ApiImplicitParam(name = "title", value = "主题名称"),
			@ApiImplicitParam(name = "pageNum", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "每页条数") })
	public ResponseEntity<ResultData> selectByCoundy(Long type, String version, Long channel, String title,
			Integer status, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize) {
		PageInfo<LaunThemeVo> pageInfo = launThemeService.selectByCoundy(type, version, channel, title, status, pageNum,
				pageSize);
		return ResponseEntity.ok(new ResultData(pageInfo));
	}

	/**
	 * @description 添加分类
	 * @author liudawei
	 * @since 2018年4月26日 下午2:14:32
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@PostMapping("addType")
	@ApiOperation("添加分类")
	@ApiImplicitParam(name = "classification", value = "渠道名称")
	public ResponseEntity<ResultData> addaType(String classification, String ids) {
		launThemeClassificationService.addType(classification, ids);
		return ResponseEntity.ok(new ResultData());
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
	 * @description 查询所有的分类
	 * @author liudawei
	 * @since 2018年4月26日 下午10:11:54
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@GetMapping("selectByType")
	@ApiOperation("查询所有的分类")
	public ResponseEntity<ResultData> selectByType() {
		List<LaunThemeClassification> listClass = launThemeClassificationService.selecByType();
		List<LaunThemeClassificationVo> returnList = new ArrayList<LaunThemeClassificationVo>();
		LaunThemeClassificationVo themeClassificationVo = null;
		for (LaunThemeClassification launThemeClassification : listClass) {
			themeClassificationVo = new LaunThemeClassificationVo();
			BeanUtils.copyProperties(launThemeClassification, themeClassificationVo);
			returnList.add(themeClassificationVo);
		}
		return ResponseEntity.ok(new ResultData(returnList));
	}

	/**
	 * @description demo的版本返回
	 * @author liudawei
	 * @since 2018年4月28日 下午6:28:42
	 * @param
	 * @return ResponseEntity<ResultData>
	 */
	@GetMapping("selectVersion")
	@ApiOperation("查询所有的版本")
	public ResponseEntity<ResultData> selectVersion() {
		return ResponseEntity.ok(new ResultData(launWidgetManagerService.findWidgetVersion()));
	}

	/**
	 * @description 根据id和渠道去保存和修改
	 * @author liudawei
	 * @since 2018年4月29日 下午2:46:58
	 * @param baseJson底屏json
	 * @param widgetJson组件json
	 * @param themeJson主题内容json
	 * @param saveType保存类型:0暂存;1保存
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
	 * @param baseJson底屏json
	 * @param widgetJson组件json
	 * @param themeJson主题内容json
	 * @param saveType保存类型:0暂存;1保存
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
