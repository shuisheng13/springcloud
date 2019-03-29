package com.pactera.vo;

import lombok.Data;

/**
 * @ClassName LaunKeywords
 * @Description
 * @Author xukj
 * @Date 2019/3/4 17:44
 * @Version
 */

@Data
public class LaunKeywordsVo {
    private String id;
    private String keyword;
    private String searchCount;
    private String creator;
    private String sort;
    private String status;
    private String top;
    private String layoutId;
}
