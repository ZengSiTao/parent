package com.linktop.cloud.entranceguardmodel.sync;

import java.util.List;

public class PushBodyDoor {

    public String getLock_cat() {
        return lock_cat;
    }

    public void setLock_cat(String lock_cat) {
        this.lock_cat = lock_cat;
    }

    public int getSrc_ts() {
        return src_ts;
    }

    public void setSrc_ts(int src_ts) {
        this.src_ts = src_ts;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    String lock_cat; // 	str 	开关锁类型
    int src_ts; // 	int 	发生时间，unix秒
    String media; // 	str 	开锁介质，定义见Css_dev_gatekeeper
    String uid; // 	str 	用户id
    String receipt;
}
