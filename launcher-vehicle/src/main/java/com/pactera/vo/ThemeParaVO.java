package com.pactera.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author zhaodong
 * @Date 15:20 2019/3/19
 * @Param
 * @return
 **/
@Data
@Accessors(chain = true)
public class ThemeParaVO {

       private int recommendSort;

       private double  version;

       private String typeId;

       private String  layoutName;

       private int sort;

       private int    downloadCount;


}
