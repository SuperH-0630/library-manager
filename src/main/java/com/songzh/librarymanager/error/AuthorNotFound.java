package com.songzh.librarymanager.error;

public class AuthorNotFound extends Exception {
    private final String authorName;
    public AuthorNotFound(String authorName) {
        super("作者未找到");
        this.authorName = authorName;
    }

    public String authorName() {
        return this.authorName;
    }
}