/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: TestPink
 * Author:   gengxin.hao
 * Date:     2021/5/25 16:27
 * Description:
 * History:
 */
package com.hgx.springboot.example.web;

//import e.e.H;
import e.e.H;
import e.e.L;
import org.junit.Test;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/5/25
 * @since 1.0.0
 */
public class TestPink {

    @Test
    public void pink() {
        L.ALLATORIxDEMO("D:\\my-project\\example-parent\\example-web\\src\\main\\resources\\");
        String pwd = H.ALLATORIxDEMO("6212142200000000075","123456","utf-8");
        System.out.println("password" + pwd);
    }
}
