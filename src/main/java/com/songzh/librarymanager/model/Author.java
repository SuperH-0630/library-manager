package com.songzh.librarymanager.model;

import java.util.Set;
import java.util.TreeMap;

import com.songzh.librarymanager.error.*;

public class Author {
    private final String name;

    private final TreeMap<String, Book> books;

    public Author(String name) {
        this.name = name;
        this.books = new TreeMap<>();
    }

    public String name() {
        return this.name;
    }

    public void addBook(Book book) {
        this.books.put(book.name(), book);
    }

    public Book getBook(String name) throws BookNotFound {
        var book = this.books.get(name);
        if (book == null) {
            throw new BookNotFound(name);
        }
        return book;
    }

    public Set<String> books() {
        return this.books.keySet();
    }
}
