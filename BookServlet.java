package com.library.controller;
import com.library.dao.BookDAO; import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import java.io.*;
@WebServlet("/books") public class BookServlet extends HttpServlet {private final BookDAO dao=new BookDAO();
 protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{try{req.setAttribute("books",dao.findAll());req.getRequestDispatcher("/books.jsp").forward(req,res);}catch(Exception e){throw new ServletException(e);}}
 protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{try{String a=req.getParameter("action");if("add".equals(a))dao.add(req.getParameter("title"),req.getParameter("author"),req.getParameter("category"),req.getParameter("isbn"),Integer.parseInt(req.getParameter("copies")));else if("delete".equals(a))dao.delete(Integer.parseInt(req.getParameter("id")));res.sendRedirect(req.getContextPath()+"/books");}catch(Exception e){throw new ServletException(e);}}
}
