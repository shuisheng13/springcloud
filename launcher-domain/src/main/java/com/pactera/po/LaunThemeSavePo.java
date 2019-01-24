package com.pactera.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName LaunSaveThemeVo
 * @Description
 * @Author xukj
 * @Date 2019/1/15 15:44
 * @Version
 */
@Data
public class LaunThemeSavePo {

    private String id;
    private Long longResolution;
    private Long wideResolution;
    private String version;
    private String typeId;
    private Map<String, String> filesJson;
    private String title;
    private String zipUrl;
    private String description;
    private Integer addition;
    private String author;
    private Date releaseTime;
    private BigDecimal price;
    private String startTime;
    private String endTime;
    private Long fileSize;
}
