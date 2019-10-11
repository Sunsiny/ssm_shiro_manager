package com.syc.perms.shiro;

import com.syc.perms.mapper.AdminMenusMapper;
import com.syc.perms.mapper.TbAdminMapper;
import com.syc.perms.pojo.TbAdmin;
import com.syc.perms.pojo.TbMenus;
import com.syc.perms.service.AdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;

    //获得主页面的信息
    @Autowired
    AdminMenusMapper adminMenusMapper;

    //用户的认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken tk) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) tk;
        String username = token.getUsername();

        //登录，通过用户名
        TbAdmin admin = adminService.findAdminByName(username);
       // System.out.println("后台拿到的用户信息admin:"+4+":"+admin);
        if (admin == null) {
            throw new UnknownAccountException("账号不存在");
        }

        //String password=new String(token.getPassword());
        String password = new Md5Hash(token.getPassword()).toString();
        if (!admin.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("用户名或密码不正确");
        }

        if (admin.getRoleId() == null || admin.getRoleId() == 0) {
            throw new UnknownAccountException("账号有异常");
        }

        //封装认证信息
        return new SimpleAuthenticationInfo(admin, password, getName());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        //collection.getPrimaryPrincipal();
        TbAdmin admin = (TbAdmin) super.getAvailablePrincipal(collection);
        Long roleId = admin.getRoleId();
      //  System.out.println("用户登录的角色id："+roleId);
        //调用用户角色的方法
        List<TbMenus> menus = adminMenusMapper.getMenus(roleId);

        List<String> permList = new ArrayList<>();
        for (TbMenus menu : menus) {
            //if (menu!=null) {
            //    permList.add(menu.getPerms());
            //}
            //IllegalArgumentException: Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.
            if (menu.getPerms() != null&&!"".equals(menu.getPerms())) {
                permList.add(menu.getPerms());
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addRole();
        info.addStringPermissions(permList);
        return info;
    }



}
