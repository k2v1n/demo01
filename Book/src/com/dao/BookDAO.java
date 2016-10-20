package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.Book;
import com.domain.BookType;
import com.mysql.jdbc.Statement;
import com.utils.HibernateUtil;


public class BookDAO {

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
    public void AddBook(Book book) throws Exception {
        Session s = null;
        Transaction tx = null;
        try { 
            s = HibernateUtil.getSession();
            tx = s.beginTransaction(); 
            s.save(book);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
                tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*查询Book信息*/
    public ArrayList<Book> QueryBookInfo(String bookName,BookType bookType,String barcode,int currentPage) { 
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Book book where 1=1";
            if(!bookName.equals("")) hql = hql + " and book.bookName like '%" + bookName + "%'";
            if(null != bookType && bookType.getBookTypeId()!=0) hql += " and book.bookType.bookTypeId=" + bookType.getBookTypeId();
            if(!barcode.equals("")) hql = hql + " and book.barcode like '%" + barcode + "%'";
            Query q = s.createQuery(hql);
            /*计算当前显示页码的开始记录*/
            int startIndex = (currentPage-1) * this.PAGE_SIZE;
            q.setFirstResult(startIndex);
            q.setMaxResults(this.PAGE_SIZE);
            List bookList = q.list();
            return (ArrayList<Book>) bookList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*函数功能：查询所有的Book记录*/
    public ArrayList<Book> QueryAllBookInfo() {
        Session s = null; 
        try {
            s = HibernateUtil.getSession();
            String hql = "From Book";
            Query q = s.createQuery(hql);
            List bookList = q.list();
            return (ArrayList<Book>) bookList;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*计算总的页数和记录数*/
    public void CalculateTotalPageAndRecordNumber(String bookName,BookType bookType,String barcode) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            String hql = "From Book book where 1=1";
            if(!bookName.equals("")) hql = hql + " and book.bookName like '%" + bookName + "%'";
            if(null != bookType && bookType.getBookTypeId()!=0) hql += " and book.bookType.bookTypeId=" + bookType.getBookTypeId();
            if(!barcode.equals("")) hql = hql + " and book.barcode like '%" + barcode + "%'";
            Query q = s.createQuery(hql);
            List bookList = q.list();
            recordNumber = bookList.size();
            int mod = recordNumber % this.PAGE_SIZE;
            totalPage = recordNumber / this.PAGE_SIZE;
            if(mod != 0) totalPage++;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*根据主键获取对象*/
    public Book GetBookByBarcode(String barcode) {
        Session s = null;
        try {
            s = HibernateUtil.getSession();
            Book book = (Book)s.get(Book.class, barcode);
            return book;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*更新Book信息*/
    public void UpdateBook(Book book) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            s.update(book);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null)
            	  tx.rollback();
            throw e;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    /*删除Book信息*/
    public void DeleteBook (String barcode) throws Exception {
        Session s = null;
        Transaction tx = null;
        try {
            s = HibernateUtil.getSession();
            tx = s.beginTransaction();
            Object book = s.get(Book.class, barcode);
            s.delete(book);
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
