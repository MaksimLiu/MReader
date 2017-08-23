package com.maksimliu.mreader.bean;

import java.util.List;

/**
 * Created by MaksimLiu on 2017/8/21.
 */

public class BaseGankBean<T>{


    private boolean error;

    private List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
