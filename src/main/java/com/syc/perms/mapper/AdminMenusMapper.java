package com.syc.perms.mapper;

import com.syc.perms.pojo.TbMenus;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMenusMapper {
    //查询用户登录的角色信息，并且用户具备哪些权限的管理
    @Select("SELECT m.menu_id as menuId, m.title, m.icon, m.href, m.perms, m.spread, m.parent_id as parentId, m.sorting " +
            "from tb_menus m " +
            "LEFT JOIN tb_roles_menus rm " +
            "ON rm.menu_id = m.menu_id WHERE rm.role_id = #{0} ORDER BY m.sorting DESC")
    List<TbMenus> getMenus(Long roleId);
}
