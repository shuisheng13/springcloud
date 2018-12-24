package com.pactera.business.dao;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LauncThemeClassification;
import com.pactera.vo.LauncThemeClassVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *  主题分类管理
 * @author:zhaodong
 * @since:2018年12月19日 上午14:29:39
 */
public interface LauncThemeClassificationMapper extends BaseMapper<LauncThemeClassification> {

    /**
     * 根据id修改分类
     * @Author zhaodong
     * @Date 14:02 2018/12/20
     * @Param themClassId
     * @return
     **/
    int updateByThemClassId(LauncThemeClassVo launcThemeClassVo);

    /**
     * 删除主题分类（假删除）
     * @Author zhaodong
     * @Date 14:46 2018/12/20
     * @Param classificationId
     * @return
     **/
    int deleteByThemClassId(@Param("id") String id);

    /**
     * 查询主题列表
     * @Author zhaodong
     * @Date 12:57 2018/12/21
     * @Param
     * @return
     **/
    List<LauncThemeClassVo> selectLauncThemeClassVo(LauncThemeClassVo launcThemeClassVo);
}
