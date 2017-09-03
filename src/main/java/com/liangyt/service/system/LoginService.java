package com.liangyt.service.system;

import com.liangyt.common.rest.MessageReturn;
import com.liangyt.entity.system.User;
import org.apache.commons.lang3.StringUtils;
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

        User loginUser = userService.findUserBoUserno(user.getUsername());

        if (loginUser == null) {
            return MessageReturn.fail("用户不存在");
        }

        if (loginUser.getStatus() != 0) {
            return MessageReturn.fail("用户被锁住");
        }

        if (!StringUtils.equals(loginUser.getPassword(), user.getPassword())) {
            return MessageReturn.fail("密码不正确");
        }

        user.setName(loginUser.getName());

        return MessageReturn.success("登录成功");
    }
}
