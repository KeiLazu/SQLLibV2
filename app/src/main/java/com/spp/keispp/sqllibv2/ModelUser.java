package com.spp.keispp.sqllibv2;

/**
 * Created by Asus on 3/27/2017.
 */

public class ModelUser {

    int _id;
    String _User;
    String _Pass;
    String _PIN;

    public ModelUser() {
        //NEEDED
    }


    public ModelUser(int id, String user, String pass, String pin) {
        this._id = id;
        this._User = user;
        this._Pass = pass;
        this._PIN = pin;
    }

    public  ModelUser(String user, String pass, String pin) {
        this._User = user;
        this._Pass = pass;
        this._PIN = pin;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_User() {
        return _User;
    }

    public void set_User(String _User) {
        this._User = _User;
    }

    public String get_Pass() {
        return _Pass;
    }

    public void set_Pass(String _Pass) {
        this._Pass = _Pass;
    }

    public String get_PIN() {
        return _PIN;
    }

    public void set_PIN(String _PIN) {
        this._PIN = _PIN;
    }

}
