package com.company.antoine.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("docs")
    @Expose
    private List<Result> docs = null;

    public List<Result> getDocs() {
        return docs;
    }

    public void setDocs(List<Result> docs) {
        this.docs = docs;
    }
}