package com.pactera.domain;

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
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LaunVersions extends LaunBase {

    private Long id;
    private String version;
    private Long tenantId;
    private Long tenantName;
    private String description;
}
