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
 * Servlet implementation class GetSessionServlet
 */
@WebServlet("/GetSessionServlet")
public class GetSessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSessionServlet() {
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
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        
        HttpSession session = request.getSession();
        
        System.out.println(InetAddress.getLocalHost().getHostAddress() + ":"
                + InetAddress.getLocalHost().getHostName());
        System.out.println("获取session信息！");
        
        //        if (session == null) {
        //            pw.println("session为空！");
        //        }
        //        else {
        //            pw.println("sessionId为:" + session.getId());
        //            pw.println("姓名：" + session.getAttribute("userName"));
        //            pw.println("年龄：" + session.getAttribute("age"));
        //            pw.println(InetAddress.getLocalHost().getHostAddress()
        //                    + InetAddress.getLocalHost().getHostName());
        //        }
        //        Cookie[] cookies = request.getCookies();
        //        
        //        if (cookies != null && cookies.length > 0) {
        //            for (Cookie cookie : cookies) {
        //                if (cookie.getName().equals("sessionId")) {
        //                    String key = cookie.getValue();
        //                    String sessionStr = RedisApi.get(key);
        //                    System.out.println("sesssion信息为：" + sessionStr);
        //                    pw.println("sesssion信息为：" + sessionStr);
        //                    pw.println(InetAddress.getLocalHost().getHostAddress()
        //                            + InetAddress.getLocalHost().getHostName());
        //                    return;
        //                }
        //            }
        //            System.out.println("从redis中获取不到session信息！");
        //            pw.print("从redis中获取不到session信息！");
        //        }
        //        else {
        //            pw.print("cookies为空！");
        //        }
        pw.println("sesssion信息为：" + session.getAttribute("userName") + ":"
                + session.getAttribute("age"));
        pw.println(InetAddress.getLocalHost().getHostAddress()
                + InetAddress.getLocalHost().getHostName());
    }
    
}
