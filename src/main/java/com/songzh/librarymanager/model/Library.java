package com.songzh.librarymanager.model;

import com.songzh.librarymanager.error.AuthorNotFound;
import com.songzh.librarymanager.error.BookNotFound;

import java.util.Set;
import java.util.TreeMap;

public class Library {
    private final TreeMap<String, Book> books;
    private final TreeMap<String, Author> authors;

    public Library() {
        this.books = new TreeMap<>();
        this.authors = new TreeMap<>();
    }

    public Book getBook(String name) throws BookNotFound {
        var book = this.books.get(name);
        if (book == null) {
            throw new BookNotFound(name);
        }
        return book;
    }

    public Author getAuthor(String name) throws AuthorNotFound {
        var author = this.authors.get(name);
        if (author == null) {
            throw new AuthorNotFound(name);
        }
        return author;
    }

    public void addBook(Book book) {
        this.books.put(book.name(), book);

        var author = book.author();
        this.authors.put(author.name(), author);
    }

    public void addAuthor(Author author) {
        this.authors.put(author.name(), author);

        var books = author.books();
        books.forEach((String name)->{
            try {
                var book = author.getBook(name);
                this.books.put(book.name(), book);
            } catch (BookNotFound ignored) {
                // 跳过
            }
        });
    }

    public Set<String> books() {
        return this.books.keySet();
    }

    public Set<String> authors() {
        return this.authors.keySet();
    }
}
