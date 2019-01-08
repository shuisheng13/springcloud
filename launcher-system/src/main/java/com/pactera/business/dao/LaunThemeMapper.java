package com.pactera.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeClassification;
import com.pactera.vo.LaunThemeVo;

/**
 * @description:主题的mapper
 * @author:woqu
 * @since:2018年4月26日 上午11:29:48
 */
public interface LaunThemeMapper extends BaseMapper<LaunThemeAdministration> {


	/**
	 * 列表查询
	 * @param tenantId 租户id
     * @param typeId 主题分类id
     * @param title 主题名称
     * @param status 主题状态
     *
	 * @return
	 */
	List<LaunThemeAdministration> query(
	        @Param("tenantId") Long tenantId,
            @Param("typeId") String typeId,
            @Param("title") String title,
            @Param("status") Integer status);

	/**
	 * @description 根据条件去查询主题
	 * @author liudawei
	 * @since 2018年4月26日 上午11:31:01
	 * @param type
	 *            类型 title 名称 status 上架状态
	 * @param isChannleManager
	 *            是否为渠道管理员
	 * @return List<LaunThemeAdministration>
	 */
	List<LaunThemeVo> selectByCound(@Param("type") Long type, @Param("version") String version,
									@Param("channel") Long channel, @Param("title") String title, @Param("status") Integer status,
									@Param("isChannleManager") Integer isChannleManager);

	/**
	 * @description 根据条件去查询主题
	 * @author liudawei
	 * @since 2018年4月26日 上午11:31:01
	 * @param status
	 *            主题状态
	 * @return List<LaunThemeAdministration>
	 */
	List<LaunThemeAdministration> selectByStatus(@Param("status") Integer status);

	/**
	 * @description 根据id去查看主题
	 * @author liudawei
	 * @since 2018年4月26日 下午2:29:02
	 * @param id 主键
	 * @return LaunThemeAdministration
	 */
	LaunThemeAdministration selectById(@Param("id") String id);

	/**
	 * @description 根据id去查询主题主体
	 * @author liudawei
	 * @since 2018年4月27日 下午5:34:26
	 * @param id
	 *            主题的id
	 * @return LaunThemeAdministration
	 */
	LaunThemeAdministration selectByTheme(@Param("id") String id);

	/**
	 * 查询主题所有分类，包含该分类使用的主题数量
	 * 
	 * @author LL
	 * @date 2018年5月15日 下午9:42:16
	 * @param
	 * @return List<LaunThemeClassification>
	 */
	List<LaunThemeClassification> selectTypeList();

	/**
	 * 根据分组查询有效主题
	 * 
	 * @author LL
	 * @date 2018年8月3日 下午3:59:43
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, String>> getEffeCount();

}
