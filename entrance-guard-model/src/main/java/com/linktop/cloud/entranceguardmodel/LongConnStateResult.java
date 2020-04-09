package com.linktop.cloud.entranceguardmodel;

public class LongConnStateResult extends BusiCommonResult {
    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    private boolean connected;
}
