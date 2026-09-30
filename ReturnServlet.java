package com.library.controller;
import com.library.util.DBConnection; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.*; import java.sql.*;
@WebServlet("/return") public class ReturnServlet extends HttpServlet {
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{int id=Integer.parseInt(req.getParameter("id"));
  try(Connection c=DBConnection.getConnection()){c.setAutoCommit(false);int bookId;
   try(PreparedStatement p=c.prepareStatement("SELECT book_id FROM issued_books WHERE id=? AND status='ISSUED' FOR UPDATE")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Active issue not found.");bookId=r.getInt(1);}}
   try(PreparedStatement p=c.prepareStatement("UPDATE issued_books SET return_date=CURDATE(), fine=GREATEST(DATEDIFF(CURDATE(),due_date),0)*5, status='RETURNED' WHERE id=?");PreparedStatement u=c.prepareStatement("UPDATE books SET available_copies=available_copies+1 WHERE id=?")){p.setInt(1,id);p.executeUpdate();u.setInt(1,bookId);u.executeUpdate();}
   c.commit();res.sendRedirect(req.getContextPath()+"/returns?ok=returned");
  }catch(Exception e){res.sendRedirect(req.getContextPath()+"/returns?error="+java.net.URLEncoder.encode(e.getMessage(),"UTF-8"));}
 }
}
