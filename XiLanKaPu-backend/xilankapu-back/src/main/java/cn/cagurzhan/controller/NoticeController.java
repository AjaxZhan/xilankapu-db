package cn.cagurzhan.controller;

import cn.cagurzhan.domain.R;
import cn.cagurzhan.domain.entity.Release;
import cn.cagurzhan.domain.vo.ReleaseVo;
import cn.cagurzhan.mapper.ReleaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Cagur Zhan
 * 通知控制器
 */
@RestController
@Slf4j
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final ReleaseMapper releaseMapper;

    @GetMapping("/release")
    public R<List<ReleaseVo>> release(){
        // 查询所有版本信息
        List<Release> releases = releaseMapper.selectList();
        // 转换为Vo并排序
        List<ReleaseVo> releaseVos = releases.stream().map(release -> {
            ReleaseVo releaseVo = new ReleaseVo();
            releaseVo.setBody(release.getBody());
            releaseVo.setPublished_at(release.getPublishedAt());
            releaseVo.setCreate_at(release.getCreateAt());
            return releaseVo;
        }).sorted((o1, o2) -> o2.getCreate_at().compareTo(o1.getCreate_at())).collect(Collectors.toList());

        return R.ok(releaseVos);
    }
}
