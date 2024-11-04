package cn.cagurzhan.controller;

import cn.cagurzhan.domain.R;
import cn.cagurzhan.domain.entity.PClass;
import cn.cagurzhan.domain.entity.Pattern;
import cn.cagurzhan.domain.page.PageQuery;
import cn.cagurzhan.domain.page.PureDataInfo;
import cn.cagurzhan.mapper.PClassMapper;
import cn.cagurzhan.service.PatternService;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cagur Zhan
 */
@RestController
@RequestMapping("/pattern")
@Slf4j
@RequiredArgsConstructor
public class PatternController extends BaseController {

    private final PatternService patternService;
    private final PClassMapper pClassMapper;

    /**
     * 添加纹样
     */
    @PostMapping
    public R<Void> add(@RequestBody Pattern pattern){
        return toAjax(patternService.insertPattern(pattern));
    }
    /**
     * 分页按条件查找纹样
     */
    @GetMapping("/list")
    public PureDataInfo<Pattern> list(Pattern pattern, PageQuery pageQuery){
        return patternService.queryPage(pattern,pageQuery);
    }

    /**
     * 更新纹样
     */
    @PutMapping
    public R<Void> edit(@RequestBody Pattern pattern){
        return toAjax(patternService.updatePattern(pattern));
    }

    /**
     * 删除纹样
     */
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Integer id){
        return toAjax(patternService.deletePatternById(id));
    }
    /**
     * 获取纹样分类
     */
    @GetMapping("/pclass")
    public R<List<PClass>> getPClass(){
        return R.ok(pClassMapper.selectList());
    }

    /**
     * 通过模糊查询，查询纹样信息
     */
    @GetMapping("/{name}")
    @SaIgnore
    @CrossOrigin(origins = "*")
    public R<Pattern> getPatternByName(@PathVariable("name")String name){
        LambdaQueryWrapper<Pattern> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Pattern::getName,name);
        List<Pattern> list = patternService.list(wrapper);
        if(ObjectUtil.isNull(list)){
            return R.fail("查无此纹样");
        }
        PClass pClass = pClassMapper.selectById(list.get(0).getPclassId());
        list.get(0).setPClass(pClass);
        return R.ok(list.get(0));
    }

}
