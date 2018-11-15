package com.yniot.lms.security;

import com.yniot.lms.db.pojo.SysAuth;
import com.yniot.lms.db.pojo.SysRole;
import com.yniot.lms.db.pojo.User;
import com.yniot.lms.service.SysAuthService;
import com.yniot.lms.service.SysRoleService;
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

public class ShiroRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    //这里尝试过使用@Autowired 但是发现会报错。这个是spring的注解。如果有知道原因的可以留言。谢谢
    @Resource
    private UserService userService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysAuthService authService;
    /**
     * 配置权限 注入权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        System.out.println("--------权限配置-------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        try {
            //注入角色(查询所有的角色注入控制器）
            List<SysRole> list = sysRoleService.selectRoleByUserId(user.getId());
            for (SysRole role: list){
                authorizationInfo.addRole(role.getRolename());
            }
            //注入角色所有权限（查询用户所有的权限注入控制器）
            List<SysAuth> sysAuths = authService.queryByUserId(user.getId());
            for(SysAuth sysAuth:sysAuths){
                authorizationInfo.addStringPermission(sysAuth.getPermission());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /**
     * 用户验证
     * @param token 账户数据
     * @return
     * @throws AuthenticationException 根据账户数据查询账户。根据账户状态抛出对应的异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号
        String username = (String) token.getPrincipal();
        //这里需注意。看别人的教程有人是这样写的String password = (String) token.getCredentials();
        //项目运行的时候报错，发现密码不正确。后来进源码查看发现将密码注入后。Shiro会进行转义将字符串转换成字符数组。
        //源码：this(username, password != null ? password.toCharArray() : null, false, null);
        //不晓得是否是因为版本的原因，建议使用的时候下载源码进行查看
        String password = new String((char[]) token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.selectByUsername(username);
        if(null == user){
            throw new UnknownAccountException();
        }else {
            if(password.equals(user.getPassword())){
                if(0 == user.getState()){
                    throw new LockedAccountException();
                }else if (2 == user.getState()){
                    throw new DisabledAccountException();
                }else{
                    SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(user,user.getPassword().toCharArray(),getName());
                    return authorizationInfo;
                }
            } else {
                throw new IncorrectCredentialsException();
            }
        }
    }
}
