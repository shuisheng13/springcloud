package com.pactera.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName LaunThemeUploadFileVo
 * @Description 上传文件后的返回数据格式
 * @Author xukj
 * @Date 2018/12/26 15:37
 * @Version
 */
@Data
public class LaunThemeUploadFileVo {

    /**
     *  主题预览图片（最大4张）
     */
    private List<LaunThemeFileVo> themeImgsList;
    /**
     *  主题分辨率宽
     */
    private String wideResolution;
    /**
     *  主题分辨率长
     */
    private String longResolution;

    /**
     * 压缩包路径
     */
    private String zipUrl;

    /**
     * 文件大小
     */
    private Long fileSize;
}
