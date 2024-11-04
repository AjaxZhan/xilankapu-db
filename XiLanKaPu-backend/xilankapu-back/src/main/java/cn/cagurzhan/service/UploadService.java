package cn.cagurzhan.service;

import cn.cagurzhan.config.properties.OssProperties;
import cn.cagurzhan.domain.entity.Pattern;
import cn.cagurzhan.mapper.PatternMapper;
import cn.cagurzhan.utils.PathUtils;
import cn.cagurzhan.domain.vo.UploadVo;
import cn.cagurzhan.exception.ServiceException;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author Cagur Zhan
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UploadService {

    private final OssProperties ossProperties;
    private final PatternMapper patternMapper;

    /**
     * 上传给定的文件，返回文件地址
     * @param img 二进制文件
     * @return 地址
     */
    public String testUpload(MultipartFile img,Long id, Integer type){
        // 检查文件类型
        checkFileType(img);
        // 生成文件名
        String ossName = PathUtils.generateFilePath(img.getOriginalFilename());
        // 上传文件
        String url =  uploadOss(img, ossName);
        // 存入数据库
        Pattern pattern = new Pattern();
        pattern.setId(id);
        if(type == 0){
            pattern.setPicMain(url);
        }else if(type == 1){
            pattern.setPicSimple(url);
        }else if(type == 2){
            pattern.setPicColor(url);
        }
        patternMapper.updateById(pattern);
        // 返回
        return url;
    }

    /**
     * 将文件上传到七牛云
     * @param doc 二进制文件
     * @param filename 文件名
     * @return 链接
     */
    private String uploadOss(MultipartFile doc, String filename){

        String url = "";

        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2; // 指定分片上传版本
        UploadManager uploadManager = new UploadManager(cfg);

        String key = filename; // 文件名
        try {
            InputStream data = doc.getInputStream(); // 文档数据
            Auth auth = Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
            String upToken = auth.uploadToken(ossProperties.getBucket());

            try {
                Response response = uploadManager.put(data,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                log.info("文件上传成功，文件名:{}，哈希值:{}",putRet.key,putRet.hash);
                url =  "http://" + ossProperties.getDomain() + "/" + key;
                return url;
            } catch (QiniuException ex) {
                log.warn("七牛云异常:{}",ex.getMessage());
                if (ex.response != null) {
                    log.error("七牛云上传错误:{}",ex.response);
                    try {
                        String body = ex.response.toString();
                        log.error("七牛云上传错误:{}",body);
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception ex) {

        }
        throw new ServiceException("上传文件过程出现异常");
    }

    /**
     * 检查文件类型
     */
    private void checkFileType(MultipartFile doc){
        String name = doc.getOriginalFilename();
        if(!(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")
                || name.endsWith(".gif"))){
            throw new ServiceException("上传文件格式不正确");
        }
    }
}
