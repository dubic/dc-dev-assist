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
    private String path;
    private boolean found;

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
    
    
}
