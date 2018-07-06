package com.pactera.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.exception.status.SuccessStatus;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetChannel;
import com.pactera.domain.LaunWidgetFile;
import com.pactera.domain.LaunWidgetType;
import com.pactera.result.ResultData;
import com.pactera.utlis.BeanUtil;
import com.pactera.vo.LaunWidgetTypeVo;
import com.pactera.vo.LaunWidgetVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @description widget管理相关
 * @author lizhipeng
 * @since 2018年5月25日10:14:47
 */
@RestController
@Api(description = "widget管理")
public class LaunWidgetManagerController {

	@Autowired
	public FastFileStorageClient fastFileStorageClient;

	@Autowired
	private LaunWidgetManagerService launWidgetManagerService;

	@PostMapping("insertWidgetType")
	@ApiOperation("添加Widget类型")
	@ApiImplicitParam(name = "typeName", value = "需要添加的Widget类型名称")
	public ResponseEntity<ResultData> insertWidgetType(String typeName) {
		String message = launWidgetManagerService.insertMidgetType(typeName);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),message));
	}

	@GetMapping("findWidgetTypeList")
	@ApiOperation("查询widget类型列表")
	public ResponseEntity<ResultData> findWidgetTypeList() {
		PageInfo<LaunWidgetTypeVo> pageInfo = launWidgetManagerService.findWidgetTypeList();
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),pageInfo));
	}

	@PostMapping("deleteWidgetType")
	@ApiOperation("根据主键id删除widget类型")
	@ApiImplicitParam(name = "id", value = "需要删除的widget的主键id")
	public ResponseEntity<ResultData> deleteWidgetType(Long id) {
		launWidgetManagerService.deleteWidgetType(id);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@PostMapping("uploadBaseWidget")
	@ApiOperation("上传基础widget压缩包")
	@ApiImplicitParam(name = "file", value = "接收的文件")
	public ResponseEntity<ResultData> uploadBaseWidget(MultipartFile file, HttpServletRequest request) {
		launWidgetManagerService.fileUpload(file);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),SuccessStatus.OPERATION_SUCCESS.message()));
	}

	@PostMapping("replaceBaseWidget")
	@ApiOperation("替换基础widget压缩包")
	@ApiImplicitParams({ @ApiImplicitParam(name = "file", value = "替换文件"),
			@ApiImplicitParam(name = "widgetId", value = "要替换widget的主键") })
	public ResponseEntity<ResultData> replaceBaseWidget(MultipartFile file, Long widgetId) {
		String message = launWidgetManagerService.fileReplace(file, widgetId);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),message));
	}

	@GetMapping("findWidgetsList")
	@ApiOperation("查询widget列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示的条数"),
			@ApiImplicitParam(name = "defaultSize", value = "默认尺寸"), @ApiImplicitParam(name = "category", value = "分类"),
			@ApiImplicitParam(name = "version", value = "最低支持版本"), @ApiImplicitParam(name = "keyWord", value = "关键词"),
			@ApiImplicitParam(name = "type", value = "人员类别") })
	public ResponseEntity<ResultData> findWidgetsList(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String defaultSize, Long category, Integer version,
			String keyWord, Integer type) {
		PageInfo<LaunWidgetVo> pageInfo = launWidgetManagerService.findWidgetsList(pageNum, pageSize, defaultSize,
				category, version, keyWord, type);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),pageInfo));
	}
	
	@GetMapping("findWidgetsPullList")
	@ApiOperation("用于管理员创建主题或widget时的下拉列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示的条数"),
			@ApiImplicitParam(name = "defaultSize", value = "默认尺寸"), @ApiImplicitParam(name = "category", value = "分类"),
			@ApiImplicitParam(name = "version", value = "最低支持版本"), @ApiImplicitParam(name = "keyWord", value = "关键词"),
			@ApiImplicitParam(name = "type", value = "人员类别") })
	public ResponseEntity<ResultData> findWidgetsPullList(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize, String defaultSize, Long category, Integer version,
			String keyWord, Integer type,String channels,Integer channelnum) {
		PageInfo<LaunWidgetVo> pageInfo = launWidgetManagerService.findWidgetsPullList(pageNum, pageSize, defaultSize,
				category, version, keyWord, type,channels,channelnum);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),pageInfo));
	}

	@GetMapping("deleteWidgetById")
	@ApiOperation("根据主键删除widget")
	@ApiImplicitParam(name = "id", value = "widget主键")
	public ResponseEntity<ResultData> deleteWidgetById(Long id) {
		String message = launWidgetManagerService.deleteWidgetById(id);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),message));
	}

	@GetMapping("findWidgetDefaultSize")
	@ApiOperation("查询所有widget尺寸列表")
	public ResponseEntity<ResultData> findWidgetDefaultSize() {
		PageInfo<String> pageInfo = launWidgetManagerService.findWidgetDefaultSize();
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),pageInfo));
	}

	@GetMapping("findWidgetCategory")
	@ApiOperation("查询所有widget类型列表")
	public ResponseEntity<ResultData> findWidgetCategory() {
		PageInfo<LaunWidgetType> pageInfo = launWidgetManagerService.findWidgetCategory();
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),pageInfo));
	}

	@GetMapping("findWidgetVersion")
	@ApiOperation("查询所有widget版本列表")
	public ResponseEntity<ResultData> findWidgetVersion() {
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),launWidgetManagerService.findWidgetVersion()));
	}

	@GetMapping("findWidgetById")
	@ApiOperation("根据widgetId查询widget的信息，基础或变体回显使用")
	@ApiImplicitParam(name = "widgetId", value = "widget的主键id")
	public ResponseEntity<ResultData> findWidgetById(Long widgetId) throws Exception {
		String json = launWidgetManagerService.getWidgetDetail(widgetId);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),(Object)json));
	}

	@GetMapping("getImgPathBywidthId")
	@ApiOperation("根据widgetId查询该widget所有的图片路径")
	@ApiImplicitParam(name = "widgetId", value = "widget的主键id")
	public Map<String, String> getImgPathBywidthId(Long widgetId) {
		Map<String, String> map = new HashMap<String, String>(16);
		List<LaunWidgetFile> filelist = launWidgetManagerService.findWidgetfileById(widgetId);
		if (filelist != null) {
			for (LaunWidgetFile file : filelist) {
				map.put(file.getFileName(), file.getPath());
			}
		}
		return map;
	}

	@GetMapping("widgetpreview")
	@ApiOperation("预览widget")
	@ApiImplicitParam(name = "widgetId", value = "widget主键")
	public ResponseEntity<ResultData> widgetpreview(Long widgetId) {
		String imgpath = launWidgetManagerService.widgetPreview(widgetId);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),(Object)imgpath));
	}

	@ApiOperation("文件下载")
	@GetMapping("dowloadwidgetFile")
	@ApiImplicitParam(name = "widgetId", value = "widget主键")
	public String dowloadwidgetFile(HttpServletResponse response, HttpServletRequest request, Long widgetId) {
		LaunWidgetFile widgetfile = launWidgetManagerService.findWidgetFileById(widgetId);
		// StorePath storePath = StorePath.praseFromUrl(widgetfile.getPath());
		// byte[] bytes =
		// fastFileStorageClient.downloadFile(storePath.getGroup(),
		// storePath.getPath(), new DownloadByteArray());
		return widgetfile.getPath();
	}

	@RequestMapping("saveWidget")
	@ApiOperation("创建widget变体")
	@ApiImplicitParams({ @ApiImplicitParam(name = "widgetjson", value = "widget信息json"),
			@ApiImplicitParam(name = "filepath", value = "图片路径json"),
			@ApiImplicitParam(name = "channels", value = "渠道数组") })
	public ResponseEntity<ResultData> saveWidget(String widgetjson, String filepath, String channels) {
		int status = launWidgetManagerService.saveSingleWidget(widgetjson, filepath, channels);
		if(status == 1) {
			return ResponseEntity.ok(new ResultData(SuccessStatus.WIDGETSAVE_SUCCESS.status(),SuccessStatus.WIDGETSAVE_SUCCESS.message()));
		}else {
			return ResponseEntity.ok(new ResultData(ErrorStatus.SYS_ERROR.status(),ErrorStatus.SYS_ERROR.message()));
		}

	}

	@PostMapping("updateWidget")
	@ApiOperation("修改widget变体")
	@ApiImplicitParams({ @ApiImplicitParam(name = "widgetjson", value = "widget信息json"),
			@ApiImplicitParam(name = "filepath", value = "图片路径json"),
			@ApiImplicitParam(name = "channels", value = "渠道数组") })
	public ResponseEntity<ResultData> updateWidget(String widgetjson, String filepath, String channels) {
		int status = launWidgetManagerService.updateSingleWidget(widgetjson, filepath, channels);
		if(status == 1) {
			return ResponseEntity.ok(new ResultData(SuccessStatus.WIDGETUPDATE_SUCCESS.status(),SuccessStatus.WIDGETUPDATE_SUCCESS.message()));
		}else {
			return ResponseEntity.ok(new ResultData(ErrorStatus.SYS_ERROR.status(),ErrorStatus.SYS_ERROR.message()));
		}
		
	}

	@GetMapping("getWidgetChannels")
	@ApiOperation("查询widget所属渠道ID")
	@ApiImplicitParam(name = "widgetId", value = "widget主键")
	public ResponseEntity<ResultData> getWidgetChannels(Long widgetId) {
		List<LaunWidgetChannel> list = launWidgetManagerService.findWidgetChannelById(widgetId);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),list));
	}

	@GetMapping("getGoupWidgetById")
	@ApiOperation("根据widgetId获取基本信息及其文件信息")
	@ApiImplicitParam(name = "widgetId", value = "widget主键")
	public ResponseEntity<ResultData> getGoupWidgetById(Long widgetId) {
		String json = launWidgetManagerService.getGoupWidgetById(widgetId);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),(Object)json));
	}

	@PostMapping("saveGroupWidget")
	@ApiOperation("保存自定义widget")
	@ApiImplicitParams({ @ApiImplicitParam(name = "widgetjon", value = "自定义widget的所有信息"),
			@ApiImplicitParam(name = "groupjson", value = "子widget的所有信息") })
	public ResponseEntity<ResultData> saveGroupWidget(String widgetjon, String groupjson) {
		String message = launWidgetManagerService.saveGroupWidget(widgetjon, groupjson);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),message));
	}

	@GetMapping("showGroupWidget")
	@ApiOperation("自定义widget回显")
	@ApiImplicitParam(name = "widgetId", value = "widget主键")
	public ResponseEntity<ResultData> showGroupWidget(Long widgetId) {
		LaunWidget widget = launWidgetManagerService.findWidgetById(widgetId);
		LaunWidgetVo widgetvo = new LaunWidgetVo();
		BeanUtil.copyProperties(widget, widgetvo);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),widgetvo));
	}

	@GetMapping("findWidgetUseNum")
	@ApiOperation("查询widget在主题内使用情况")
	@ApiImplicitParam(name = "widgetId", value = "widget主键")
	public ResponseEntity<ResultData> findWidgetUseNum(Long widgetId) {
		String useNum = launWidgetManagerService.findWidgetUseNum(widgetId);
		return ResponseEntity.ok(new ResultData(SuccessStatus.OPERATION_SUCCESS.status(),useNum));
	}

}
