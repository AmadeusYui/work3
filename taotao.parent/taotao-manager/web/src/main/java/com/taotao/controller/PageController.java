package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于页面跳转
 */
@Controller
public class PageController {

    //打开首页
    @RequestMapping("/")
    public String showIndex(){
        System.out.println("1");
        return "index";
    }

    @RequestMapping("/{page}")
    public String showpage(@PathVariable String page){
        return page;
    }
}
