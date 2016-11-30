package com.dongnao.jack.session;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class JackServletRequest extends HttpServletRequestWrapper {
    
    private ServletResponse resp;
    
    private HttpServletRequest request;
    
    public JackServletRequest(HttpServletRequest request, ServletResponse resp) {
        super(request);
        this.resp = resp;
        this.request = request;
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public HttpSession getSession() {
        
        HttpSession session = new HttpSessionImpl(request, resp);
        
        // TODO Auto-generated method stub
        return session;
    }
    
}
