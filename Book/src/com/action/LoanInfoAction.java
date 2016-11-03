package com.action;

import java.util.List;

import com.dao.BookDAO;
import com.dao.LoanInfoDAO;
import com.dao.ReaderDAO;
import com.domain.Book;
import com.domain.LoanInfo;
import com.domain.Reader;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.TestUtil;

public class LoanInfoAction extends ActionSupport {

    /*界面层需要查询的属性: 图书对象*/
    private Book book;
    public void setBook(Book book) {
        this.book = book;
    }
    public Book getBook() {
        return this.book;
    }

    /*界面层需要查询的属性: 读者对象*/
    private Reader reader;
    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public Reader getReader() {
        return this.reader;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int loadId;
    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }
    public int getLoadId() {
        return loadId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    LoanInfoDAO loanInfoDAO = new LoanInfoDAO();

    /*待操作的LoanInfo对象*/
    private LoanInfo loanInfo;
    public void setLoanInfo(LoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }
    public LoanInfo getLoanInfo() {
        return this.loanInfo;
    }

    /*跳转到添加LoanInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Book信息*/
        BookDAO bookDAO = new BookDAO();
        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        /*查询所有的Reader信息*/
        ReaderDAO readerDAO = new ReaderDAO();
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        return "add_view";
    }

    /*添加LoanInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddLoanInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.GetBookByBarcode(loanInfo.getBook().getBarcode());
            loanInfo.setBook(book);
            ReaderDAO readerDAO = new ReaderDAO();
            reader = readerDAO.GetReaderByReaderNo(loanInfo.getReader().getReaderNo());
            loanInfo.setReader(reader);
            loanInfoDAO.AddLoanInfo(loanInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LoanInfo添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LoanInfo添加失败!"));
            return "error";
        }
    }

    /*查询LoanInfo信息*/
    public String QueryLoanInfo() {
        if(currentPage == 0) currentPage = 1;
        List<LoanInfo> loanInfoList = loanInfoDAO.QueryLoanInfoInfo(book, reader, currentPage);
        /*计算总的页数和总的记录数*/
        loanInfoDAO.CalculateTotalPageAndRecordNumber(book, reader);
        /*获取到总的页码数目*/
        totalPage = loanInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = loanInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("loanInfoList",  loanInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("book", book);
        BookDAO bookDAO = new BookDAO();
        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        ctx.put("reader", reader);
        ReaderDAO readerDAO = new ReaderDAO();
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        return "query_view";
    }

    /*查询要修改的LoanInfo信息*/
    public String ModifyLoanInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键loadId获取LoanInfo对象*/
        LoanInfo loanInfo = loanInfoDAO.GetLoanInfoByLoadId(loadId);

        BookDAO bookDAO = new BookDAO();
        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        ReaderDAO readerDAO = new ReaderDAO();
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        ctx.put("loanInfo",  loanInfo);
        return "modify_view";
    }

    /*更新修改LoanInfo信息*/
    public String ModifyLoanInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.GetBookByBarcode(loanInfo.getBook().getBarcode());
            loanInfo.setBook(book);
            ReaderDAO readerDAO = new ReaderDAO();
            reader = readerDAO.GetReaderByReaderNo(loanInfo.getReader().getReaderNo());
            loanInfo.setReader(reader);
            loanInfoDAO.UpdateLoanInfo(loanInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LoanInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LoanInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除LoanInfo信息*/
    public String DeleteLoanInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            loanInfoDAO.DeleteLoanInfo(loadId);
            ctx.put("message",  java.net.URLEncoder.encode("LoanInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LoanInfo删除失败!"));
            return "error";
        }
    }

}
