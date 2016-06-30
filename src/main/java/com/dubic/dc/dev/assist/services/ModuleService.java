/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.dc.dev.assist.services;

import com.dubic.dc.dev.assist.dao.ModFile;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author dubic
 */
@Service
public class ModuleService {

    private final String serviceKey = "servicename";
    @Autowired
    private VelocityEngine engine;

    public List<ModFile> listAffectedFiles(Map m) {
        List<ModFile> files = new ArrayList<>();
        String p = ev(m, "modulePath");
        String sname = m.get("moduleName").toString();
        String lname = sname.toLowerCase();
        String peg = m.get("pegasus").toString().toLowerCase();
        //FILES START HERE
        files.add(createModFile(p, "/${servicename}-service/pom.xml", lname, "service-pom.vm", ModFile.REPLACE, null, null));
        files.add(createModFile(p, "/${servicename}-shared/pom.xml", lname, "shared-pom.vm", ModFile.REPLACE, null, null));
        files.add(createModFile(peg, "/foundation-guiwar/src/main/webapp/WEB-INF/spring/app-servlet.xml", lname, "app-servlet.vm", ModFile.APPEND, "<context:component-scan base-package=\"pegasus\" use-default-filters=\"false\">", "</context:component-scan>"));
        files.add(createModFile(peg, "/foundation-guiwar/src/main/resources/pegasus/project/internetbank/channel/ActionDataConfig.xml", lname, "action-data-config.vm", ModFile.APPEND, null, "</ActionData>"));
        files.add(createModFile(peg, "/foundation-guiwar/src/main/resources/pegasus/project/internetbank/channel/MenuStructureListConfig.xml", lname, "manual.vm", ModFile.MANUAL, null, null));
////        SQL CHANGES
        files.add(createModFile(peg, "/netbanksrv-core/src/main/resources/script/sql/demo/customercontract/customercontract-function.sql", lname, "customercontract-function.vm", ModFile.APPEND, null, null));
//        files.add(createModFile(peg, "/netbanksrv-core/src/main/resources/script/sql/demo/customercontract/customercontract-protectedobject.sql", lname, null));
//        files.add(createModFile(peg, "/netbanksrv-core/src/main/resources/script/sql/demo/functionmanagement/functionmanagement.sql", lname, null));
////        files.add(createModFile(p,"/${servicename}-service/target/classes/script/versions/1.0.0/dml/resources/minipayment-resources-en.sql", lname,null));
////        JAVA/JAVASCRIPT CHANGES
//        files.add(createModFile(peg, "/foundation-guiwar/src/main/java/pegasus/project/foundation/FoundationRemoteConfig.java", lname, null));
//        files.add(createModFile(peg, "/foundation-guiwar/src/main/resources/js/foundation/index.js", lname, null));
//        files.add(createModFile(peg, "/netbanksrv-core/src/main/java/pegasus/project/netbanksrv/NetBankSrvDSDBConfig.java", lname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/controller/${servicename}Controller.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/dao/${servicename}DaoImpl.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/service/${servicename}ServiceImpl.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/facade/${servicename}FacadeImpl.java", sname, null));
//        files.add(createModFile(p, "/${servicename}-client-customer-ng/src/main/resources/js/pegasus/module/${servicename}/module.js", lname, null));
////        ---------------------DELETE------------>
//        files.add(createModFile(peg, "/netbanksrv-core/src/main/java/pegasus/project/netbanksrv/config/${servicename}ImportConfig.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename}/client/common/${servicename}ControllerConfig.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/service/config", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/client/common/${servicename}ControllerConfig.java", sname, null));
////        ---------------------CREATE------------>
//        files.add(createModFile(peg, "/netbanksrv-core/src/main/java/pegasus/project/netbanksrv/config/${servicename}Config.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/Enable${servicename}Controllers.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ControllerImportSelector.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/internal/${servicename}ControllerConfiguration.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ControllerConfigurer.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ControllerConfigurerAdapter.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-shared/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/Enable${servicename}FunctionsRemote.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-shared/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}RemoteImportSelector.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-shared/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/internal/${servicename}RemoteConfiguration.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/Enable${servicename}.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ImportSelector.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/internal/${servicename}Configuration.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}Configurer.java", sname, null));
//        files.add(createModFile(p, "/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ConfigurerAdapter.java", sname, null));

        return files;
    }

    private ModFile createModFile(String path, String name, String moduleName, String template, String action, String start, String end) {
        Map<String, Object> params = new HashMap();
        params.put(serviceKey, moduleName);
        ModFile modFile = new ModFile(resolve(path + name, params), true);
//        System.out.println("Loading file : "+modFile.getPath());
        modFile.setFound(new File(modFile.getPath()).exists());
        modFile.setName(resolve(name, params));
        modFile.setTemplate(template);
        modFile.setAction(action);
        modFile.setStartline(start);
        modFile.setEndline(end);
        return modFile;
    }

    /**
     * uses velocity to evaluate to resolve a simple string template
     *
     * @param str the template string e.g 'welcome ${name}'
     * @param params
     * @return the resolved string
     */
    public String resolve(String str, Map<String, Object> params) {
        VelocityContext context = new VelocityContext();
        for (String key : params.keySet()) {
            context.put(key, params.get(key));
        }
        Writer w = new StringWriter();
        Velocity.evaluate(context, w, "MODULE", str);
        return w.toString();
    }

    private String ev(Map m, String key) {
        return m.get(key) == null ? "" : m.get(key).toString();
    }

    private String resolveTemplate(Map<String, Object> params, String template) {
        return VelocityEngineUtils.mergeTemplateIntoString(this.engine, template, "UTF-8", params);
    }

    public String getFile(String path) throws IOException {
        return FileUtils.readFileToString(new File(path));
    }

    public String processFile(String moduleName, boolean tfw, ModFile mfile) throws IOException {
        if (ModFile.MANUAL.equals(mfile.getAction())) {
            return "Edit manually";
        }
        
        File f = new File(mfile.getPath());
        Map<String, Object> p = new HashMap<>();
        p.put(serviceKey, moduleName);
        p.put("tfw", tfw);
        String contents = resolveTemplate(p, mfile.getTemplate());

        if (ModFile.REPLACE.equals(mfile.getAction())) {
            FileUtils.write(f, contents);
            System.out.println("Replaced file : " + f.getName());
            return "replaced";
        } else if (ModFile.APPEND.equals(mfile.getAction())) {
            //check if line exists
            if (FileUtils.readFileToString(f).contains(contents)) {
                System.out.println("EXISTS IN FILE!!! : " + f.getName());
                return "exists";
            }

            List<String> lines = FileUtils.readLines(f);
            boolean started = false;

            int i = 0;
            for (String line : lines) {
                //
                if (mfile.getStartline() != null) {//start line is set
//                    System.out.println("mfile.getStartline() != null");
                    if (line.contains(mfile.getStartline())) {//start line is hit
//                        System.out.println("line.equals(mfile.getStartline()");
                        started = true;
                    }
                    if (started && line.contains(mfile.getEndline())) {//end line hit
                        System.out.println("started && line.equals(mfile.getEndline())");
                        lines.add(i, contents);
                        break;
                    }
                } else {//No start line so use end line
                    if (mfile.getEndline() == null) {
                        lines.add(contents);
                        break;
                    } else if (line.contains(mfile.getEndline())) {//end line hit
                        lines.add(i, contents);
                        break;
                    }
                }
                i++;
            }

//            for (String line : lines) {
//                System.out.println(line);
//            }
            FileUtils.writeLines(f, lines);
            System.out.println("Appended to file : " + f.getName());
            return "appended";

        } else {
            System.out.println("No Action");
            return "no action";
        }
    }

}
