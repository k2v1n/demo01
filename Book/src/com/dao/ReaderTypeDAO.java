package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.ReaderType;
import com.mysql.jdbc.Statement;
import com.utils.HibernateUtil;


public class ReaderTypeDAO {

    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

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
    public void AddReaderType(ReaderType readerType) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(readerType);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询ReaderType信息*/
    public ArrayList<ReaderType> QueryReaderTypeInfo(int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ReaderType readerType where 1=1";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List readerTypeList = q.list();
            return (ArrayList<ReaderType>) readerTypeList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的ReaderType记录*/
    public ArrayList<ReaderType> QueryAllReaderTypeInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From ReaderType";
            Query q = s.createQuery(hql);
            List readerTypeList = q.list();
            return (ArrayList<ReaderType>) readerTypeList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber() {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From ReaderType readerType where 1=1";
            Query q = s.createQuery(hql);
            List readerTypeList = q.list();
            recordNumber = readerTypeList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public ReaderType GetReaderTypeByReaderTypeId(int readerTypeId) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            ReaderType readerType = (ReaderType)s.get(ReaderType.class, readerTypeId);
            return readerType;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新ReaderType信息*/
    public void UpdateReaderType(ReaderType readerType) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(readerType);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除ReaderType信息*/
    public void DeleteReaderType (int readerTypeId) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object readerType = s.get(ReaderType.class, readerTypeId);
            s.delete(readerType);
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
