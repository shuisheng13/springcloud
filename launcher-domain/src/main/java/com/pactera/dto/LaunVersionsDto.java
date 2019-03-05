package com.pactera.dto;

import lombok.Data;

/**
 * @ClassName LaunVersions
 * @Description
 * @Author xukj
 * @Date 2019/3/5 15:18
 * @Version
 */
@Data
public class LaunVersionsDto {

    private Long id;
    private Double version;
    private String versionName;
    private String layout;
}
