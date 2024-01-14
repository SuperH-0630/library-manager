package com.songzh.librarymanager.viewer;

import com.songzh.librarymanager.error.Back;
import com.songzh.librarymanager.error.Cancel;
import com.songzh.librarymanager.error.Exit;
import com.songzh.librarymanager.model.Library;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Viewer {
    private final Scanner scanner;

    public Viewer() {
        this.scanner = new Scanner(System.in);
    }

    public void showInfo(String data) {
        System.out.println(data);
    }

    public int getMenuInput() throws Exit {
        try {
            System.out.print("你的操作 >>> ");
            var opt = this.scanner.nextInt();

            if (this.scanner.hasNextLine()) {
                this.scanner.nextLine();
            }

            return opt;
        } catch (InputMismatchException e) {
            var n = this.scanner.next();
            if (n.equals("q")) {
                throw new Exit();
            }
            return -1;
        }
    }

    private String getInput() throws Cancel, Exit, Back {
        var res = this.scanner.nextLine();
        if (res.isEmpty()) {
            throw new Cancel();
        } else if (res.equals("n")) {
            throw new Back();
        } else if (res.equals("q")) {
            throw new Exit();
        }
        return res;
    }

    public String getBookName() throws Cancel, Exit, Back {
        System.out.print("输入书名：");
        return this.getInput();
    }

    public String getAuthorName() throws Cancel, Exit, Back {
        System.out.print("输入作者名称：");
        return this.getInput();
    }

    public String getBookType() throws Cancel, Exit, Back {
        System.out.print("输入书本类型名称 [SCI：学术书，SCORE：评分书]：");
        return this.getInput();
    }

    public String getSCINumber() throws Cancel, Exit, Back {
        System.out.print("输入书本SCI代码：");
        return this.getInput();
    }

    public int getScore() throws Cancel, Exit, NumberFormatException, Back {
        System.out.print("输入书本评分分数：");
        var scoreString = this.getInput();
        return Integer.parseInt(scoreString);
    }
}
