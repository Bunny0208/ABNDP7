package com.example.android.abndp7;

public class News {
    private String mTitle;
    private String mAuthor;
    private String mSectionName;
    private String mDate;
    private String mUrl;

    public News(String title, String sectionName, String author, String date, String url) {
        mTitle = title;
        mAuthor = author;
        mSectionName = sectionName;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getAuthor() {return mAuthor;}

    public String getSectionName() {
        return mSectionName;
    }


    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
