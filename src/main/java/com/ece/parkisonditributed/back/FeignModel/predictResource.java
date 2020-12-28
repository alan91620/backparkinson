package com.ece.parkisonditributed.back.FeignModel;

public class predictResource {
    private String path;

    public predictResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
