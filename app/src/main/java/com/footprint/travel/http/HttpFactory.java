package com.footprint.travel.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @标题: HttpFactory .java
 * @概述: 网络通讯封装类
 * @作者: Allen
 * @日期: 2016/12/5 @版本：
 */
public class HttpFactory {
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    public HttpFactory() {
    }
    public static HttpFactory getInstance(){
        HttpFactory httpFactory=null;
        if (httpFactory==null){
            httpFactory=new HttpFactory();
        }
        return httpFactory;
    }
    public OkHttpClient initOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        String body=interceptor.getLevel().BODY.toString();
        // 添加公共参数拦截器
//        HttpCommonInterceptor interceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform","android")
//                .addHeaderParams("userToken","1234343434dfdfd3434")
//                .addHeaderParams("userId","123445")
//                .build();
//      证书锁定  okHttpClient=new OkHttpClient.Builder().certificatePinner(new CertificatePinner.Builder().add().build())
        okHttpClient=new OkHttpClient.Builder().addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
    public Retrofit  getRetrofit(String baseURl){
         retrofit=new Retrofit.Builder()
                .client(initOkHttpClient())
                .baseUrl(baseURl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public <T> T createService(Class<T> service,String baseURl){
        return getRetrofit(baseURl).create(service);
    }

}
