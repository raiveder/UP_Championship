package com.example.championship;

public class Feelings implements Comparable<Feelings> {

    public int id;
    private String title;
    private String image;
    private Integer position;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int compareTo(Feelings feeling) {

        return position.compareTo(feeling.position);
    }
}