package com.dongnao.jack.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionServlet
 */
@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doPost(request, response);
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        saveSession(request, response);
        PrintWriter pw = response.getWriter();
        System.out.println(InetAddress.getLocalHost().getHostAddress() + ":"
                + InetAddress.getLocalHost().getHostName());
        System.out.println("保存session信息！");
        pw.println(InetAddress.getLocalHost().getHostAddress() + ":"
                + InetAddress.getLocalHost().getHostName());
    }
    
    private void saveSession(HttpServletRequest request,
            HttpServletResponse response) {
        //        HttpSession session = request.getSession();
        
        //        session.setAttribute("userName", request.getParameter("userName"));
        //        session.setAttribute("age", request.getParameter("age"));
        
        //        String userInfo = "姓名：" + request.getParameter("userName") + "--"
        //                + "年龄：" + request.getParameter("age");
        //        
        //        String key = Math.random() + "-userInfo";
        //        
        //        RedisApi.set(key, userInfo);
        //        
        //        Cookie cookie = new Cookie("sessionId", key);
        //        cookie.setPath("/");
        //        response.addCookie(cookie);
        HttpSession session = request.getSession();
        session.setAttribute("userName", request.getParameter("userName"));
        session.setAttribute("age", request.getParameter("age"));
    }
}
