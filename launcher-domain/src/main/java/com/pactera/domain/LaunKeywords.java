package com.pactera.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName LaunKeywords
 * @Description
 * @Author xukj
 * @Date 2019/3/4 16:51
 * @Version
 */

@Table
@Entity
@Data
public class LaunKeywords {

    @Id
    private String id;
    private String keyword;
    private String searchCount;
    private String creator;
    private String sort;
    private String status;
    private String top;
    private String layoutId;

}
