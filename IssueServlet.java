package com.library.controller;
import com.library.dao.*; import com.library.model.*; import com.library.util.DBConnection; import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.*; import java.sql.*; import java.time.*;
@WebServlet("/issue") public class IssueServlet extends HttpServlet {
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{try{req.setAttribute("books",new BookDAO().findAll());req.setAttribute("members",new MemberDAO().findAll());req.getRequestDispatcher("/issue.jsp").forward(req,res);}catch(Exception e){throw new ServletException(e);}}
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
  int book=Integer.parseInt(req.getParameter("bookId")),member=Integer.parseInt(req.getParameter("memberId"));
  try(Connection c=DBConnection.getConnection()){c.setAutoCommit(false);
   try(PreparedStatement lock=c.prepareStatement("SELECT available_copies FROM books WHERE id=? FOR UPDATE")){lock.setInt(1,book);try(ResultSet r=lock.executeQuery()){if(!r.next()||r.getInt(1)<=0)throw new SQLException("This book is not available.");}}
   try(PreparedStatement active=c.prepareStatement("SELECT COUNT(*) FROM issued_books WHERE book_id=? AND member_id=? AND status='ISSUED'")){active.setInt(1,book);active.setInt(2,member);try(ResultSet r=active.executeQuery()){if(r.next()&&r.getInt(1)>0)throw new SQLException("This member already has this book issued.");}}
   try(PreparedStatement p=c.prepareStatement("INSERT INTO issued_books(book_id,member_id,issue_date,due_date) VALUES(?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 14 DAY))");PreparedStatement u=c.prepareStatement("UPDATE books SET available_copies=available_copies-1 WHERE id=?")){p.setInt(1,book);p.setInt(2,member);p.executeUpdate();u.setInt(1,book);u.executeUpdate();}
   c.commit();res.sendRedirect(req.getContextPath()+"/returns?ok=issued");
  }catch(Exception e){req.setAttribute("error",e.getMessage());doGet(req,res);}
 }
}
