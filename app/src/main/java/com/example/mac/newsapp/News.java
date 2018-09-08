package com.example.mac.newsapp;

/**
 * Created by mac on 23/08/17.
 */

public class News {


    private String SectionName;
    private String Title;
    private String firstNameAuthor;
    private String secondNameAuthor;
    private String publishedDate;
    private String imageLink;
    private String webUrl;

    public News(String sectionName, String title, String firstNameAuthor,String secondNameAuthor, String publishedDate, String imageLink, String webUrl) {
        SectionName = sectionName;
        Title = title;
        this.firstNameAuthor = firstNameAuthor;
        this.secondNameAuthor=secondNameAuthor;
        this.publishedDate = publishedDate;
        this.imageLink = imageLink;
        this.webUrl = webUrl;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }



    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setFirstNameAuthor(String firstNameAuthor) {
        this.firstNameAuthor = firstNameAuthor;
    }

    public void setSecondNameAuthor(String secondNameAuthor) {
        this.secondNameAuthor = secondNameAuthor;
    }

    public String getFirstNameAuthor() {
        return firstNameAuthor;
    }

    public String getSecondNameAuthor() {
        return secondNameAuthor;
    }
}


