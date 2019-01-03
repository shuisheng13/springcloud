package com.pactera.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName ThemeDetailVo
 * @Description
 * @Author xukj
 * @Date 2019/1/3 10:38
 * @Version
 */
@Data
@Accessors(chain = true)
public class ThemeDetailVO {

    List<ThemeFileVO> file;
    ThemeVO theme;
}
