package com.pactera.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName ThemeListVO
 * @Description
 * @Author xukj
 * @Date 2019/1/3 13:25
 * @Version
 */
@Data
public class ThemeListVO {
    private String id;
    private String previewPath;
    private String title;
    private Integer downloadCount;
    private BigDecimal price;
}
