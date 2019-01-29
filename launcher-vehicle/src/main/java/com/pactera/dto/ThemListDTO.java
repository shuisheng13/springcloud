package com.pactera.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhaodong
 * @Date 10:21 2019/1/7
 **/
@Data
public class ThemListDTO {

    private String previewPath;

    private String title;

    private int downCount;

    private String id;

    private BigDecimal price;

    private double version;


}
