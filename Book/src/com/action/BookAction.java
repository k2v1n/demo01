package com.action;

import java.util.List;

import com.dao.BookDAO;
import com.dao.BookTypeDAO;
import com.domain.Book;
import com.domain.BookType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.TestUtil;

public class BookAction extends ActionSupport {

    /*界面层需要查询的属性: 图书名称*/
    private String bookName;
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookName() {
        return this.bookName;
    }

    /*界面层需要查询的属性: 图书所在类别*/
    private BookType bookType;
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
    public BookType getBookType() {
        return this.bookType;
    }

    /*界面层需要查询的属性: 图书条形码*/
    private String barcode;
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getBarcode() {
        return this.barcode;
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    BookDAO bookDAO = new BookDAO();

    /*待操作的Book对象*/
    private Book book;
    public void setBook(Book book) {
        this.book = book;
    }
    public Book getBook() {
        return this.book;
    }

    /*跳转到添加Book视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的BookType信息*/
        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        return "add_view";
    }

    /*添加Book信息*/
    @SuppressWarnings("deprecation")
    public String AddBook() {
        ActionContext ctx = ActionContext.getContext();
        /*验证图书条形码是否已经存在*/
        String barcode = book.getBarcode();
        Book db_book = bookDAO.GetBookByBarcode(barcode);
        if(null != db_book) {
            ctx.put("error",  java.net.URLEncoder.encode("该图书条形码已经存在!"));
            return "error";
        }
        try {
            BookTypeDAO bookTypeDAO = new BookTypeDAO();
            bookType = bookTypeDAO.GetBookTypeByBookTypeId(book.getBookType().getBookTypeId());
            book.setBookType(bookType);
            bookDAO.AddBook(book);
            ctx.put("message",  java.net.URLEncoder.encode("Book添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Book添加失败!"));
            return "error";
        }
    }

    /*查询Book信息*/
    public String QueryBook() {
        if(currentPage == 0) currentPage = 1;
        if(bookName == null) bookName = "";
        if(barcode == null) barcode = "";
        List<Book> bookList = bookDAO.QueryBookInfo(bookName, bookType, barcode, currentPage);
        /*计算总的页数和总的记录数*/
        bookDAO.CalculateTotalPageAndRecordNumber(bookName, bookType, barcode);
        /*获取到总的页码数目*/
        totalPage = bookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = bookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookList",  bookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("bookName", bookName);
        ctx.put("bookType", bookType);
        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        ctx.put("barcode", barcode);
        return "query_view";
    }

    /*查询要修改的Book信息*/
    public String ModifyBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键barcode获取Book对象*/
        Book book = bookDAO.GetBookByBarcode(barcode);

        BookTypeDAO bookTypeDAO = new BookTypeDAO();
        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        ctx.put("book",  book);
        return "modify_view";
    }

    /*更新修改Book信息*/
    public String ModifyBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BookTypeDAO bookTypeDAO = new BookTypeDAO();
            bookType = bookTypeDAO.GetBookTypeByBookTypeId(book.getBookType().getBookTypeId());
            book.setBookType(bookType);
            bookDAO.UpdateBook(book);
            ctx.put("message",  java.net.URLEncoder.encode("Book信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Book信息更新失败!"));
            return "error";
       }
   }

    /*删除Book信息*/
    public String DeleteBook() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            bookDAO.DeleteBook(barcode);
            ctx.put("message",  java.net.URLEncoder.encode("Book删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Book删除失败!"));
            return "error";
        }
    }

}
