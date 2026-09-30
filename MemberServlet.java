package com.library.controller;
import com.library.dao.MemberDAO; import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.*;
@WebServlet("/members") public class MemberServlet extends HttpServlet {private final MemberDAO dao=new MemberDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{try{req.setAttribute("members",dao.findAll());req.getRequestDispatcher("/members.jsp").forward(req,res);}catch(Exception e){throw new ServletException(e);}}
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{try{String a=req.getParameter("action");if("add".equals(a))dao.add(req.getParameter("name"),req.getParameter("email"),req.getParameter("phone"),req.getParameter("department"));else if("delete".equals(a))dao.delete(Integer.parseInt(req.getParameter("id")));res.sendRedirect(req.getContextPath()+"/members");}catch(Exception e){throw new ServletException(e);}}
}
