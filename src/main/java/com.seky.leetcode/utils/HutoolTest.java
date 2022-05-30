package com.seky.leetcode.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

/**
 * @author: wf
 * @create: 2022/5/27 14:03
 * @description:
 */
public class HutoolTest {
    
    public static void main(String[] args) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 1000);
        lineCaptcha.write("D:/image.png");

        //输出code
        String code = lineCaptcha.getCode();
        System.out.println("验证码: " + code);
        System.out.println(lineCaptcha.verify(code));
    }
    
}
