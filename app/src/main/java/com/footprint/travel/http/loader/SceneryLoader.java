package com.footprint.travel.http.loader;

import com.footprint.travel.entity.City;
import com.footprint.travel.entity.CitySubject;
import com.footprint.travel.entity.Scenery;
import com.footprint.travel.entity.ScenerySubject;
import com.footprint.travel.http.HttpFactory;
import com.footprint.travel.http.ObjectLoader;
import com.footprint.travel.utils.ConstUtils;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/20 @版本：
 */
public class SceneryLoader extends ObjectLoader{
    public SceneryService cityService;

    public SceneryLoader() {
        cityService= HttpFactory.getInstance().createService(SceneryService.class,ConstUtils.BASE_URL_TRAVEL);
    }
    /**
     * 获取城市列表
     * */
    public Observable<List<City>> getCitys(){
        Observable<List<City>> observable= observe(cityService.getCitys(ConstUtils.TRAVEL_KEY)).map(new Func1<CitySubject, List<City>>() {
           @Override
           public List<City> call(CitySubject citySubject) {
               return citySubject.getResult();
           }
       });
        return observable;
    }
    /**
     * 获取景点列表
     * */
    public Observable<List<Scenery>> getScenerys(int provinceId,int cityId,int page){
        Observable<List<Scenery>> observable= observe(cityService.getScenerys(ConstUtils.TRAVEL_KEY,provinceId,cityId,page)).map(new Func1<ScenerySubject, List<Scenery>>() {
            @Override
            public List<Scenery> call(ScenerySubject citySubject) {
                return citySubject.getResult();
            }
        });
        return observable;
    }
    public interface SceneryService{
        //获取城市列表
        @FormUrlEncoded
        @POST("cityList")
        Observable<CitySubject> getCitys(@Field("key") String key);

        //获取景点列表
        @FormUrlEncoded
        @POST("scenery")
        Observable<ScenerySubject> getScenerys(@Field("key") String key, @Field("pid") int pid, @Field("cid") int cid, @Field("page") int page);

        @GET("x3/weather")
        Observable<String> getWeatherService(@Query("city") String city, @Query("key") String key);
    }
}
