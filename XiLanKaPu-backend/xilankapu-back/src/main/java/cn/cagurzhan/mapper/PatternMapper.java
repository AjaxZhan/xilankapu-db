package cn.cagurzhan.mapper;

import cn.cagurzhan.domain.entity.Pattern;
import cn.cagurzhan.domain.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (Pattern)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-24 17:01:32
 */
@Mapper
public interface PatternMapper extends BaseMapperPlus<PatternMapper,Pattern,Pattern> {

    Page<Pattern> selectPageList(@Param("page")Page<Pattern> build, @Param(Constants.WRAPPER)Wrapper<Pattern> userWrapper);
}
