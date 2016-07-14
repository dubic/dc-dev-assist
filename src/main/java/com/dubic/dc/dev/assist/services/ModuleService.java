/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.dc.dev.assist.services;

import com.dubic.dc.dev.assist.dao.ModFile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
    @Value("classpath:files.json")
    private Resource filesResource;
    @Value("classpath:tfwfiles.json")
    private Resource tfwfilesResource;

    public List<ModFile> listAffectedFiles(Map m) throws IOException {
//        List<ModFile> files = new ArrayList<>();
        String modulePath = ev(m, "modulePath");
        String moduleName = m.get("moduleName").toString();

        String pegasusPath = m.get("pegasus").toString().toLowerCase();
        //Load files from gson
        List<ModFile> files = loadFilesFromJson(filesResource);
        files = resolveFiles(files, moduleName, modulePath, pegasusPath);
        //TFW
        if ((Boolean) m.get("tfw") == true) {
            List<ModFile> tfwfiles = loadFilesFromJson(tfwfilesResource);
            tfwfiles = resolveFiles(tfwfiles, moduleName, modulePath, pegasusPath);
            files.addAll(tfwfiles);
        }

        return files;
    }

    private List<ModFile> loadFilesFromJson(Resource r) throws IOException {
        File f = r.getFile();
        Type listType = new TypeToken<ArrayList<ModFile>>() {
        }.getType();

        return new Gson().fromJson(FileUtils.readFileToString(f), listType);

    }

    private List<ModFile> resolveFiles(List<ModFile> files, String moduleName, String modPath, String pegPath) throws IOException {
        Map<String, Object> params = new HashMap();
        params.put(serviceKey, moduleName);
        for (ModFile file : files) {
            file.setName(resolve(file.getName(), params));
            if (null != file.getChecktext()) {
                file.setChecktext(resolve(file.getChecktext(), params));
            }

            if (null != file.getPath()) {
                switch (file.getPath()) {
                    case "module":
                        file.setPath(resolve(modPath + file.getName(), params));
                        break;
                    case "pegasus":
                        file.setPath(resolve(pegPath + file.getName(), params));
                        break;
                    default:
                        throw new IOException(file.getSimpleName() + ": Json File path not either pegasus or module");
                }
            }
            file.setFound(new File(file.getPath()).exists());
        }
        return files;
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
        } else if (ModFile.DELETE.equals(mfile.getAction())) {
            FileUtils.deleteQuietly(new File(mfile.getPath()));
            System.out.println("delete file : " + new File(mfile.getName()));
            return "deleted";
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
            if (FileUtils.readFileToString(f).contains(mfile.getChecktext())) {
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

        } else if (ModFile.PREPEND.equals(mfile.getAction())) {
            if (FileUtils.readFileToString(f).contains(mfile.getChecktext())) {
                System.out.println("EXISTS IN FILE!!! : " + f.getName());
                return "exists";
            }

            List<String> originalLines = FileUtils.readLines(f);
            originalLines.add(0, contents);
            FileUtils.writeLines(f, originalLines);
            System.out.println("Prepended to file : " + f.getName());
            return "prepended";
        } else if (ModFile.REMOVE.equals(mfile.getAction())) {
            List<String> lines = FileUtils.readLines(f);
            int i = 0;
            for (String line : lines) {
                if (line.contains(contents)) {
//                    exists?
                    if (line.startsWith("//")) {
                        return "exists";
                    }
                    lines.remove(i);
                    lines.add(i, "//".concat(line));
                    break;
                }
                i++;
            }
            FileUtils.writeLines(f, lines);
            System.out.println("Commented out from : " + f.getName());
            return "commented";
        } else if (ModFile.CREATE.equals(mfile.getAction())) {
            FileUtils.write(f, contents, "UTF-8");
            System.out.println("Created file : " + f.getName());
            return "created";
        } else {
            System.out.println("No Action");
            return "no action";
        }
    }

}
