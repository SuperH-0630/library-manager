package com.songzh.librarymanager.error;

public class BookNotFound extends Exception {
    private final String bookName;
    public BookNotFound(String bookName) {
        super("书本未找到");
        this.bookName = bookName;
    }

    public String bookName() {
        return this.bookName;
    }
}