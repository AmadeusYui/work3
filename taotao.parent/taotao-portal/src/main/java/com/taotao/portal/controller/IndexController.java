package com.taotao.portal.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        String adJson = contentService.getContentList();
        System.out.println(adJson);
        model.addAttribute("ad1",adJson);
        return "index";
    }
}
