package com.ng.NaijaTalks;

public class Item {
    String pubName;
    int pubImage;

    public Item(String pubName,int pubImage)
    {
        this.pubImage=pubImage;
        this.pubName=pubName;
    }
    public String getpubName()
    {
        return pubName;
    }
    public int getpubImage()
    {
        return pubImage;
    }
}
