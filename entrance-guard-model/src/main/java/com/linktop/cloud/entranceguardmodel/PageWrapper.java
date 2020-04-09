package com.linktop.cloud.entranceguardmodel;

import java.util.List;

public class PageWrapper<T> {
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    List<T> data;
    int total;
}
