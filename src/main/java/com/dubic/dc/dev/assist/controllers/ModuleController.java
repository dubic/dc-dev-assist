/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.dc.dev.assist.controllers;

import com.dubic.dc.dev.assist.dao.ModFile;
import com.dubic.dc.dev.assist.services.ModuleService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dubic
 */
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired private ModuleService moduleService;
    
    @RequestMapping("/{name}/prepare")
    public List<ModFile> prepare(@RequestBody Map module) {
//        System.out.println("printing map : "+module.size());
//        for (Object key : module.keySet()) {
//            System.out.println(key+" : "+module.get(key));
//        }    
        //get list of affected files from module name
        return this.moduleService.listAffectedFiles(module);
//        return files;
    }
    
    @RequestMapping("/file/view")
    public Map viewFile(@RequestBody Map m) {
        System.out.println("printing path : "+m.get("path"));
        try {
            m.put("text", moduleService.getFile(m.get("path").toString()));
            m.put("found", true);
        } catch (IOException iOException) {
            m.put("found", false);
        }
        return m;
    }
}
