package com.library.dao;
import com.library.model.Member; import com.library.util.DBConnection; import java.sql.*; import java.util.*;
public class MemberDAO {
 public List<Member> findAll() throws SQLException {List<Member> list=new ArrayList<>();try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,name,email,phone,department FROM members ORDER BY id DESC");ResultSet r=p.executeQuery()){while(r.next())list.add(new Member(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5)));}return list;}
 public void add(String name,String email,String phone,String department) throws SQLException {try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO members(name,email,phone,department) VALUES(?,?,?,?)")){p.setString(1,name);p.setString(2,email.isBlank()?null:email);p.setString(3,phone);p.setString(4,department);p.executeUpdate();}}
 public void delete(int id) throws SQLException {try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM members WHERE id=?")){p.setInt(1,id);p.executeUpdate();}}
}
