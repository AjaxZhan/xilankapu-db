package cn.cagurzhan.service.impl;

import cn.cagurzhan.constant.UserConstants;
import cn.cagurzhan.domain.entity.Pattern;
import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.domain.page.PageQuery;
import cn.cagurzhan.domain.page.PureDataInfo;
import cn.cagurzhan.mapper.PatternMapper;
import cn.cagurzhan.service.PatternService;
import cn.cagurzhan.utils.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * (Pattern)表服务实现类
 *
 * @author makejava
 * @since 2024-01-24 17:01:34
 */
@Service("patternService")
@RequiredArgsConstructor
public class PatternServiceImpl extends ServiceImpl<PatternMapper, Pattern> implements PatternService {

    private final PatternMapper patternMapper;

    /**
     * 新增一条纹样
     */
    @Override
    public int insertPattern(Pattern pattern) {
        // 补充前端没有传的字段
        pattern.setDelFlag("0");
        // 插入
        return patternMapper.insert(pattern);
    }

    /**
     * 更新一条纹样
     */
    @Override
    public int updatePattern(Pattern pattern) {
        return patternMapper.updateById(pattern);
    }

    @Override
    public int deletePatternById(Integer id) {
        return patternMapper.deleteById(id);
    }

    @Override
    public PureDataInfo<Pattern> queryPage(Pattern pattern, PageQuery pageQuery) {
        Page<Pattern> page = patternMapper.selectPageList(pageQuery.build(),this.buildQueryWrapper(pattern));
        return PureDataInfo.build(page);
    }

    private Wrapper<Pattern> buildQueryWrapper(Pattern pattern) {
        Map<String, Object> params = new HashMap<>();
        // 查询：pClass、piClass、pName
        QueryWrapper<Pattern> wrapper = Wrappers.query();
        wrapper.eq("p.del_flag", UserConstants.USER_NORMAL)
                // id
                .eq(ObjectUtil.isNotNull(pattern.getId()), "p.id", pattern.getId())
                // 皮尔斯三性
                .eq(ObjectUtil.isNotNull(pattern.getPiClass()), "p.pi_class", pattern.getPiClass())
                // 分类
                .eq(ObjectUtil.isNotNull(pattern.getPclassId()), "p.pclass_id", pattern.getPclassId())
                // 纹样名字
                .like(StringUtils.isNotBlank(pattern.getName()), "p.name", pattern.getName());
        return wrapper;
    }
}

