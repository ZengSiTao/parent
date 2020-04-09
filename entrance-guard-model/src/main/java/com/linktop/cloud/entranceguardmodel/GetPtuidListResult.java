package com.linktop.cloud.entranceguardmodel;

import java.util.List;

public class GetPtuidListResult extends BusiCommonResult {

    public List<String> getListPtuid() {
        return listPtuid;
    }

    public void setListPtuid(List<String> listPtuid) {
        this.listPtuid = listPtuid;
    }

    List<String> listPtuid;
}
