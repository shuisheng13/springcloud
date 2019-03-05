package com.pactera.po;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LaunVersion
 * @Description
 * @Author xukj
 * @Date 2019/3/5 16:07
 * @Version
 */
@Data
public class LaunVersion {

    @NotNull(message = "version不能为空")
    private Double version;
    private String apiKey;
    private String versionName;
}
