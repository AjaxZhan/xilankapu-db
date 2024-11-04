package cn.cagurzhan.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Cagur Zhan
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PathUtils {
    public static String generateFilePath(String filename){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String postfix = filename.substring(filename.lastIndexOf("."));
        return new StringBuilder().append(datePath).append(uuid).append(postfix).toString();
    }
}
