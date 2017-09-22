package com.liangyt.common.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 描述：aop 拦截器实现
 *
 * <table>
 *     <tr>
 *         <td>@Aspect</td><td>将一个java类定义为切面类</td>
 *     </tr>
 *     <tr>
 *         <td>@Pointcut</td><td>定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等</td>
 *     </tr>
 * </table>
 * <strong>5种类型的通知 </strong>
 * <table>
 *     <tr>
 *         <td>@Before</td><td>前置通知：在切入点开始处切入内容</td>
 *     </tr>
 *     <tr>
 *         <td>@After</td><td>最终通知：在切入点结尾处切入内容</td>
 *     </tr>
 *     <tr>
 *         <td>@AfterReturning</td><td>后置通知：在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）</td>
 *     </tr>
 *     <tr>
 *         <td>@Around</td><td>环绕通知：在切入点前后切入内容，并自己控制何时执行切入点自身的内容</td>
 *     </tr>
 *     <tr>
 *         <td>@AfterThrowing</td><td>异常通知：用来处理当切入内容部分抛出异常之后的处理逻辑</td>
 *     </tr>
 * </table>
 * <strong>通知参数</strong>
 * <p>任何通知方法可以将第一个参数定义为org.aspectj.lang.JoinPoint类型（环绕通知需要定义第一个参数为ProceedingJoinPoint类型，它是 JoinPoint 的一个子类）。JoinPoint接口提供了一系列有用的方法，比如 getArgs()（返回方法参数）、getThis()（返回代理对象）、getTarget()（返回目标）、getSignature()（返回正在被通知的方法相关信息）和 toString()（打印出正在被通知的方法的有用信息）</p>
 * <strong>切入点表达式</strong>
 * <pre>
 *     切入点表达式的格式：execution([可见性] 返回类型 [声明类型].方法名(参数) [异常])

     　　其中【】中的为可选，其他的还支持通配符的使用:

     　   *：匹配所有字符
         ..：一般用于匹配多个包，多个参数
          +：表示类及其子类

     　　运算符有：&&、||、!
 * </pre>
 * <strong>切入点表达式关键词</strong>
 * <pre>
 *     　 1）execution：用于匹配子表达式。
         //匹配com.cjm.model包及其子包中所有类中的所有方法，返回类型任意，方法参数任意
         @Pointcut("execution(* com.cjm.model..*.*(..))")
         public void before(){}

         2）within：用于匹配连接点所在的Java类或者包。

         //匹配Person类中的所有方法
         @Pointcut("within(com.cjm.model.Person)")
         public void before(){}

         //匹配com.cjm包及其子包中所有类中的所有方法

         @Pointcut("within(com.cjm..*)")
         public void before(){}

         3） this：用于向通知方法中传入代理对象的引用。
         @Before("before() && this(proxy)")
         public void beforeAdvide(JoinPoint point, Object proxy){
         //处理逻辑
         }

         4）target：用于向通知方法中传入目标对象的引用。
         @Before("before() && target(target)
         public void beforeAdvide(JoinPoint point, Object proxy){
         //处理逻辑
         }

         5）args：用于将参数传入到通知方法中。
         @Before("before() && args(age,username)")
         public void beforeAdvide(JoinPoint point, int age, String username){
         //处理逻辑
         }

         6）@within ：用于匹配在类一级使用了参数确定的注解的类，其所有方法都将被匹配。

         @Pointcut("@within(com.cjm.annotation.AdviceAnnotation)") － 所有被@AdviceAnnotation标注的类都将匹配
         public void before(){}

         7）@target ：和@within的功能类似，但必须要指定注解接口的保留策略为RUNTIME。
         @Pointcut("@target(com.cjm.annotation.AdviceAnnotation)")
         public void before(){}

         8）@args ：传入连接点的对象对应的Java类必须被@args指定的Annotation注解标注。
         @Before("@args(com.cjm.annotation.AdviceAnnotation)")
         public void beforeAdvide(JoinPoint point){
         //处理逻辑
         }

         9）@annotation ：匹配连接点被它参数指定的Annotation注解的方法。也就是说，所有被指定注解标注的方法都将匹配。
         @Pointcut("@annotation(com.cjm.annotation.AdviceAnnotation)")
         public void before(){}

         10）bean：通过受管Bean的名字来限定连接点所在的Bean。该关键词是Spring2.5新增的。
         @Pointcut("bean(person)")
         public void before(){}
 * </pre>
 * @author tony
 * @创建时间 2017-08-23 11:40
 */
@Aspect
@Component
public class RestRequestMappingAspectInterceptor {

    Logger logger = LoggerFactory.getLogger(RestRequestMappingAspectInterceptor.class);

    /**
     * 定义拦截规则
     */
    @Pointcut("execution(* com.liangyt.rest..* (..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcat() {

    }

    /**
     * 前置通知
     */
    @Before("controllerMethodPointcat()")
    public void beforeInterceptor(JoinPoint jp) {
        logger.info("我是前置通知");
    }

    /**
     * 最终通知
     * @param jp
     */
    @After("controllerMethodPointcat()")
    public void afterInterceptor(JoinPoint jp) {
        logger.info("我的最终通知");
    }

    /**
     * 后置通知
     * @param jp
     */
    @AfterReturning("controllerMethodPointcat()")
    public void afterReturningInterceptor(JoinPoint jp) {
        logger.info("我是后置通知");
    }

    /**
     * 环绕通知
     * @param pjp
     * @return
     */
    @Around("controllerMethodPointcat()")
    public Object Interceptor(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();

        ObjectMapper objectMapper = new ObjectMapper();
        Object[] args = pjp.getArgs();

        logger.info("请求的方法名：" + methodName);
        try {
            logger.info("请求的参数：" + objectMapper.writeValueAsString(args));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
//            e.printStackTrace();
        }

        Object result = null;

        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return result;
    }
}
