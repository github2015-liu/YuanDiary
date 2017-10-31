package com.home.we.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity {

    private String qa_url = "http://114.215.238.246/api?padapi=questask-asklist.php";

    private SubscriberOnNextListener getQAOnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*getQAOnNext = new SubscriberOnNextListener<List<QA>>() {

            @Override
            public void onNext(List<QA> qas) {

            }
        };*/

        /**
         * 观察者
         */
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d("ljk", "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d("ljk", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ljk", "Error!");
            }
        };


        /**
         * 被观察者
         */
       /* Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });*/

        //事件队列
        //Observable observable = Observable.just("hello1","hello2","hello3");

        String[] words = {"lhello1", "lhello2", "lhello3"};
        Observable observable = Observable.from(words);

        //事件被订阅时，observable 的 call 方法被调用
        observable.subscribe(subscriber);


    }
}
