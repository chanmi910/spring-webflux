package com.example.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySub implements Subscriber<Integer> {

    private Subscription s;
    private int bufferSize;

    public MySub() {
        this.initBufferSize();
    }

    public void initBufferSize() {
        this.bufferSize = 3;
    };

    @Override
    public void onSubscribe(Subscription s) {
        System.out.println("구독자: 구독 정보 잘 받았어");
        this.s = s;
        System.out.println("구독자: 나 이제 신문 1개씩 줘");
        s.request(bufferSize);
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("onNext(): " + integer);
        bufferSize--;
        if(bufferSize == 0) {
            System.out.println("하루 지남");
            this.initBufferSize();
            s.request(bufferSize); // 한번에 받을 갯수
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("구독 중 에러");
    }

    @Override
    public void onComplete() {
        System.out.println("구독 완료");
    }
}
