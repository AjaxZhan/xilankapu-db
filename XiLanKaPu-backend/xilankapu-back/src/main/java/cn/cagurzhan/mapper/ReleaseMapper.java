package cn.cagurzhan.mapper;

import cn.cagurzhan.domain.entity.Release;
import cn.cagurzhan.domain.vo.ReleaseVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Release)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-24 22:51:38
 */
@Mapper
public interface ReleaseMapper extends BaseMapperPlus<ReleaseMapper, Release,Release> {

}
