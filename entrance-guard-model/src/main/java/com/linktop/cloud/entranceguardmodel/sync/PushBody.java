package com.linktop.cloud.entranceguardmodel.sync;

public class PushBody {
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String get_pld() {
        return _pld;
    }

    public void set_pld(String _pld) {
        this._pld = _pld;
    }

    private String desc;
    private String id;
    private String cb;
    private String _pld;
}
