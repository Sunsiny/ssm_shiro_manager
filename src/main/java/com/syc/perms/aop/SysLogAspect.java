package com.syc.perms.aop;

import com.syc.perms.annotation.SysLog;
import com.syc.perms.pojo.TbAdmin;
import com.syc.perms.pojo.TbLog;
import com.syc.perms.service.LogService;
import com.syc.perms.util.ShiroUtils;
import com.syc.perms.util.WebUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 这是一个切面类
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private LogService logService;

    //定义切入点表达式
    //@Pointcut("execution(* com.syc.perms.aop.*.*.(..))")--->匹配某个包里的某个类里的某个方法
    //下面这个写法去匹配某个注解
    @Pointcut("@annotation(com.syc.perms.annotation.SysLog)")
    public void point() {
    }

    //通过切面获取日志信息
    //5种通知:前置,后置,环绕,异常,最终
    //try{if(){}}catch(exception e){}finally{}
    @Before("point()")
    public void getLogInfo(JoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //String ip = request.getRemoteAddr();

        TbLog log = new TbLog();
        log.setCreateTime(new Date());
        String ip = WebUtils.getRemoteAddr(request);
        log.setIp(ip);

        //比如这个路径/sys/updRole
        String requestURI = request.getRequestURI();
        log.setMethod(requestURI);

        //target:目标类--->AdminController;辅助类--->SysLogAspect
        //Object target = joinPoint.getTarget();
        //Class clazz=target.getClass();

        //获取目标方法的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                params += args[i] + ";";
            }
        }

        log.setParams(params);

        //获取方法的描述信息
        //Class<?> clazz = joinPoint.getTarget().getClass();
        //得到方法签名
        //String methodName = joinPoint.getSignature().getName();
        //clazz.getMethod(methodName)

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //editRole
        Method method = signature.getMethod();
        //method.invoke()
        SysLog annotation = method.getAnnotation(SysLog.class);
        if(annotation!=null){
            String value = annotation.value();
            log.setOperation(value);
        }

        TbAdmin admin = ShiroUtils.getUserEntity();
        log.setUsername(admin.getUsername());

        //此处是把日志信息(敏感)记录到了表中;
        //其他信息可以记录到日志文件中...
        logService.saveLog(log);
    }

}
