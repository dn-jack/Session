package com.dongnao.jack.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import redis.RedisApi;

public class HttpSessionImpl implements HttpSession {
    
    private String key;
    
    private ServletResponse resp;
    
    private HttpServletRequest request;
    
    private ThreadLocal<String> local = new ThreadLocal<String>();
    
    private ThreadLocal<Map<String, Object>> maplocal = new ThreadLocal<Map<String, Object>>();
    
    public HttpSessionImpl(HttpServletRequest request, ServletResponse resp) {
        this.resp = resp;
        this.request = request;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    @Override
    public Object getAttribute(String arg0) {
        Cookie[] cookies = request.getCookies();
        
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("sessionId")) {
                String key = cookie.getValue();
                List<String> list = RedisApi.hmget(key, arg0);
                System.out.println("sesssion信息为：" + list.get(0));
                return list.get(0);
            }
        }
        
        return null;
    }
    
    @Override
    public Enumeration<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public long getCreationTime() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return getKey();
    }
    
    @Override
    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Object getValue(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String[] getValueNames() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void invalidate() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void putValue(String arg0, Object arg1) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void removeAttribute(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void removeValue(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setAttribute(String arg0, Object arg1) {
        
        //        String key = getKeyFromCookie(request);
        //        
        //        if (key == null) {
        if (local.get() != null) {
            key = local.get();
        }
        else {
            key = Math.random() + "--" + "userInfo";
            local.set(key);
        }
        //        }
        
        if (maplocal.get() != null) {
            Map<String, Object> map = maplocal.get();
            //            List<Object> list = new ArrayList<Object>();
            //            list.add(arg1);
            map.put(arg0, arg1);
        }
        else {
            Map<String, Object> map = new HashMap<String, Object>();
            //            List<Object> list = new ArrayList<Object>();
            //            list.add(arg1);
            map.put(arg0, arg1);
            maplocal.set(map);
        }
        
        //        if (RedisApi.exist(key)) {
        //            Set<String> keys = RedisApi.hkeys(key);
        //            if (keys != null && keys.size() > 0) {
        //                for (String mapkey : keys) {
        //                    maplocal.get().put(mapkey, RedisApi.hmget(key, mapkey));
        //                }
        //            }
        //        }
        
        //        RedisApi.del(key);
        RedisApi.hmset(key, maplocal.get());
        
        Cookie cookie = new Cookie("sessionId", key);
        cookie.setPath("/");
        ((HttpServletResponse)resp).addCookie(cookie);
    }
    
    private String getKeyFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    @Override
    public void setMaxInactiveInterval(int arg0) {
        // TODO Auto-generated method stub
        
    }
    
    public void CreateKey(String name) {
        setKey(Math.random() + "--" + name);
    }
    
}
