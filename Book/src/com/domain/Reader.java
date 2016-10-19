package com.domain;

public class Reader {
    /*读者编号*/
    private String readerNo;
    public String getReaderNo() {
        return readerNo;
    }
    public void setReaderNo(String readerNo) {
        this.readerNo = readerNo;
    }

    /*读者类型*/
    private ReaderType readerType;
    public ReaderType getReaderType() {
        return readerType;
    }
    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }

    /*姓名*/
    private String readerName;
    public String getReaderName() {
        return readerName;
    }
    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    /*读者年龄*/
    private String readerSex;
    public String getReaderSex() {
        return readerSex;
    }
    public void setReaderSex(String readerSex) {
        this.readerSex = readerSex;
    }

}