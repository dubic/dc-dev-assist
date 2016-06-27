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
    private final String camelServiceKey = "servicename";

    public List<ModFile> listAffectedFiles(Map m) {
        List<ModFile> files = new ArrayList<>();
        String p = ev(m,"modulePath");
        String sname = m.get("moduleName").toString();
        String lname = sname.toLowerCase();
        String peg = m.get("pegasus").toString().toLowerCase();
        //FILES START HERE
        files.add(createModFile(p,"/${servicename}-service/pom.xml", lname));
        files.add(createModFile(p,"/${servicename}-shared/pom.xml", lname));
        files.add(createModFile(peg,"/foundation-guiwar/src/main/webapp/WEB-INF/spring/app-servlet.xml", lname));
        files.add(createModFile(peg,"/foundation-guiwar/src/main/resources/pegasus/project/internetbank/channel/ActionDataConfig.xml", lname));
        files.add(createModFile(peg,"/foundation-guiwar/src/main/resources/pegasus/project/internetbank/channel/MenuStructureListConfig.xml", lname));
//        SQL CHANGES
        files.add(createModFile(peg,"/netbanksrv-core/src/main/resources/script/sql/demo/customercontract/customercontract-function.sql", lname));
        files.add(createModFile(peg,"/netbanksrv-core/src/main/resources/script/sql/demo/customercontract/customercontract-protectedobject.sql", lname));
        files.add(createModFile(peg,"/netbanksrv-core/src/main/resources/script/sql/demo/functionmanagement/functionmanagement.sql", lname));
//        files.add(createModFile(p,"/${servicename}-service/target/classes/script/versions/1.0.0/dml/resources/minipayment-resources-en.sql", lname));
//        JAVA/JAVASCRIPT CHANGES
        files.add(createModFile(peg,"/foundation-guiwar/src/main/java/pegasus/project/foundation/FoundationRemoteConfig.java", lname));
        files.add(createModFile(peg,"/foundation-guiwar/src/main/resources/js/foundation/index.js", lname));
        files.add(createModFile(peg,"/netbanksrv-core/src/main/java/pegasus/project/netbanksrv/NetBankSrvDSDBConfig.java", lname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/${servicename}Controller.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/dao/${servicename}DaoImpl.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/service/${servicename}ServiceImpl.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/facade/${servicename}FacadeImpl.java", sname));
        files.add(createModFile(p,"/${servicename}-client-customer-ng/src/main/resources/js/pegasus/module/${servicename}/module.js", lname));
//        ---------------------DELETE------------>
        files.add(createModFile(peg,"/netbanksrv-core/src/main/java/pegasus/project/netbanksrv/config/${servicename}ImportConfig.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename}/client/common/${servicename}ControllerConfig.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/service/config", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/client/common/${servicename}ControllerConfig.java", sname));
//        ---------------------CREATE------------>
        files.add(createModFile(peg,"/netbanksrv-core/src/main/java/pegasus/project/netbanksrv/config/${servicename}Config.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/Enable${servicename}Controllers.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ControllerImportSelector.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/internal/${servicename}ControllerConfiguration.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ControllerConfigurer.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-client-common/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ControllerConfigurerAdapter.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-shared/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/Enable${servicename}FunctionsRemote.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-shared/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}RemoteImportSelector.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-shared/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/internal/${servicename}RemoteConfiguration.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/Enable${servicename}.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ImportSelector.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/internal/${servicename}Configuration.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}Configurer.java", sname));
        files.add(createModFile(p,"/${servicename.toLowerCase()}-service/src/main/java/pegasus/module/${servicename.toLowerCase()}/config/${servicename}ConfigurerAdapter.java", sname));
        
        return files;
    }

    private ModFile createModFile(String path, String name, String moduleName) {
        ModFile modFile = new ModFile(resolve(path+name, moduleName,serviceKey), true);
        System.out.println("Loading file : "+modFile.getPath());
        modFile.setFound(new File(modFile.getPath()).exists());
        modFile.setName(resolve(name, moduleName,serviceKey));
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
