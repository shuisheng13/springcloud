package com.pactera.business.dao;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunThemeAdministration;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:主题的mapper
 * @author:woqu
 * @since:2018年4月26日 上午11:29:48
 */
public interface LaunThemeMapper extends BaseMapper<LaunThemeAdministration> {


	Integer count (@Param("type") int type, @Param("id") String id);

    List<LaunThemeAdministration> search(@Param("value") String value, @Param("version") double version);
}
