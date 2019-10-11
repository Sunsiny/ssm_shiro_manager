package com.syc.perms.service;

import com.syc.perms.pojo.*;

import java.util.List;

public interface AdminService {
    // 登陆通过用户名
    TbAdmin findAdminByName(String username);
    //得到登陆后主页面左侧菜单的页面信息
    List<Menu> getMenus(TbAdmin admin);
    //通过用户名和密码，查找用户
    TbAdmin findAdminByNameAndPwd(String username, String oldPwd);
    //修改密码
    void updatePwd(TbAdmin admin);
    //分页
    R getRoleList(Integer page, Integer limit);
    //检查角色是否已经存在了，通过角色名查询
    TbRoles getRoleByName(String roleName);
    //添加角色
    void addRole(TbRoles role, String menuId);
    //角色权限选择
    List<TbMenus> getRolePermissionTree(TbAdmin admin);
    //通过id找role对象，编辑
    TbRoles getRoleById(Long roleId);
    //修改角色的信息
    void updateRole(TbRoles role, String menuIds);
    //删除角色信息
    void deleteOne(Long roleId);

    void deleteMore(String roleIds);
}
