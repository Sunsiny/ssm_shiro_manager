package com.syc.perms.web;

import com.syc.perms.pojo.R;
import com.syc.perms.pojo.TbAdmin;
import com.syc.perms.pojo.TbMenus;
import com.syc.perms.pojo.TbRoles;
import com.syc.perms.service.AdminService;
import com.syc.perms.util.JsonUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//管理员管理列表
@Controller
@RequestMapping("/sys")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //用户角色列表，权限的大小显示的信息的量 -----展示角色信息的列表---跳转到角色信息列表
    @RequestMapping(value = "/roleList",method = RequestMethod.GET)
    @RequiresPermissions({"sys:role:list"})
    public String showRoleList() {
        return "page/admin/roleList";
    }
    //分页
    //RequiresPermissions:访问该接口需要具有如下权限
    @ResponseBody
    @RequestMapping(value = "/getRoleList",method = RequestMethod.GET)
    @RequiresPermissions({"sys:role:list"})
    public R getRoleList(Integer page, Integer limit) {
        //page:当前页码;limit:每页多少条
        return adminService.getRoleList(page,limit);
    }

    //跳转到添加角色的页面
    @RequestMapping(value = "/addRole", method = RequestMethod.GET)
    public String showAddRole() {
        return "page/admin/addRole";
    }

    //添加角色
    @ResponseBody
    @RequestMapping(value = "/insRole", method = RequestMethod.POST)
    public R addRole(TbRoles role, @RequestParam("m") String menuId) {
        TbRoles tbRoles = adminService.getRoleByName(role.getRoleName());
        System.out.println("tbRoles==========:"+tbRoles);
        if (tbRoles != null) {
            return new R(500, "角色已存在");
        }
        adminService.addRole(role, menuId);
        return R.ok();
    }
    //检查这个角色名是否已经存在
    @ResponseBody
    @RequestMapping(value = "/checkRoleName/{roleName}", method = RequestMethod.POST)
    public R checkRoleName(@PathVariable("roleName") String roleName) {
        System.out.println(1);
        TbRoles role = adminService.getRoleByName(roleName);
        System.out.println(2);
        if (role != null) {
            return new R(500, "角色已存在");
        }
        System.out.println("ok");
        return R.ok();
    }

    //添加角色时的权限分配    xtreedata=getRolePermissionTree
    //[{"menuId":,'xxx':},{"menuId":,'xxx':}]
    //produces:该属性类似于response.setContentType()方法
    @ResponseBody
    @RequestMapping(value = "/xtreedata", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String getRolePermissionTree(@RequestParam(value = "roleId", defaultValue = "-1") Long roleId) {

        //response.setContentType("application/json; charset=UTF-8");

        TbAdmin admin = new TbAdmin();
        admin.setRoleId(roleId);
        List<TbMenus> rolePermissionTree = adminService.getRolePermissionTree(admin);
        return JsonUtils.objectToJson(rolePermissionTree);
    }

    //跳转到角色编辑界面
    @RequestMapping(value = "/editRole", method = RequestMethod.GET)
    public String showEditRole(@RequestParam("roleId") Long roleId, Model model) {
        //通过id进行编辑，修改
        TbRoles role = adminService.getRoleById(roleId);
        //通过对象传值，把role对象传过去
        model.addAttribute("role", role);
        return "page/admin/editRole";
    }
    //编辑角色的信息
    @ResponseBody
    @RequestMapping(value = "/updRole", method = RequestMethod.POST)
    public void editRole(TbRoles role, @RequestParam("m") String menuIds) {
        adminService.updateRole(role, menuIds);
    }

    //删除角色，包括角色的权限
    @ResponseBody
    @RequestMapping(value = "/delRole/{roleId}", method = RequestMethod.GET)
    @RequiresPermissions("sys:role:delete")
    public R deleteOneRole(@PathVariable("roleId") Long roleId) {
        adminService.deleteOne(roleId);
        return R.ok();
    }

    //批量删除角色
    @ResponseBody
    @RequestMapping(value = "/delRoles/{roleId}", method = RequestMethod.GET)
    @RequiresPermissions("sys:role:delete")
    public R deleteRoles(@PathVariable("roleId") String roleIds) {
        adminService.deleteMore(roleIds);
        return R.ok();
    }

}
