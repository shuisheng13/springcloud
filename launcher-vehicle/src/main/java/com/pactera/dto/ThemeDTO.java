package com.pactera.dto;

import com.pactera.vo.LaunThemeFileVo;
import com.pactera.vo.LaunThemeVo;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ThemeDTO
 * @Description
 * @Author xukj
 * @Date 2019/1/3 10:37
 * @Version
 */
@Data
public class ThemeDTO {
    List<LaunThemeFileVo> file;
    LaunThemeVo theme;
}
