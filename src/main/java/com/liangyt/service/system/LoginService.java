package com.liangyt.service.system;

import com.liangyt.common.rest.MessageReturn;
import com.liangyt.entity.system.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述：登录服务
 *
 * @author tony
 * @创建时间 2017-09-03 09:50
 */
@SuppressWarnings("all")
@Service
public class LoginService {
    private Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    public MessageReturn login(User user) {
        if (user == null || StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return MessageReturn.fail("登录信息不全");
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        }
        catch (UnknownAccountException e) {
            logger.error(e.getMessage(), e);
            return MessageReturn.fail("不存在该账户");
        }
        catch (IncorrectCredentialsException e) {
            logger.error(e.getMessage(), e);
            return MessageReturn.fail("密码不正确");
        }
        catch (LockedAccountException e) {
            logger.error(e.getMessage(), e);
            return MessageReturn.fail("账户已锁定");
        }
        catch (ExcessiveAttemptsException e) {
            logger.error(e.getMessage(), e);
            return MessageReturn.fail("用户名或密码错误次数过多");
        }
        catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            return MessageReturn.fail("用户名或密码不正确");
        }

        return MessageReturn.success("登录成功");
    }
}
