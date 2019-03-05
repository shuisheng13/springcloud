package com.pactera.business.dao;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunVersions;
import com.pactera.dto.LaunVersionsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName LaunVersionMapper
 * @Description
 * @Author xukj
 * @Date 2018/12/19 14:32
 * @Version
 */
public interface LaunVersionMapper extends BaseMapper<LaunVersions> {

    List<LaunVersionsDto> findByVersionAndLayout(@Param("version") Double version, @Param("layout") String layout);

}
