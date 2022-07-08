package com.example.webflux;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter2 implements Filter {

    private  EventNotify eventNotify;

    public MyFilter2(EventNotify eventNotify) {
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터2 실행됨");

        eventNotify.add("새로운 데이터");

    }

}