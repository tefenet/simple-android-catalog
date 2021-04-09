package com.tutanota.kepes.androidproductcatalog;

import io.realm.RealmObject;


public class Product extends RealmObject {
    private long _id;
    private String name;
    private String video_url;
    private String function;
    private String definition;
    private String image_name;
    private String use_mode;
    private String apli;
    private String parts;
    private String prevents;
    private String tool_type;
    private String others="";

    public String getUse_mode() {
        return use_mode;
    }

    public void setUse_mode(String use_mode) {
        this.use_mode = use_mode;
    }

    public String getApli() {
        return apli;
    }

    public void setApli(String apli) {
        this.apli = apli;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getPrevents() {
        return prevents;
    }

    public void setPrevents(String prevents) {
        this.prevents = prevents;
    }

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
}
