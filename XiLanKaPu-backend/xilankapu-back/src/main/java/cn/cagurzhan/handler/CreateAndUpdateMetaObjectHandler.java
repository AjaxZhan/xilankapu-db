package cn.cagurzhan.handler;

import cn.cagurzhan.domain.BaseEntity;
import cn.cagurzhan.domain.model.LoginUser;
import cn.cagurzhan.exception.ServiceException;
import cn.cagurzhan.helper.LoginHelper;
import cn.cagurzhan.utils.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MybatisPlus 字段更新处理器
 * @author Cagur
 * @version 1.0
 */
@Slf4j
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if(ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity){
            // 设置创建时间
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
            Date current = ObjectUtil.isNotNull(baseEntity.getCreateTime())
                    ? baseEntity.getCreateTime() : new Date();
            baseEntity.setCreateTime(current);
            baseEntity.setUpdateTime(current);

            String username = StringUtils.isNotBlank(baseEntity.getCreateBy())
                    ? baseEntity.getCreateBy() : getLoginUsername();
            // 当前已登录 且 创建人为空 则填充
            baseEntity.setCreateBy(username);
            // 当前已登录 且 更新人为空 则填充
            baseEntity.setUpdateBy(username);

        }

    }
    /**
     * 获取登录用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUsername() : null;
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();
                Date current = new Date();
                // 更新时间填充(不管为不为空)
                baseEntity.setUpdateTime(current);
                String username = getLoginUsername();
                // 当前已登录 更新人填充(不管为不为空)
                if (StringUtils.isNotBlank(username)) {
                    baseEntity.setUpdateBy(username);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }
}
