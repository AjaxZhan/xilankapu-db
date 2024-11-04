package cn.cagurzhan.exception.user;

import cn.cagurzhan.exception.BaseException;

/**
 * 用户信息异常类
 *
 * @author Cagur Zhan
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object... args) {
        super("user", code, args, null);
    }
}
