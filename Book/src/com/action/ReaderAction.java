package com.action;

import java.util.List;

import com.dao.ReaderDAO;
import com.dao.ReaderTypeDAO;
import com.domain.Reader;
import com.domain.ReaderType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.TestUtil;

public class ReaderAction extends ActionSupport {

    /*界面层需要查询的属性: 读者编号*/
    private String readerNo;
    public void setReaderNo(String readerNo) {
        this.readerNo = readerNo;
    }
    public String getReaderNo() {
        return this.readerNo;
    }

    /*界面层需要查询的属性: 读者类型*/
    private ReaderType readerType;
    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }
    public ReaderType getReaderType() {
        return this.readerType;
    }

    /*界面层需要查询的属性: 姓名*/
    private String readerName;
    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }
    public String getReaderName() {
        return this.readerName;
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
    ReaderDAO readerDAO = new ReaderDAO();

    /*待操作的Reader对象*/
    private Reader reader;
    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public Reader getReader() {
        return this.reader;
    }

    /*跳转到添加Reader视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的ReaderType信息*/
        ReaderTypeDAO readerTypeDAO = new ReaderTypeDAO();
        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        return "add_view";
    }

    /*添加Reader信息*/
    @SuppressWarnings("deprecation")
    public String AddReader() {
        ActionContext ctx = ActionContext.getContext();
        /*验证读者编号是否已经存在*/
        String readerNo = reader.getReaderNo();
        Reader db_reader = readerDAO.GetReaderByReaderNo(readerNo);
        if(null != db_reader) {
            ctx.put("error",  java.net.URLEncoder.encode("该读者编号已经存在!"));
            return "error";
        }
        try {
            ReaderTypeDAO readerTypeDAO = new ReaderTypeDAO();
            readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(reader.getReaderType().getReaderTypeId());
            reader.setReaderType(readerType);
            readerDAO.AddReader(reader);
            ctx.put("message",  java.net.URLEncoder.encode("Reader添加成功!"));
            return "add_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reader添加失败!"));
            return "error";
        }
    }

    /*查询Reader信息*/
    public String QueryReader() {
        if(currentPage == 0) currentPage = 1;
        if(readerNo == null) readerNo = "";
        if(readerName == null) readerName = "";
        List<Reader> readerList = readerDAO.QueryReaderInfo(readerNo, readerType, readerName, currentPage);
        /*计算总的页数和总的记录数*/
        readerDAO.CalculateTotalPageAndRecordNumber(readerNo, readerType, readerName);
        /*获取到总的页码数目*/
        totalPage = readerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = readerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("readerList",  readerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("readerNo", readerNo);
        ctx.put("readerType", readerType);
        ReaderTypeDAO readerTypeDAO = new ReaderTypeDAO();
        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        ctx.put("readerName", readerName);
        return "query_view";
    }

    /*查询要修改的Reader信息*/
    public String ModifyReaderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键readerNo获取Reader对象*/
        Reader reader = readerDAO.GetReaderByReaderNo(readerNo);

        ReaderTypeDAO readerTypeDAO = new ReaderTypeDAO();
        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        ctx.put("reader",  reader);
        return "modify_view";
    }

    /*更新修改Reader信息*/
    public String ModifyReader() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ReaderTypeDAO readerTypeDAO = new ReaderTypeDAO();
            readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(reader.getReaderType().getReaderTypeId());
            reader.setReaderType(readerType);
            readerDAO.UpdateReader(reader);
            ctx.put("message",  java.net.URLEncoder.encode("Reader信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reader信息更新失败!"));
            return "error";
       }
   }

    /*删除Reader信息*/
    public String DeleteReader() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            readerDAO.DeleteReader(readerNo);
            ctx.put("message",  java.net.URLEncoder.encode("Reader删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reader删除失败!"));
            return "error";
        }
    }

}
