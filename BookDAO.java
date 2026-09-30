package com.library.dao;
import com.library.model.Book; import com.library.util.DBConnection; import java.sql.*; import java.util.*;
public class BookDAO {
 public List<Book> findAll() throws SQLException {List<Book> list=new ArrayList<>();String q="SELECT id,title,author,category,isbn,total_copies,available_copies FROM books ORDER BY id DESC";try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(q);ResultSet r=p.executeQuery()){while(r.next())list.add(new Book(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getInt(6),r.getInt(7)));}return list;}
 public void add(String title,String author,String category,String isbn,int copies) throws SQLException {String q="INSERT INTO books(title,author,category,isbn,total_copies,available_copies) VALUES(?,?,?,?,?,?)";try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(q)){p.setString(1,title);p.setString(2,author);p.setString(3,category);p.setString(4,isbn.isBlank()?null:isbn);p.setInt(5,copies);p.setInt(6,copies);p.executeUpdate();}}
 public void delete(int id) throws SQLException {try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM books WHERE id=?")){p.setInt(1,id);p.executeUpdate();}}
}
