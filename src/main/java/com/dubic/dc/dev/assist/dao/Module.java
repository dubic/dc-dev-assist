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
public class Module {
    private String moduleName;
    private String modulePath;
    private String pegasus;
    private boolean tfw;

    public Module() {
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getPegasus() {
        return pegasus;
    }

    public void setPegasus(String pegasusPath) {
        this.pegasus = pegasusPath;
    }

    public boolean isTfw() {
        return tfw;
    }

    public void setTfw(boolean tfw) {
        this.tfw = tfw;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "Module{" + "moduleName=" + moduleName + ", modulePath=" + modulePath + ", pegasus=" + pegasus + ", tfw=" + tfw + '}';
    }
    
    
}
