/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: SpringUtil
 * Author:   gengxin.hao
 * Date:     2021/4/7 16:52
 * Description:
 * History:
 */
package com.hgx.springboot.example.redis.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 〈Spring容器中获取Bean工具类〉
 *
 * @author gengxin.hao
 * @create 2021/4/7
 * @since 1.0.0
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @Description  根据类型返回实例对象
     * @author gengxin.hao
     * @CreateDate 2021/4/7 16:56
     * @param
     * @return
     * @exception
     * @Version 1.0
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @Description  根据名称返回实例对象
     * @author gengxin.hao
     * @CreateDate 2021/4/7 16:56
     * @param
     * @return
     * @exception
     * @Version 1.0
     */
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    /**
     * @Description  根据名称和类型返回实例对象
     * @author gengxin.hao
     * @CreateDate 2021/4/7 16:57
     * @param
     * @return
     * @exception
     * @Version 1.0
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return getApplicationContext().getBean(beanName, clazz);
    }

}
