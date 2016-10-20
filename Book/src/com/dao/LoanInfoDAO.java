package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.Book;
import com.domain.LoanInfo;
import com.domain.Reader;
import com.mysql.jdbc.Statement;
import com.utils.HibernateUtil;


public class LoanInfoDAO {

    /*每页显示记录数目*/
    private final int PAGE_SIZE = 3;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddLoanInfo(LoanInfo loanInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(loanInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询LoanInfo信息*/
    public ArrayList<LoanInfo> QueryLoanInfoInfo(Book book,Reader reader,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From LoanInfo loanInfo where 1=1";
            if(null != book && !book.getBarcode().equals("")) hql += " and loanInfo.book.barcode='" + book.getBarcode() + "'";
            if(null != reader && !reader.getReaderNo().equals("")) hql += " and loanInfo.reader.readerNo='" + reader.getReaderNo() + "'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List loanInfoList = q.list();
            return (ArrayList<LoanInfo>) loanInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的LoanInfo记录*/
    public ArrayList<LoanInfo> QueryAllLoanInfoInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From LoanInfo";
            Query q = s.createQuery(hql);
            List loanInfoList = q.list();
            return (ArrayList<LoanInfo>) loanInfoList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(Book book,Reader reader) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From LoanInfo loanInfo where 1=1";
            if(null != book && !book.getBarcode().equals("")) hql += " and loanInfo.book.barcode='" + book.getBarcode() + "'";
            if(null != reader && !reader.getReaderNo().equals("")) hql += " and loanInfo.reader.readerNo='" + reader.getReaderNo() + "'";
            Query q = s.createQuery(hql);
            List loanInfoList = q.list();
            recordNumber = loanInfoList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public LoanInfo GetLoanInfoByLoadId(int loadId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            LoanInfo loanInfo = (LoanInfo)s.get(LoanInfo.class, loadId);
            return loanInfo;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新LoanInfo信息*/
    public void UpdateLoanInfo(LoanInfo loanInfo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(loanInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除LoanInfo信息*/
    public void DeleteLoanInfo (int loadId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object loanInfo = s.get(LoanInfo.class, loadId);
            s.delete(loanInfo);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

}
