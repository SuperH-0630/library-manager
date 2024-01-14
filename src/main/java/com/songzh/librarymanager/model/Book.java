package com.songzh.librarymanager.model;

public class Book {
    private final String name;
    private final Author author;

    Book(Author author, String name) {
        this.author = author;
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public Author author() {
        return this.author;
    }

    public String identity() {
        return this.name;
    }

    public String toString() {
        return String.format("<书本 %s (作者: %s)>", this.identity(), this.author().name());
    }
}
