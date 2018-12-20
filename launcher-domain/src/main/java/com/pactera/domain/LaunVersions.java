package com.pactera.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName LaunVersion
 * @Description
 * @Author xukj
 * @Date 2018/12/19 14:18
 * @Version
 */
@Table
@Entity
@ApiModel("版本实体")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LaunVersions extends LaunBase {

    private @ApiModelProperty("版本号") String version;
    private @ApiModelProperty("关联租户") Long tenantId;
    private @ApiModelProperty("描述") String description;
}
