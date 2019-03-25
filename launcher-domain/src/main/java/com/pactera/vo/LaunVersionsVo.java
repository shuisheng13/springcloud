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
    private Double version;
    private String versionName;
    private Integer tenantId;
    private String tenantName;
    private String description;
}
