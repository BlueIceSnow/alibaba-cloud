package com.tianqi.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuantianqi
 */
@RestController
@RequestMapping("movie")
public class DemoRest {

    @GetMapping("/movie")
    public Map<String, Object> demo() {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("msg", "Movie登录成功！");
        return objectObjectHashMap;
    }
}
