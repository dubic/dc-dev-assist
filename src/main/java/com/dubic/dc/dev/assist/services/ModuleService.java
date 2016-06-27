/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.dc.dev.assist.services;

import com.dubic.dc.dev.assist.dao.ModFile;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

/**
 *
 * @author dubic
 */
@Service
public class ModuleService {
    private final String serviceKey = "servicename";

    public List<ModFile> listAffectedFiles(Map m) {
        List<ModFile> files = new ArrayList<>();
        String p = ev(m,"modulePath");
        String lname = m.get("moduleName").toString().toLowerCase();
        String peg = m.get("pegasus").toString().toLowerCase();
        //FILES START HERE
        files.add(createModFile(p+"/${servicename}-service/pom.xml", lname));
        files.add(createModFile(p+"/${servicename}-shared/pom.xml", lname));
        files.add(createModFile(peg+"/foundation-guiwar/src/main/webapp/WEB-INF/spring/app-servlet.xml", lname));
        files.add(createModFile(peg+"/foundation-guiwar/src/main/resources/pegasus/project/internetbank/channel/ActionDataConfig.xml", lname));
        files.add(createModFile(peg+"/foundation-guiwar/src/main/resources/pegasus/project/internetbank/channel/MenuStructureListConfig.xml", lname));
//        SQL CHANGES
        files.add(createModFile(peg+"/netbanksrv-core/src/main/resources/script/sql/demo/customercontract/customercontract-function.sql", lname));
        files.add(createModFile(peg+"/netbanksrv-core/src/main/resources/script/sql/demo/customercontract/customercontract-protectedobject.sql", lname));
        files.add(createModFile(peg+"/netbanksrv-core/src/main/resources/script/sql/demo/functionmanagement/functionmanagement.sql", lname));
        files.add(createModFile(p+"/${servicename}-service/target/classes/script/versions/1.0.0/dml/resources/minipayment-resources-en.sql", lname));
        
        return files;
    }

    private ModFile createModFile(String path, String moduleName) {
        ModFile modFile = new ModFile(resolve(path, moduleName,serviceKey), true);
        System.out.println("Loading file : "+modFile.getPath());
        modFile.setFound(new File(modFile.getPath()).exists());
        return modFile;
    }

    /**uses velocity to evaluate to resolve a simple string template
     *
     * @param str the template string e.g 'welcome ${name}'
     * @param var the value of the key 'name'
     * @param keys the keys ${...}
     * @return the resolved string
     */
    public String resolve(String str, String var, String... keys) {
        VelocityContext context = new VelocityContext();
        for (String key : keys) {
            context.put(key, var);
        }
        Writer w = new StringWriter();
        Velocity.evaluate(context, w, "MODULE", str);
        return w.toString();
    }

    private String ev(Map m, String key) {
       return m.get(key) ==null? "":m.get(key).toString();
    }

}
