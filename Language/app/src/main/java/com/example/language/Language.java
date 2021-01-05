package com.example.language;

import com.google.gson.annotations.SerializedName;

public class Language {
    @SerializedName("Label")
    private String label;
    @SerializedName("Code")
    private String code;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
