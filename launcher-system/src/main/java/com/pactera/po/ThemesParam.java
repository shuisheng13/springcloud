package com.pactera.po;

import com.pactera.valid.annotation.ThemeResolution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @ClassName Themes
 * @Description
 * @Author xukj
 * @Date 2019/3/5 17:26
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemesParam {

    private String type;
    @NotEmpty
    private String title;
    private Integer status;
    private Long layoutId;
    @ThemeResolution private Integer[] resolution;
    private int pageNum = 1;
    private int pageSize = 10;

}
