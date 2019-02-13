package com.amaltarek.newsapp.Model;

public class News {

    /** Art Resource for the News */
    private String mNewsArt;

    /** String resource for News Title */
    private String mNewsTitle;

    /** String resource for News URL */
    private String mNewsUrl;

    /** String of Author name of the Story*/
    private String mAuthorName;

    /** String of Publication Date for News */
    private String mPublicationDate;

    /** String of Section name of the Story*/
    private String mSectionName;

    public News(String mNewsArt, String mNewsTitle, String mNewsUrl, String mAuthorName, String mPublicationDate, String mSectionName) {
        this.mNewsArt = mNewsArt;
        this.mNewsTitle = mNewsTitle;
        this.mNewsUrl = mNewsUrl;
        this.mAuthorName = mAuthorName;
        this.mPublicationDate = mPublicationDate;
        this.mSectionName = mSectionName;
    }

    public String getNewsArt() {
        return mNewsArt;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getSectionName() {
        return mSectionName;
    }
}
