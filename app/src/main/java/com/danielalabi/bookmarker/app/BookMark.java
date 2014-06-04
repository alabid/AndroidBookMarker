package com.danielalabi.bookmarker.app;

/**
 * Created by danielalabi on 4/9/14.
 *
 * BookMarks are serialized as follows: name|URI
 */
public class BookMark {
    String name;
    String URI;

    public BookMark() {
        this("", "");
    }

    public BookMark(String name, String URI) {
        this.name = name;
        if (!URI.startsWith("http://") || URI.startsWith("https://")) {
            URI = "http://" + URI;
        }
        this.URI = URI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getName() {
        return name;
    }

    public String getURI() {
        return URI;
    }

    public static BookMark deserializeBookMark(String s) {
        String[] kv = s.split("\\|");
        return new BookMark(kv[0], kv[1]);
    }


    @Override
    public String toString() {
        return name + "|" + URI;
    }

    public String serializeBookMark() {
        return toString();
    }
}
