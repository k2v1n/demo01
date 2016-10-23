package com.action;

import java.util.List;

import com.dao.BookTypeDAO;
import com.domain.BookType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.TestUtil;

public class BookTypeAction extends ActionSupport {

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

    private int bookTypeId;
    public void setBookTypeId(int bookTypeId) {
        this.bookTypeId = bookTypeId;
    }
    public int getBookTypeId() {
        return bookTypeId;
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
    BookTypeDAO bookTypeDAO = new BookTypeDAO();

    /*待操作的BookType对象*/
    private BookType bookType;
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
    public BookType getBookType() {
        return this.bookType;
    }

    /*跳转到添加BookType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加BookType信息*/
    @SuppressWarnings("deprecation")
    public String AddBookType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            bookTypeDAO.AddBookType(bookType);
            ctx.put("message",  java.net.URLEncoder.encode("BookType添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType添加失败!"));
            return "error";
        }
    }

    /*查询BookType信息*/
    public String QueryBookType() {
        if(currentPage == 0) currentPage = 1;
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        bookTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = bookTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = bookTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookTypeList",  bookTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*查询要修改的BookType信息*/
    public String ModifyBookTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键bookTypeId获取BookType对象*/
        BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(bookTypeId);

        ctx.put("bookType",  bookType);
        return "modify_view";
    }

    /*更新修改BookType信息*/
    public String ModifyBookType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            bookTypeDAO.UpdateBookType(bookType);
            ctx.put("message",  java.net.URLEncoder.encode("BookType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType信息更新失败!"));
            return "error";
       }
   }

    /*删除BookType信息*/
    public String DeleteBookType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            bookTypeDAO.DeleteBookType(bookTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("BookType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType删除失败!"));
            return "error";
        }
    }

}
