package com.songzh.librarymanager.model;

public class SCIBook extends Book {
    private final String sciNumber;

    public SCIBook(Author author, String name, String sciNumber) {
        super(author, name);
        this.sciNumber = sciNumber;
    }

    @Override
    public String identity() {
        return String.format("%s:%s", super.identity(), this.sciNumber);
    }

    public String sciNumber() {
        return this.sciNumber;
    }
}
