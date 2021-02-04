package com.tutanota.kepes.androidproductcatalog;

import io.realm.RealmObject;


public class Product extends RealmObject{
    private long _id;
    private String _name;
    private String _price;
    private String _seller;
    private String _sellerlocation;
    private String _description;
    private String _thumbnail;
//    private byte[] _image;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public String get_seller() {
        return _seller;
    }

    public void set_seller(String _seller) {
        this._seller = _seller;
    }

    public String get_sellerlocation() {
        return _sellerlocation;
    }

    public void set_sellerlocation(String _sellerlocation) {
        this._sellerlocation = _sellerlocation;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_thumbnail() {
        return _thumbnail;
    }

    public void set_thumbnail(String _thumbnail) {
        this._thumbnail = _thumbnail;
    }
}
