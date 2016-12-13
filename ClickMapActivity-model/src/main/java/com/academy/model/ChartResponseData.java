package com.academy.model;

import java.util.List;

/**
 * Created by Daniel Palonek on 2016-12-12.
 */
public class ChartResponseData {

    private String key;
    private List<ChartData> values;

    public ChartResponseData(String key, List<ChartData> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ChartData> getValues() {
        return values;
    }

    public void setValues(List<ChartData> values) {
        this.values = values;
    }
}
