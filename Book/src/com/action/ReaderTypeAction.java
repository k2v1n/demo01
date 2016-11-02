package com.action;

import java.util.List;

import com.dao.ReaderTypeDAO;
import com.domain.ReaderType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.TestUtil;

public class ReaderTypeAction extends ActionSupport {

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

    private int readerTypeId;
    public void setReaderTypeId(int readerTypeId) {
        this.readerTypeId = readerTypeId;
    }
    public int getReaderTypeId() {
        return readerTypeId;
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
    ReaderTypeDAO readerTypeDAO = new ReaderTypeDAO();

    /*待操作的ReaderType对象*/
    private ReaderType readerType;
    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }
    public ReaderType getReaderType() {
        return this.readerType;
    }

    /*跳转到添加ReaderType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加ReaderType信息*/
    @SuppressWarnings("deprecation")
    public String AddReaderType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            readerTypeDAO.AddReaderType(readerType);
            ctx.put("message",  java.net.URLEncoder.encode("ReaderType添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ReaderType添加失败!"));
            return "error";
        }
    }

    /*查询ReaderType信息*/
    public String QueryReaderType() {
        if(currentPage == 0) currentPage = 1;
        List<ReaderType> readerTypeList = readerTypeDAO.QueryReaderTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        readerTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = readerTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = readerTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("readerTypeList",  readerTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*查询要修改的ReaderType信息*/
    public String ModifyReaderTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键readerTypeId获取ReaderType对象*/
        ReaderType readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(readerTypeId);

        ctx.put("readerType",  readerType);
        return "modify_view";
    }

    /*更新修改ReaderType信息*/
    public String ModifyReaderType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            readerTypeDAO.UpdateReaderType(readerType);
            ctx.put("message",  java.net.URLEncoder.encode("ReaderType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ReaderType信息更新失败!"));
            return "error";
       }
   }

    /*删除ReaderType信息*/
    public String DeleteReaderType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            readerTypeDAO.DeleteReaderType(readerTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("ReaderType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ReaderType删除失败!"));
            return "error";
        }
    }

}
