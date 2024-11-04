package cn.cagurzhan.service;

import cn.cagurzhan.domain.entity.Pattern;
import cn.cagurzhan.domain.page.PageQuery;
import cn.cagurzhan.domain.page.PureDataInfo;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (Pattern)表服务接口
 *
 * @author makejava
 * @since 2024-01-24 17:01:34
 */
public interface PatternService extends IService<Pattern> {

    /**
     * 新增一个纹样
     * @param pattern 纹样
     * @return 失败或者成功
     */
    int insertPattern(Pattern pattern);

    /**
     * 更新纹样信息
     */
    int updatePattern(Pattern pattern);

    /**
     * 删除一条纹样
     */
    int deletePatternById(Integer id);

    /**
     * 分页查询纹样列表
     * @param pattern 查询条件
     * @param pageQuery 分页
     * @return 分页的数据
     */
    PureDataInfo<Pattern> queryPage(Pattern pattern, PageQuery pageQuery);
}
