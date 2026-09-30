package com.library.controller;
import com.library.util.DBConnection; import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.*; import java.sql.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
  String u=req.getParameter("username"),pw=req.getParameter("password");
  try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT role FROM users WHERE username=? AND password=?")){p.setString(1,u);p.setString(2,pw);try(ResultSet r=p.executeQuery()){if(r.next()){HttpSession s=req.getSession(true);s.setAttribute("user",u);s.setAttribute("role",r.getString(1));res.sendRedirect(req.getContextPath()+"/dashboard");}else res.sendRedirect(req.getContextPath()+"/index.html?error=1");}}
  catch(SQLException e){res.sendRedirect(req.getContextPath()+"/index.html?error=db");}
 }
}
