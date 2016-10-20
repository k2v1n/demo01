package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.Reader;
import com.domain.ReaderType;
import com.mysql.jdbc.Statement;
import com.utils.HibernateUtil;


public class ReaderDAO {

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
    public void AddReader(Reader reader) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(reader);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询Reader信息*/
    public ArrayList<Reader> QueryReaderInfo(String readerNo,ReaderType readerType,String readerName,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Reader reader where 1=1";
            if(!readerNo.equals("")) hql = hql + " and reader.readerNo like '%" + readerNo + "%'";
            if(null != readerType && readerType.getReaderTypeId()!=0) hql += " and reader.readerType.readerTypeId=" + readerType.getReaderTypeId();
            if(!readerName.equals("")) hql = hql + " and reader.readerName like '%" + readerName + "%'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List readerList = q.list();
            return (ArrayList<Reader>) readerList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的Reader记录*/
    public ArrayList<Reader> QueryAllReaderInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Reader";
            Query q = s.createQuery(hql);
            List readerList = q.list();
            return (ArrayList<Reader>) readerList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String readerNo,ReaderType readerType,String readerName) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Reader reader where 1=1";
            if(!readerNo.equals("")) hql = hql + " and reader.readerNo like '%" + readerNo + "%'";
            if(null != readerType && readerType.getReaderTypeId()!=0) hql += " and reader.readerType.readerTypeId=" + readerType.getReaderTypeId();
            if(!readerName.equals("")) hql = hql + " and reader.readerName like '%" + readerName + "%'";
            Query q = s.createQuery(hql);
            List readerList = q.list();
            recordNumber = readerList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public Reader GetReaderByReaderNo(String readerNo) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Reader reader = (Reader)s.get(Reader.class, readerNo);
            return reader;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新Reader信息*/
    public void UpdateReader(Reader reader) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(reader);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除Reader信息*/
    public void DeleteReader (String readerNo) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object reader = s.get(Reader.class, readerNo);
            s.delete(reader);
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
