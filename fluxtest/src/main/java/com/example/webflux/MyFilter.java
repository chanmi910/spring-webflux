package com.example.webflux;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter implements Filter {

    private EventNotify eventNotify;

    public MyFilter(EventNotify eventNotify) {
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터 실행됨");

        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //servletResponse.setContentType("text/plain;charset=UTF-8"); // flush를 해도 웹브라우저가 데이터를 쌓아두고 한번에 읽음.
        servletResponse.setContentType("text/event-stream;charset=UTF-8"); // stream을 열어두고 데이터를 계속 받음

        // WebFlux
        // 1. Reactive Streams 라이브러리를 쓰면 표준을 지켜서 응답을 할 수 있다.
        PrintWriter out = servletResponse.getWriter();
        for (int i = 0; i < 5; i++) {
            out.println("응답: " + i);
            out.flush(); // 버퍼를 비움
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //-- WebFlux

        // SSE Emitter 라이브러리를 사용하면 편하게 쓸 수 있다.
        while (true) {
            try {
                if(eventNotify.getChange()) {
                    out.println("응답: " + eventNotify.getEvents().get(eventNotify.getEvents().size() - 1));
                    out.flush();
                    eventNotify.setChange(false);
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 3. WebFlux -> Reactive Streams가 적용된 stream을 배우고 (비동기 단일 스레드 동작)
        // 4. Servlet MVC -> Reactive Streams가 적용된 stream을 배우고 (멀티 스레드 동작)

    }

}