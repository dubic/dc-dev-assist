/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubic.dc.dev.assist.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author dubic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModFile {

    /**
     * totally replace the file
     */
    public static String REPLACE = "REPLACE";

    /**
     * create a new file
     */
    public static String CREATE = "CREATE";

    /**
     * deletes existing file
     */
    public static String DELETE = "DELETE";

    /**
     * Append to the end of the start and/or end line. if no start line then it
     * is appended before endline. if no end line then it is appended at the end
     * of the file.
     */
    public static String APPEND = "APPEND";

    /**
     * add to the first child
     */
    public static String PREPEND = "PREPEND"; //

    /**
     * remove or comment line
     */
    public static String REMOVE = "REMOVE";

    /**
     * this file must be upfdated manually.
     */
    public static String MANUAL = "MANUAL";
    private String name;
    private String path;
    private boolean found;
    private String template;
    private String action;
    private String startline;
    private String endline;
    private String checktext;//use this text to check if file has been edited esp in PREPEND
    private boolean process = true;

    public ModFile() {
    }

    public ModFile(String path, boolean found) {
        this.path = path;
        this.found = found;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStartline() {
        return startline;
    }

    public void setStartline(String startline) {
        this.startline = startline;
    }

    public String getEndline() {
        return endline;
    }

    public void setEndline(String endline) {
        this.endline = endline;
    }

    public String getChecktext() {
        return checktext;
    }

    public void setChecktext(String checktext) {
        this.checktext = checktext;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public String getSimpleName() {
        return this.name.substring(this.name.lastIndexOf("/")+1);
    }
    
    public String getExtension() {
        return this.name.substring(this.name.lastIndexOf(".")+1);
    }

}
