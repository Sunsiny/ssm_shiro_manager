package com.syc.perms.web;

import com.github.pagehelper.util.StringUtil;
import com.google.code.kaptcha.Producer;
import com.syc.perms.exception.CommonException;
import com.syc.perms.pojo.Menu;
import com.syc.perms.pojo.R;
import com.syc.perms.pojo.TbAdmin;
import com.syc.perms.service.AdminService;
import com.syc.perms.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/sys")
public class SysController {

    //第三方验证码
    @Autowired
    private Producer producer;
    //注入AdminService内的方法
    @Autowired
    private AdminService adminService;


    /**
     * 创建验证码
     */
    @RequestMapping(value = "/vcode", method = RequestMethod.GET)
    public void generateCode(HttpServletResponse response, HttpSession session) throws IOException {
        //随机生成一些文字
        String text = producer.createText();
        //然后把该文字绘制到图片上
        BufferedImage image = producer.createImage(text);
        //服务器要保存验证码
        session.setAttribute("kaptcha", text);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    //创建一个登陆接口
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public R login(String username, String password, String vcode, HttpSession session)throws IOException{
        if(StringUtil.isEmpty(username)||(StringUtil.isEmpty(password))||(StringUtil.isEmpty(vcode))){
            throw new CommonException("参数不能为空");
        }
        String kaptcha = ((String) session.getAttribute("kaptcha")).toLowerCase();
        if (!vcode.equals(kaptcha)) {
            return R.error("验证码不正确");
        }
        /*把常用的代码抽取成工具类:1.自己收集;2.Hutool*/
        //Subject subject = SecurityUtils.getSubject();
        Subject subject = ShiroUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        /*exception:1.受检异常;2.非受检异常*/
        //System.out.println("token:"+2+":"+token);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        } catch (UnauthenticatedException e) {
            return R.error("用户名或密码验证失败");
        }
       return R.ok();
    }

    //登录成功后进入主界面
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showMainPage() {
        return "page/main";
    }
    //展示侧菜单信息
    @ResponseBody
    @RequestMapping(value = "/getMenus", method = RequestMethod.GET)
    public List<Menu> getMenus() {
        //TbAdmin userEntity = ShiroUtils.getUserEntity();
        //使用shiro时，如果正常登陆（执行subject.login(token)成功）就能在全局通过SecurityUtils.getSubject().getPrincipal()获取用户信息。
        TbAdmin admin = (TbAdmin) SecurityUtils.getSubject().getPrincipal();
        //判断用户信息是否为空，不为空，就执行方法，得到侧菜单的信息
        if (admin != null) {
            return adminService.getMenus(admin);
        }
        return null;
    }
    //处理index.jsp界面，显示内容
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showIndex() {
        //TbAdmin admin = ShiroUtils.getUserEntity();
        //request.setAttribute("admin", admin);
        return "redirect:/index.jsp";
    }
    //退出登录
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:/login.jsp";
    }

    //跳转到修改密码界面
    @RequestMapping(value = "/changePwd", method = RequestMethod.GET)
    public String showChangePwd() {
        return "page/admin/changePwd";
    }
    //更改密码
    @ResponseBody
    @RequestMapping(value = "/updPwd", method = RequestMethod.POST)
    public R changePwd(String oldPwd, String newPwd) {
        //得到用户信息
        TbAdmin currentUser = (TbAdmin) SecurityUtils.getSubject().getPrincipal();
        //判断用户不为空
        if (currentUser != null) {
            //得到用户名和密码
            TbAdmin admin = adminService.findAdminByNameAndPwd(currentUser.getUsername(), oldPwd);
            //判断得到的用户不为空
            if (admin != null) {
                //设置新的密码
                admin.setPassword(newPwd);
                //修改密码
                adminService.updatePwd(admin);
                //退出登录，跳转到登录界面
                ShiroUtils.logout();
                return R.ok();
            } else {
                return new R(501, "旧密码错误,请重试...");
            }
        }
        //请检查自己的网络...
        return new R(500, "尚未登录,请登录...");
    }


}
