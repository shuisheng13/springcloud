package com.pactera.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName LaunLayout
 * @Description
 * @Author xukj
 * @Date 2019/3/5 14:44
 * @Version
 */

@Entity
@Data
@Accessors(chain = true)
public class LaunLayout {

    @Id
    private Long id;
    private String name;
}
