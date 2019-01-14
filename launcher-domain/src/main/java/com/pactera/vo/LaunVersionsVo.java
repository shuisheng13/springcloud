package com.pactera.vo;

import lombok.Data;

/**
 * @ClassName LaunVersion
 * @Description
 * @Author xukj
 * @Date 2018/12/19 14:18
 * @Version
 */
@Data
public class LaunVersionsVo {

    private Long id;
    private String version;
    private String versionName;
    private Long tenantId;
    private String tenantName;
    private String description;
}