package dvdishka.bank.shop.Classes.meta.book;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopItemBook implements Serializable {

    private String author;
    private int pageCount;
    private String title;
    private String generation;
    private ArrayList<String> pages;

    public ShopItemBook(String author, int pageCount, String title, String generation, ArrayList<String> pages) {
        this.author = author;
        this.pageCount = pageCount;
        this.title = title;
        this.generation = generation;
        this.pages = pages;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGeneration() {
        return this.generation;
    }

    public ArrayList<String> getPages() {
        return this.pages;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setPages(ArrayList<String> pages) {
        this.pages = pages;
    }
}
