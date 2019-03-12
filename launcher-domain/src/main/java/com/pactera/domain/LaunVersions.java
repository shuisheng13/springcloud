package com.pactera.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName LaunVersion
 * @Description
 * @Author xukj
 * @Date 2018/12/19 14:18
 * @Version
 */
@Data
@Accessors(chain = true)
public class LaunVersions extends LaunBase {

    /**
     * 版本号
     */
    private double version;
    /**
     * 版本名称
     */
    private String versionName;
    private Integer tenantId;
    private String description;
    private Long layoutId;
}
