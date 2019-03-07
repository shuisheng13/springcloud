package com.pactera.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pactera.business.dao.LaunThemeMapper;
import com.pactera.business.service.*;
import com.pactera.config.exception.DataStoreException;
import com.pactera.config.exception.IORuntimeException;
import com.pactera.config.exception.status.ErrorStatus;
import com.pactera.config.header.SaasHeaderContextV1;
import com.pactera.config.header.TenantFacadeV1;
import com.pactera.constant.ConstantUtlis;
import com.pactera.domain.*;
import com.pactera.dto.ImgPropertie;
import com.pactera.po.LaunThemeSavePo;
import com.pactera.po.ThemesParam;
import com.pactera.utlis.*;
import com.pactera.valid.ThemeSaveValidator;
import com.pactera.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @description: 主题相关的实现类
 * @author:woqu
 * @since:2018年4月26日 上午11:27:03
 */
@Service
@Slf4j
public class LaunThemeServiceImpl implements LaunThemeService {

    @Value("${system.conf.themeTemp}")
    private String themeConfigUrl;

    @Value("${file.path}")
    private String fastDfsPath;

    @Value("${file.path.yu}")
    private String filePath;

    @Value("${system.conf.themeTemp.v2}")
    private String tempPath;

    @Value("${upload.zip.prop}")
    private String upThemeProp;

    @Value("${upload.theme.long}")
    private String upThemeLong;

    @Value("${upload.theme.width}")
    private String upThemeWidth;

    @Value("${upload.theme.img.path}")
    private String upThemeImgPath;

    @Value("${upload.imgs.amount}")
    private Integer upImgsAmount;


    @Value("${upload.theme.img.widget}")
    private String upThemeImgMain;

    @Value("${upload.theme.img.icon}")
    private String upThemeImgIcon;

    @Value("${upload.theme.img.lockscreen}")
    private String upThemeImgLockscreen;

    @Value("${upload.theme.preix}")
    private String upThemePreix;

    @Value("${img.type.widget}")
    private Integer imgTypeWidget;
    @Value("${img.type.icon}")
    private Integer imgTypeIcon;
    @Value("${img.type.lockscreen}")
    private Integer imgTypeLockscreen;


    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    @Autowired
    private TenantFacadeV1 tenantFacade;

    @Autowired
    private LaunThemeMapper launThemeMapper;

    @Autowired
    private LaunThemeFileService launThemeFileService;

    @Autowired
    private LaunFileCrudService launFileCrudService;

    @Autowired
    private ThemeSaveValidator validator;

    @Autowired
    private LaunLayoutService launLayoutService;


    @Override
    public LaunPage<LaunThemeVo> query(ThemesParam themeParam) {
        Integer widthResolution = null != themeParam.getResolution() ? themeParam.getResolution()[0] : null;
        Integer longResolution = null != themeParam.getResolution() ? themeParam.getResolution()[1] : null;
        PageInfo<LaunThemeAdministration> pageInfo = PageHelper.startPage(themeParam.getPageNum(), themeParam.getPageSize())
                .doSelectPageInfo(() -> launThemeMapper.query(
                        themeParam.getLayoutId(),
                        longResolution,
                        widthResolution,
                        null,
                        themeParam.getType(),
                        themeParam.getTitle(), themeParam.getStatus()));
        BeanCopier beanCopier = BeanCopier.create(LaunThemeAdministration.class, LaunThemeVo.class, false);
        List<LaunThemeVo> themes = pageInfo.getList().stream().map(theme -> {
            LaunThemeVo themeVo = new LaunThemeVo();
            beanCopier.copy(theme, themeVo, null);
            return themeVo;
        }).collect(Collectors.toList());
        return new LaunPage(pageInfo, themes);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeStatus(List<String> ids, Integer status) {
        final int statusValue = status;
        ConstantUtlis.themeStatus statusEnum =
                Arrays.stream(ConstantUtlis.themeStatus.values())
                        .filter(t -> t.getCode() == statusValue).findFirst().get();

        if (status == ConstantUtlis.themeStatus.VALID.getCode()) {
            status = ConstantUtlis.themeStatus.DOWN_SHELF.getCode();
        }
        return launThemeMapper.changeStatus(ids, status);
    }

    /**
     * @param
     * @description 根据id去预览主题
     * @author liudawei
     * @since 2018年4月26日 下午2:27:59
     */
    @Override
    public Map<String, Object> selectById(String id) {
        List<LaunThemeFile> list = launThemeFileService.selectByThemeId(id);
        List<LaunThemeFileVo> voList = new ArrayList<>();

        LaunThemeFileVo launThemeFileVo = null;
        for (LaunThemeFile launThemeFile : list) {
            launThemeFileVo = new LaunThemeFileVo();
            BeanUtils.copyProperties(launThemeFile, launThemeFileVo);
            voList.add(launThemeFileVo);
        }
        Map<String, Object> map = new HashMap<>();
        LaunThemeAdministration themeAdministration = launThemeMapper.selectByTheme(id);
        if (null == themeAdministration) {
            return map;
        }
        LaunThemeVo themeVo = new LaunThemeVo();
        BeanUtils.copyProperties(themeAdministration, themeVo);
        Optional<LaunLayout> layout = Optional.ofNullable(launLayoutService.findById(themeAdministration.getLayoutId()));
        themeVo.setLayoutName(layout.map(l -> l.getName()).orElse(null));
        map.put("theme", themeVo);
        map.put("file", voList);
        return map;
    }

    @Override
    public int sort(String id, Long sort) {
        return launThemeMapper.sort(id, sort);
    }

    @Override
    public int recommendSort(String id, Integer sort) {
        return launThemeMapper.updateByPrimaryKeySelective(new LaunThemeAdministration().setId(id).setRecommendSort(sort));
    }

    private void themeJsonValid(LaunThemeSavePo launThemeSavePo) {
        if (null == launThemeSavePo) {
            throw new DataStoreException(ErrorStatus.THEMEJSON_ERROR);
        }
        BeanPropertyBindingResult beanPropertyBindingResult =
                new BeanPropertyBindingResult(launThemeSavePo, LaunThemeSavePo.class.getName());
        validator.validate(launThemeSavePo, beanPropertyBindingResult);
        if (beanPropertyBindingResult.hasErrors()) {
            List<String> message = beanPropertyBindingResult.getAllErrors().stream().map(e ->
                    e.getDefaultMessage()).collect(Collectors.toList());
            log.error("保存主题themeJson有误{}", message.toString());
            throw new DataStoreException(message.toString());
        }
    }

    /**
     * @param
     * @description 保存主题
     * @author liudawei
     * @since 2018年4月29日 下午2:56:55
     */
    @Override
    @Transactional
    public String saveTheme(String baseJson, String widgetJson, String themeJson) {

        LaunThemeSavePo launThemeSavePo = JsonUtils.jsonToClass(themeJson, LaunThemeSavePo.class);
        this.themeJsonValid(launThemeSavePo);
        LaunThemeAdministration administration = new LaunThemeAdministration()
                .setId(launThemeSavePo.getId())
                .setLongResolution(launThemeSavePo.getLongResolution())
                .setWideResolution(launThemeSavePo.getWideResolution())
                .setVersion(launThemeSavePo.getVersion())
                .setTypeId(launThemeSavePo.getTypeId())
                .setFilesJson(launThemeSavePo.getFilesJson())
                .setTitle(launThemeSavePo.getTitle())
                .setZipUrl(launThemeSavePo.getZipUrl())
                .setDescription(launThemeSavePo.getDescription())
                .setAddition(launThemeSavePo.getAddition() == null ? 0 : launThemeSavePo.getAddition())
                .setAuthor(launThemeSavePo.getAuthor())
                .setReleaseTime(launThemeSavePo.getReleaseTime())
                .setPrice(launThemeSavePo.getPrice())
                .setLayoutId(launThemeSavePo.getLayoutId())
                .setFileSize(launThemeSavePo.getFileSize());

        String themeId;

        administration.setWidgetJson(widgetJson);
        administration.setBasicJson(baseJson);
        administration.setThemeJson(themeJson);
        if (HStringUtlis.isNotBlank(launThemeSavePo.getStartTime())) {
            administration.setStartTime(TimeUtils.millis2Date(Long.parseLong(launThemeSavePo.getStartTime())));
        }
        if (HStringUtlis.isNotBlank(launThemeSavePo.getEndTime())) {
            administration.setEndTime(TimeUtils.millis2Date(Long.parseLong(launThemeSavePo.getEndTime())));
        }
        if (StringUtils.isNotBlank(administration.getId())) {
            themeId = administration.getId();
            administration.setPreviewPath(this.saveThemeFile(administration.getFilesJson(), themeId).get("previewPath"));
            launThemeMapper.updateByPrimaryKeySelective(administration);
            return themeId;
        }

        themeId = this.id();
        administration.setPreviewPath(this.saveThemeFile(administration.getFilesJson(), themeId).get("previewPath"))
                .setCreator(SaasHeaderContextV1.getUserName())
                .setId(themeId).setCreateDate(TimeUtils.nowTimeStamp())
                .setRecommend(false).setRecommendSort(1).setSort(1)
                .setDownloadCount(0).setUsedCount(0)
                .setPrice(null == administration.getPrice() ? new BigDecimal(0) : administration.getPrice())
                .setStatus(ConstantUtlis.themeStatus.DOWN_SHELF.getCode());
        launThemeMapper.insertSelective(administration);
        return themeId;
    }

    /**
     * 数据库主键排重
     *
     * @return
     */
    private String id() {
        String tenantName = SaasHeaderContextV1.getTenantName();
        String themeId = IdUtlis.Id(ConstantUtlis.PRIVATE_THEME, tenantName);
        LaunThemeAdministration th = launThemeMapper.selectByTheme(themeId);
        while (null != th) {
            themeId = IdUtlis.Id(ConstantUtlis.PRIVATE_THEME, tenantName);
            th = launThemeMapper.selectById(themeId);
        }
        return themeId;
    }

    /**
     * 保存主图预览图
     *
     * @param themeId 主题主键id
     * @param map     文件
     *                key:图片名,value:文件存储路径
     * @return Long
     * @author LL
     * @date 2018年5月23日 下午1:58:35
     */
    @Transactional
    public Map<String, String> saveThemeFile(Map<String, String> map, String themeId) {
        log.info("主题管理----------保存主题相关图片----------id:{}----------", themeId);

        //xukj add start 2019-01-15
        launThemeFileService.deleteById(themeId);
        //xukj add end 2019-01-15
        Map<String, String> returnMap = new HashMap<String, String>();

        LaunThemeFile themeFile = null;
        int i = 0;
        if (map == null) {
            return null;
        }

        StringBuffer urls = new StringBuffer();
        int index = 0;
        for (Entry<String, String> map2 : map.entrySet()) {
            themeFile = new LaunThemeFile();
            Long id = IdUtlis.Id();
            themeFile.setFileName(map2.getKey());
            themeFile.setFilePath(map2.getValue());
            themeFile.setThemeId(themeId);
            themeFile.setFileIndex(index++);
            themeFile.setId(id);

            if (i == 0) {
                themeFile.setType(1);
                //xukj change start 2019/1/7
                //returnMap.put("previewPath", filePath + map2.getValue());
                returnMap.put("previewPath", map2.getValue());
                //xukj change end 2019/1/7
            } else {
                //xukj add start 2019/3/7
                themeFile.setType(i + 1);
                //xukj add start 2019/3/7
                urls.append(filePath + map2.getValue()).append(",");
            }
            i++;
            launThemeFileService.insert(themeFile);
        }
        returnMap.put("urls", urls.toString());
        return returnMap;
    }

    @Override
    public LaunThemeUploadFileVo upload(MultipartFile upload) {

        if (!FileTool.checkFileType(upload.getOriginalFilename(), ConstantUtlis.file.ZIP)) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_ILLEGAL_FILE);
        }

        LaunThemeUploadFileVo launThemeUploadFileVo = new LaunThemeUploadFileVo();
        Path path = FileTool.createTempFile(upThemePreix, ConstantUtlis.file.ZIP,
                FileTool.getBytes(upload));
        FileTool.unZipFile(path.toFile().getAbsolutePath(), tempPath);
        FileTool.delTempFile(path);

        this.checkZip(tempPath);

        Map<String, File> files = FileTool.mapFiles(tempPath);
        File prop = files.get(upThemeProp);
        //先读prop文件
        ImgPropertie imgFileName = this.parseProp(prop, launThemeUploadFileVo);
        launThemeUploadFileVo.setFileSize(prop.length());
        Map<String, File> imgFiles = FileTool.mapFiles(tempPath + upThemeImgPath);
        //传图片包
        List<LaunThemeFileVo> imgs = this.uploadImgs(imgFiles, imgFileName);
        launThemeUploadFileVo.setThemeImgsList(imgs);
        //传skin
        String zipPath = this.upload2fastFDS(prop, FileTool.getExtentionWithoutPoint(prop.getAbsolutePath()));
        launThemeUploadFileVo.setZipUrl(zipPath);

        FileTool.del(new File(tempPath));
        log.info("删除本地临时文件夹...");
        return launThemeUploadFileVo;
    }

    @Override
    public int recommend(String id, boolean value) {
        return launThemeMapper.updateByPrimaryKeySelective(
                new LaunThemeAdministration().setRecommend(value).setId(id));
    }

    @Override
    public void themeAutoUpDown(String timestamp) {
        log.info("执行定时上下架任务 start");
        Example example = new Example(LaunThemeAdministration.class);
        example.createCriteria().andEqualTo("status", ConstantUtlis.themeStatus.ON_SHELF.getCode());
        example.or().andEqualTo("status", ConstantUtlis.themeStatus.DOWN_SHELF.getCode());
        List<LaunThemeAdministration> list = launThemeMapper.selectByExample(example);
        List<String> upList = new ArrayList<>();
        List<String> downList = new ArrayList<>();

        for (LaunThemeAdministration launThemeAdministration : list) {

            Date startTime = launThemeAdministration.getStartTime();
            Date endTime = launThemeAdministration.getEndTime();
            log.info("主题id:{},开始时间：{},结束时间:{}, 判断时间：{}",
                    launThemeAdministration.getId(),
                    startTime,
                    endTime,
                    timestamp);
            if (null != startTime) {
                String startTimeStr = TimeUtils.date2String(startTime, TimeUtils.DEFAULT_SIMPLE_TIME);
                if (startTimeStr.equals(timestamp)) {
                    upList.add(launThemeAdministration.getId());
                    log.info("主题：{}，上架");
                }
            }

            if (null != endTime) {
                String endTimeStr = TimeUtils.date2String(endTime, TimeUtils.DEFAULT_SIMPLE_TIME);
                if (endTimeStr.equals(timestamp)) {
                    downList.add(launThemeAdministration.getId());
                    log.info("主题：{}，下架");
                }
            }

        }
        if (upList.size() > 0) {
            this.changeStatus(upList, ConstantUtlis.themeStatus.ON_SHELF.getCode());
        }
        if (downList.size() > 0) {
            this.changeStatus(downList, ConstantUtlis.themeStatus.DOWN_SHELF.getCode());
        }
        log.info("执行定时上下架任务 end");
    }

    @Override
    public int cleanThemeClassification(String id) {
        return launThemeMapper.cleanClassification(id);
    }

    @Override
    public List<LaunResolution> resolution(Integer layoutId) {
        return launThemeMapper.resolution(layoutId);
    }

    private ImgPropertie parseProp(File file, LaunThemeUploadFileVo launThemeUploadFileVo) {
        Properties prop = FileTool.file2Prop(file);
        log.info("解析properties完成...prop:{}", prop.toString());
        launThemeUploadFileVo.setLongResolution(Long.valueOf(prop.getProperty(upThemeLong)));
        launThemeUploadFileVo.setWideResolution(Long.valueOf(prop.getProperty(upThemeWidth)));
        if (null == prop.getProperty(upThemeImgMain) || null == prop.getProperty(upThemeImgIcon) || null == prop.getProperty(upThemeImgLockscreen)) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_IMG_ERROR);
        }

        return new ImgPropertie(prop.getProperty(upThemeImgMain), prop.getProperty(upThemeImgIcon), prop.getProperty(upThemeImgLockscreen));
    }

    private List<LaunThemeFileVo> uploadImgs(Map<String, File> imgFiles, ImgPropertie imgFileName) {
        List<LaunThemeFileVo> imgs = new ArrayList<>();
        this.checkImage(imgFileName, imgFiles);
        imgs.add(this.img(imgFileName.getWidget(), imgTypeWidget, imgFiles));
        imgs.add(this.img(imgFileName.getIcon(), imgTypeIcon, imgFiles));
        imgs.add(this.img(imgFileName.getLockscreen(), imgTypeLockscreen, imgFiles));
        log.info("上传图片完成...");
        return imgs;
    }

    private void checkImage(ImgPropertie imgFileName, Map<String, File> imgFiles) {
        if (null == imgFileName.getWidget() || null == imgFiles.get(imgFileName.getWidget())) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_IMG_ERROR);
        }
        if (null == imgFileName.getIcon() || null == imgFiles.get(imgFileName.getIcon())) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_IMG_ERROR);
        }

        if (null == imgFileName.getLockscreen() || null == imgFiles.get(imgFileName.getLockscreen())) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_IMG_ERROR);
        }
    }

    private LaunThemeFileVo img(String name, Integer type, Map<String, File> imgFiles) {
        File file = imgFiles.get(name);
        String ExName = FileTool.getExtentionWithoutPoint(file.getName());
        String imgPath = this.upload2fastFDS(file, ExName);
        return new LaunThemeFileVo()
                .setFileName(file.getName())
                .setFilePath(imgPath)
                .setType(type);
    }

    private String upload2fastFDS(File file, String exName) {
        try (InputStream fileInputStream = new FileInputStream(file)) {
            String path = launFileCrudService.upload(fileInputStream,
                    file.length(), exName, null);
            log.info("上传{}完成...路径：{}", file.getName(), path);
            return path;
        } catch (FileNotFoundException e) {
            throw new IORuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private boolean checkZip(String tempPath) {

        boolean imgFlag = false;
        boolean propFlag = false;
        boolean skinFlag = false;
        boolean imgCountFlag = false;

        for (String fileName : FileTool.listFilename(tempPath)) {
            if (fileName.equals(upThemeImgPath)) {
                imgFlag = !imgFlag;
            }
            if (fileName.equals(upThemeProp)) {
                propFlag = !propFlag;
            }
            if (FileTool.getExtentionWithoutPoint(fileName).equals(ConstantUtlis.file.SKIN)) {
                skinFlag = !skinFlag;
            }
        }

        if (imgFlag && FileTool.listFiles(tempPath + upThemeImgPath).size() == upImgsAmount) {
            imgCountFlag = !imgCountFlag;
        }

        if (!imgFlag || !propFlag || !skinFlag || !imgCountFlag) {
            FileTool.del(new File(tempPath));
            log.info("删除本地临时文件夹...");
        }

        if (!imgFlag) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_NO_IMGS);
        }
        if (!propFlag) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_NO_CONFIG);
        }
        if (!skinFlag) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_NO_SKIN);
        }
        if (!imgCountFlag) {
            throw new DataStoreException(ErrorStatus.UPLOAD_THEME_TOO_MANY_IMG);
        }
        return imgFlag && propFlag && skinFlag && imgCountFlag;
    }
}
