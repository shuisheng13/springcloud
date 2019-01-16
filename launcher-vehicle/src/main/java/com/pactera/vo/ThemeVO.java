package com.pactera.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName ThemeVO
 * @Description
 * @Author xukj
 * @Date 2019/1/2 17:47
 * @Version
 */

@Data
public class ThemeVO {
    private String id;
    private String title;
    private String description;
    private String author;
    private Date releaseTime;
    private Integer downloadCount;
    private Integer addtion;
    private Integer usedCount;
    private Integer sort;
    private Long fileSize;
    private String zipUrl;
    private BigDecimal price;

    public Integer getDownloadCount() {
        return downloadCount + addtion;
    }
}
