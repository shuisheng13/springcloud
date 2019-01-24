package com.pactera.business.dao;
import com.pactera.config.mapper.BaseMapper;
import com.pactera.domain.LaunThemeAdministration;
import com.pactera.domain.LaunThemeClassificationV2;
import com.pactera.vo.LauncThemeClassVo;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  主题分类管理
 * @author:zhaodong
 * @since:2018年12月19日 上午14:29:39
 */
public interface LaunThemeClassificationV2Mapper extends BaseMapper<LaunThemeClassificationV2> {

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
    int deleteByThemClassId(String id);

    /**
     * 查询主题列表
     * @Author zhaodong
     * @Date 12:57 2018/12/21
     * @Param
     * @return
     **/
    List<LauncThemeClassVo> selectLauncThemeClassVo(LauncThemeClassVo launcThemeClassVo);

    /**
     * 批量更新
     * @Author zhaodong
     * @Date 9:48 2018/12/27
     * @Param
     * @return
     **/
    int orderThemeClass(@Param("list") List<Map> listMap);

    /**
     * 根据主题id查询分类id
     * @Author zhaodong
     * @Date 9:45 2019/1/16
     * @Param
     * @return
     **/
    List<LaunThemeAdministration> selectClassIdByThemId(@Param("list") List<String> themId);

    /**
     * 查询主题的数量
     * @Author zhaodong
     * @Date 11:01 2019/1/16
     * @Param
     * @return
     **/
    List<Integer> selectThemeCountByClassId(@Param("list")List<String> list);

    /**
     * 查询主题上架的数量
     * @Author zhaodong
     * @Date 11:01 2019/1/16
     * @Param
     * @return
     **/
    List<Integer> selectThemeCountUpByClassId(@Param("list")List<String> list);

    /**
     * 批量更新分类下主题数量
     * @Author zhaodong
     * @Date 11:41 2019/1/16
     * @Param
     * @return
     **/
    void upClassThemTypeInOrDe(@Param("list") List<Map> themId);

    /**
     * 批量更新分类下主题上架数量
     * @Author zhaodong
     * @Date 11:41 2019/1/16
     * @Param
     * @return
     **/
    void upClassThemTypeUpOrDown(@Param("list") List<Map> themId);

    /**
     * 单个修改权重
     * @Author zhaodong
     * @Date 11:01 2019/1/17
     * @Param
     * @return
     **/
    void updateThemTypeOrder(int sort,String id);

}
