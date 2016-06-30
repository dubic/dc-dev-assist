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
    public static String REPLACE = "REPLACE"; //totally replace the file
    public static String CREATE = "CREATE"; // create a new file
    public static String DELETE = "DELETE"; //deletes existing file
    public static String APPEND = "APPEND";
    public static String PREPEND = "PREPEND"; //add to the last child of start and end
    public static String REMOVE = "REMOVE";//remove or comment line
    public static String MANUAL = "MANUAL";//this file must be upfdated manually.
    private String name;
    private String path;
    private boolean found;
    private String template;
    private String action;
    private String startline;
    private String endline;

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
    
    
}
