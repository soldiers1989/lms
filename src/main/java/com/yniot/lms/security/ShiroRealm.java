package com.yniot.lms.security;

import com.yniot.lms.db.entity.Auth;
import com.yniot.lms.db.entity.Role;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.service.AuthService;
import com.yniot.lms.service.RoleService;
import com.yniot.lms.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class ShiroRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Resource
    private UserService userService;
    @Resource
    private RoleService sysRoleService;
    @Resource
    private AuthService authService;

    /**
     * 配置权限 注入权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        try {
            List<Role> roleList = sysRoleService.selectRoleByUserId(user.getId());
            List<Auth> authList = authService.selectByUserId(user.getId());
            authorizationInfo.addRoles(roleList.stream().map(Role::getRolename).collect(Collectors.toList()));
            authorizationInfo.addStringPermissions(authList.stream().map(Auth::getPermission).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /**
     * 用户验证
     *
     * @param token 账户数据
     * @return
     * @throws AuthenticationException 根据账户数据查询账户。根据账户状态抛出对应的异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        User user = userService.selectByUsername(username);
        if (null == user) {
            throw new UnknownAccountException();
        } else {
            if (password.equals(user.getPassword())) {
                if (0 == user.getState()) {
                    throw new LockedAccountException();
                } else if (2 == user.getState()) {
                    throw new DisabledAccountException();
                } else {
                    SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), getName());
                    return authorizationInfo;
                }
            } else {
                throw new IncorrectCredentialsException();
            }
        }
    }
}
