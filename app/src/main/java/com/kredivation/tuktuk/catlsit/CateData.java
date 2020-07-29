package com.kredivation.tuktuk.catlsit;

public class CateData {
    private String catName;
    private int id;

    public CateData() {
    }

    public CateData(String catName, int id) {
        this.catName = catName;
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        CateData itemCompare = (CateData) obj;
        if(itemCompare.getCatName().equals(this.getCatName()))
            return true;

        return false;
    }
}
