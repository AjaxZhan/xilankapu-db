package cn.cagurzhan.controller;

import cn.cagurzhan.domain.R;
import cn.cagurzhan.domain.vo.UploadVo;
import cn.cagurzhan.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 纹样图片上传接口
 * @author Cagur Zhan
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    /**
     * 测试上传方法
     * @param img 图片文件
     * @param type 上传类型，0 1 2分别表示pic_main, pic_simple, pic_color
     * @param id 纹样ID
     * @return 成功的对象
     */
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<UploadVo> testUpload(@RequestPart MultipartFile img, @RequestParam Integer type,@RequestParam Long id){
        String url = uploadService.testUpload(img, id, type);
        UploadVo uploadVo = new UploadVo();
        uploadVo.setId(id);
        uploadVo.setType(type);
        uploadVo.setUrl(url);
        return R.ok(uploadVo);
    }
}
