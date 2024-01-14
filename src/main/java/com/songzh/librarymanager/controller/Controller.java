package com.songzh.librarymanager.controller;

import com.songzh.librarymanager.error.*;
import com.songzh.librarymanager.model.*;
import com.songzh.librarymanager.viewer.Viewer;

public class Controller {
    private final Viewer viewer;

    private final Library libary;
    private Book book;
    private Author author;

    private Status status;

    static Controller createWithInit() {
        var ctrl = new Controller();
        ctrl.init();
        return ctrl;
    }

    public Controller() {
        this.libary = new Library();
        this.viewer = new Viewer();
        this.status = Status.Library;
    }

    private void init() {
        var author = new Author("佳程");
        var book1 = new SCIBook(author, "如何成为顶级前端？", "20040101-xxx222yyz");
        var book2 = new ScoreBook(author, "前端保姆教程", 98);
        author.addBook(book1);
        author.addBook(book2);

        this.libary.addAuthor(author);
    }

    private void mainloop() {
        for (;;) {
            try {
                this.viewer.showInfo(this.header());
                this.viewer.showInfo(this.menu());
                this.viewer.showInfo(this.footer());

                var opt = this.viewer.getMenuInput();

                switch (this.status) {
                    case Library -> {
                        LIBARYOPT:
                        switch (opt) {
                            case 1 -> {
                                try {
                                    this.book = this.libary.getBook(this.viewer.getBookName());
                                    this.status = Status.Book;
                                } catch (BookNotFound bookNotFound) {
                                    this.viewer.showInfo(String.format("书本未找到：%s", bookNotFound.bookName()));
                                }
                            }
                            case 2 -> {
                                try {
                                    this.author = this.libary.getAuthor(this.viewer.getAuthorName());
                                    this.status = Status.Author;
                                } catch (AuthorNotFound authorNotFound) {
                                    this.viewer.showInfo(String.format("作者未找到：%s", authorNotFound.authorName()));
                                }
                            }
                            case 3 -> {
                                Author author;
                                try {
                                    author = this.libary.getAuthor(this.viewer.getAuthorName());
                                } catch (AuthorNotFound authorNotFound) {
                                    this.viewer.showInfo(String.format("作者未找到：%s", authorNotFound.authorName()));
                                    break LIBARYOPT;
                                }

                                var bookName = this.viewer.getBookName();
                                var bookType = this.viewer.getBookType();
                                Book book;

                                switch (bookType) {
                                    case "SCI" -> {
                                        var sciNumber = this.viewer.getSCINumber();
                                        book = new SCIBook(author, bookName, sciNumber);
                                    }
                                    case "SCORE" -> {
                                        int score;
                                        try {
                                            score = this.viewer.getScore();
                                        } catch (NumberFormatException e) {
                                            this.viewer.showInfo(String.format("评分分数解析错误：%s", e.getMessage()));
                                            break LIBARYOPT;
                                        }

                                        book = new ScoreBook(author, bookName, score);
                                    }
                                    default -> {
                                        this.viewer.showInfo(String.format("书本类型不支持：%s", bookType));
                                        break LIBARYOPT;
                                    }
                                }

                                libary.addBook(book);
                            }
                            case 5 -> {
                                var author = this.libary.authors();
                                author.forEach((b)->{
                                    this.viewer.showInfo(String.format("* %s", b));
                                });
                            }
                            case 6 -> {
                                var books = this.libary.books();
                                books.forEach((b)->{
                                    this.viewer.showInfo(String.format("* %s", b));
                                });
                            }
                            default -> {
                                this.viewer.showInfo(String.format("不支持的操作：%d", opt));
                            }
                        }
                    }
                    case Author -> {
                        AUTHOROPT:
                        switch (opt) {
                            case 1 -> {
                                this.viewer.showInfo(this.author.name());
                                var books = this.author.books();
                                books.forEach((b)->{
                                    this.viewer.showInfo(String.format("* %s", b));
                                });
                            }
                            case 2 -> {
                                try {
                                    this.book = this.author.getBook(this.viewer.getBookName());
                                    this.status = Status.Book;
                                } catch (BookNotFound bookNotFound) {
                                    this.viewer.showInfo(String.format("书本未找到：%s", bookNotFound.bookName()));
                                }
                            }
                            case 3 -> {
                                var bookName = this.viewer.getBookName();
                                var bookType = this.viewer.getBookType();
                                Book book;

                                switch (bookType) {
                                    case "SCI" -> {
                                        var sciNumber = this.viewer.getSCINumber();
                                        book = new SCIBook(this.author, bookName, sciNumber);
                                    }
                                    case "SCORE" -> {
                                        int score;
                                        try {
                                            score = this.viewer.getScore();
                                        } catch (NumberFormatException e) {
                                            this.viewer.showInfo(String.format("评分分数解析错误：%s", e.getMessage()));
                                            break AUTHOROPT;
                                        }

                                        book = new ScoreBook(this.author, bookName, score);
                                    }
                                    default -> {
                                        this.viewer.showInfo(String.format("书本类型不支持：%s", bookType));
                                        break AUTHOROPT;
                                    }
                                }

                                this.author.addBook(book);
                            }
                            default -> {
                                this.viewer.showInfo(String.format("不支持的操作：%d", opt));
                            }
                        }
                    }
                    case Book -> {
                        BOOKOPT:
                        switch (opt) {
                            case 1 -> {
                                this.viewer.showInfo(this.book.identity());
                            }
                            case 2 -> {
                                this.author = this.book.author();
                                this.status = Status.Author;
                            }
                            default -> {
                                this.viewer.showInfo(String.format("不支持的操作：%d", opt));
                            }
                        }
                    }
                }


            } catch (Exit ignored) {
                return;
            } catch (Cancel ignored) {
                this.viewer.showInfo("已取消执行");
            } catch (Back ignored) {
                this.status = Status.Library;
            }
        }
    }

    private String menu() {
        return switch (this.status) {
            case Library -> """
                    1) 查找图书
                    2) 查找作者
                    3) 新增图书
                    4) 新增作者
                    5) 查看图书馆作者列表
                    6) 查看图书馆图书列表""";
            case Book -> """
                    1) 查看图书信息
                    2) 查找作者""";
            case Author -> """
                    1) 查看作者信息
                    2) 查找图书
                    3) 新增图书""";
        };
    }

    private String header() {
        return switch (this.status) {
            case Library -> "图书馆模式";
            case Book -> String.format("图书：%s", this.book.identity());
            case Author -> String.format("作者：%s", this.book.identity());
        };
    }

    private String footer() {
        return """
                输入 "q" 退出，输入 "n" 返回图书馆模式。""";
    }

    public static void main(String[] args) {
        System.out.println("""
                欢迎使用 顶级前端 Java!!! 的图书馆管理系统。
                本系统基于内存管理图书，不做任何持久化处理。
                谢谢！
                """);
        Controller.createWithInit().mainloop();
    }
}
