package com.domain;

public class LoanInfo {
    /*借阅编号*/
    private int loadId;
    public int getLoadId() {
        return loadId;
    }
    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }

    /*图书对象*/
    private Book book;
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    /*读者对象*/
    private Reader reader;
    public Reader getReader() {
        return reader;
    }
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    /*借阅时间*/
    private String borrowTIme;
    public String getBorrowTIme() {
        return borrowTIme;
    }
    public void setBorrowTIme(String borrowTIme) {
        this.borrowTIme = borrowTIme;
    }

    /*归还时间*/
    private String returnTIme;
    public String getReturnTIme() {
        return returnTIme;
    }
    public void setReturnTIme(String returnTIme) {
        this.returnTIme = returnTIme;
    }

}