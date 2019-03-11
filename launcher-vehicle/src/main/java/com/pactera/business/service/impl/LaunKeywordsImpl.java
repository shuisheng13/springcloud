package com.pactera.business.service.impl;

import com.pactera.business.dao.LaunKeywordsMapper;
import com.pactera.business.service.LaunKeywordsService;
import com.pactera.domain.LaunKeywords;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LaunKeywords
 * @Description
 * @Author xukj
 * @Date 2019/3/4 16:47
 * @Version
 */

@Service
public class LaunKeywordsImpl implements LaunKeywordsService {

    @Resource
    private LaunKeywordsMapper launKeywordsMapper;

    @Override
    public List<String> keywords() {
        Example example = new Example(LaunKeywords.class);
        example.setOrderByClause("top desc,sort desc");
        return launKeywordsMapper.selectByExample(example).stream().map(
                k-> k.getKeyword()).collect(Collectors.toList());
    }
}
