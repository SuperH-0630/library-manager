package com.songzh.librarymanager.model;

public class ScoreBook extends Book {
    private final int score;

    public ScoreBook(Author author, String name, int score) {
        super(author, name);
        this.score = score;
    }

    @Override
    public String identity() {
        return String.format("%s <score %d>", super.identity(), this.score);
    }

    public int score() {
        return this.score;
    }
}
