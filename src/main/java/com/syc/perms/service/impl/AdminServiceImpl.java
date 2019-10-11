package com.syc.perms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.perms.mapper.*;
import com.syc.perms.pojo.*;
import com.syc.perms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    //管理员用户信息的各种方法
    @Autowired
    private TbAdminMapper adminMapper;
    //角色id和对的菜单显示的ID
    @Autowired
    private TbRolesMenusMapper rolesMenusMapper;
    //角色对应的权限
    @Autowired
    private AdminMenusMapper adminMenusMapper;
    //角色的称呼
    @Autowired
    private TbRolesMapper rolesMapper;
    @Autowired
    private TbMenusMapper menusMapper;


    //登录
    @Override
    public TbAdmin findAdminByName(String username) {
        TbAdminExample example = new TbAdminExample();
        TbAdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        //调用查询方法
        List<TbAdmin> tbAdmins = adminMapper.selectByExample(example);

        if (tbAdmins.size() > 0) {
            return tbAdmins.get(0);
        }

        return null;
    }
    //主页面左侧菜单栏的显示方法
    @Override
    public List<Menu> getMenus(TbAdmin admin) {
        //集合，放树形控件
        List<Menu> results = new ArrayList<>();
        //得到角色id
        Long roleId = admin.getRoleId();
        TbRolesMenusExample example = new TbRolesMenusExample();
        TbRolesMenusExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        //菜单id对应的角色的id
        List<TbRolesMenusKey> list = rolesMenusMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            //通过角色id，得到用户对应的权限信息
            List<TbMenus> menus = adminMenusMapper.getMenus(roleId);
            for (int i = 0; i < menus.size(); i++) {
                TbMenus tbMenu = menus.get(i);
                //parentId=0,说明该菜单是顶层父级目录
                if (menus.get(i).getParentId() == 0) {
                    //封装自身的菜单信息
                    Menu menu = new Menu();
                    menu.setTitle(tbMenu.getTitle());
                    menu.setIcon(tbMenu.getIcon());
                    menu.setHref(tbMenu.getHref());
                    menu.setSpread(tbMenu.getSpread());

                    //判断自身是否有子菜单,如果有则封装子菜单的信息
                    List<Menu> childMenus = new ArrayList<>();
                    for (int j = 0; j < menus.size(); j++) {
                        if (menus.get(j).getParentId() == tbMenu.getMenuId()) {
                            Menu menu2 = new Menu();
                            menu2.setTitle(menus.get(j).getTitle());
                            menu2.setIcon(menus.get(j).getIcon());
                            menu2.setHref(menus.get(j).getHref());
                            menu2.setSpread(menus.get(j).getSpread());
                            childMenus.add(menu2);
                        }
                    }
                    menu.setChildren(childMenus);
                    results.add(menu);
                }
            }
        }
        return results;
    }
    //通过用户名和密码查找用户
    @Override
    public TbAdmin findAdminByNameAndPwd(String username, String oldPwd) {
        //对旧的密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(oldPwd.getBytes());
        TbAdminExample example = new TbAdminExample();
        //链式调用--->构建器模式.
        //当一个类中有大量的设置的时候,就可以考虑使用构建器模式.
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andPasswordEqualTo(password);
        //执行通过用户名和密码找用户信息的方法
        List<TbAdmin> admins = adminMapper.selectByExample(example);
        if (admins.size() > 0) {
            //返回
            return admins.get(0);
        }
        return null;
    }
    //修改密码
    @Override
    public void updatePwd(TbAdmin admin) {
        //得到加密后的旧密码
        String password = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes());
        //设置旧密码
        admin.setPassword(password);
        //执行修改密码的方法
        adminMapper.updateByPrimaryKeySelective(admin);
    }
    //角色列表的分页
    @Override
    public R getRoleList(Integer page, Integer limit) {
        //注意:这句话必须写在select查询语句之前!!!
        //PageHelper.startPage(page, limit);

        PageHelper.startPage(page, limit);

        List<TbRoles> roles = rolesMapper.selectByExample(new TbRolesExample());

        PageInfo<TbRoles> info = new PageInfo<>(roles);
        R result = new R();
        result.setCode(0);
        result.setCount(info.getTotal());
        result.setData(info.getList());
        return result;
    }
    //通过角色名查角色信息
    @Override
    public TbRoles getRoleByName(String roleName) {
        //查询角色
        TbRolesExample example = new TbRolesExample();
        example.createCriteria().andRoleNameEqualTo(roleName);
        List<TbRoles> roles = rolesMapper.selectByExample(example);
        //判断角色是否为空
        if (roles != null && roles.size() > 0) {
            return roles.get(0);
        }
        return null;
    }

    //添加角色的方法
    @Override
    public void addRole(TbRoles role, String menuIds) {
        //添加到角色表
        rolesMapper.insert(role);
        //级联添加该角色对应的权限---tb_roles_menu表中添加该角色对应的权限.
        //menuId:"1,3,4,5,..."
        addRoleMenu(role, menuIds);
    }
    //角色的权限分配
    @Override
    public List<TbMenus> getRolePermissionTree(TbAdmin admin) {
        TbMenusExample example = new TbMenusExample();
        List<TbMenus> menus = menusMapper.selectByExample(example);

        Long roleId = admin.getRoleId();
        if (!roleId.equals(-1)) {
            TbRolesMenusExample roleMenusExample = new TbRolesMenusExample();
            TbRolesMenusExample.Criteria criteria = roleMenusExample.createCriteria();
            criteria.andRoleIdEqualTo(roleId);
            List<TbRolesMenusKey> roleMenus = rolesMenusMapper.selectByExample(roleMenusExample);
            for (TbMenus menu : menus) {
                for (TbRolesMenusKey key : roleMenus) {
                    if (menu.getMenuId().equals(key.getMenuId())) {
                        //决定了该菜单可以被展开
                        menu.setChecked("true");
                    }
                }
            }
        }
        return menus;
    }

    @Override
    public TbRoles getRoleById(Long roleId) {
        return rolesMapper.selectByPrimaryKey(roleId);
    }

    //编辑角色的信息
    @Override
    public void updateRole(TbRoles role, String menuIds) {
        //单纯角色表
        rolesMapper.updateByPrimaryKeySelective(role);

        //删除原有的权限
        TbRolesMenusExample example = new TbRolesMenusExample();
        TbRolesMenusExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(role.getRoleId());
        rolesMenusMapper.deleteByExample(example);

        //再次添加新的权限
        addRoleMenu(role, menuIds);
    }

    @Override
    public void deleteOne(Long roleId) {
        rolesMapper.deleteByPrimaryKey(roleId);
    }
    //批量删除角色
    @Override
    public void deleteMore(String roleIds) {
        String[] ids = roleIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            rolesMapper.deleteByPrimaryKey(Long.parseLong(ids[i]));
        }
        //TbRolesExample example = new TbRolesExample();
        //example.createCriteria().andRoleIdIn(ids);
        //rolesMapper.deleteByExample(example);
    }

    //方法抽取
    private void addRoleMenu(TbRoles role, String menuIds) {
        //不为空
        if (menuIds != null && !"".equals(menuIds)) {
            String[] menuId = menuIds.split(",");
            for (String menu : menuId) {
                TbRolesMenusKey roleMenu = new TbRolesMenusKey();
                roleMenu.setMenuId(Long.parseLong(menu));
                //注意:roleId的赋值
                roleMenu.setRoleId(role.getRoleId());
                System.out.println("reloMenu++++++"+roleMenu);
                rolesMenusMapper.insert(roleMenu);
            }
        }
    }

}
