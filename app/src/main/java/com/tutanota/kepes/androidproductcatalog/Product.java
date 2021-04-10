package com.tutanota.kepes.androidproductcatalog;

import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;

import androidx.core.text.HtmlCompat;

import org.xml.sax.XMLReader;

import io.realm.RealmObject;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH;


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

    public Spanned getUse_mode() {
        return processHtmlString(use_mode);
    }

    public void setUse_mode(String use_mode) {
        this.use_mode = use_mode;
    }

    public Spanned getApli() {
        return processHtmlString(apli);
    }

    public void setApli(String apli) {
        this.apli = apli;
    }

    public Spanned getParts() {
        return processHtmlString(parts);
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public Spanned getPrevents() {
        return processHtmlString(prevents);
    }

    public void setPrevents(String prevents) {
        this.prevents = prevents;
    }

    public Spanned getTool_type() {
        return processHtmlString(tool_type);
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

    public Spanned getFunction() {
        return processHtmlString(function);
    }
    public void setFunction(String function) {
        this.function = function;
    }

    public Spanned getDefinition() {
        return processHtmlString(definition);
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

    public Spanned processHtmlString(String htmlString){
        Spanned seq;
        // reduce multiple \n in the processed HTML string
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seq = Html.fromHtml(htmlString,  FROM_HTML_MODE_COMPACT);
        }else{
            seq = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT, null, new UlTagHandler());
        }
        return seq.length()>1? trim(seq,0,seq.length()):seq;
    }
    static class UlTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.equals("ul") && !opening) output.append("\n");
            if (tag.equals("ol") && !opening) output.append("\n");
            if (tag.equals("li") && opening) output.append("\n•");
        }
    }


    public Spanned trim(CharSequence s, int start, int end) {
        while (start < end && Character.isWhitespace(s.charAt(start))) {
            start++;
        }
        while (end > start && Character.isWhitespace(s.charAt(end - 1))) {
            end--;
        }
        return (Spanned) s.subSequence(start, end);
    }
}
