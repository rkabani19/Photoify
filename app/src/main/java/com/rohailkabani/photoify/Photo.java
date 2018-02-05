package com.rohailkabani.photoify;

/**
 * Created by rohailkabani on 2018-02-04.
 */

class Photo {
    private String title;
    private String author;
    private String authorID;
    private String link;
    private String tags;
    private String image;

    public Photo(String title, String author, String authorID, String link, String tags, String image) {
        this.title = title;
        this.author = author;
        this.authorID = authorID;
        this.link = link;
        this.tags = tags;
        this.image = image;
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    String getAuthorID() {
        return authorID;
    }

    String getLink() {
        return link;
    }

    String getTags() {
        return tags;
    }

    String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorID='" + authorID + '\'' +
                ", link='" + link + '\'' +
                ", tags='" + tags + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
