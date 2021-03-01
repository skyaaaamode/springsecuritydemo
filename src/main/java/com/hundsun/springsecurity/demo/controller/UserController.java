package com.hundsun.springsecurity.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/user/api")
public class UserController {

    @GetMapping("hello")
    public String hello() {
        return "hello, user";
    }


    @GetMapping("getImage")
    public void getImage(HttpServletRequest request, HttpServletResponse response, String name) {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = new FileInputStream("E:\\file\\arch\\springsecuritydemo\\src\\main\\resources\\test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        response.setContentType("image/png");
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = resourceAsStream.read(buff, 0, 100)) > 0) {
                out.write(buff, 0, rc);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
