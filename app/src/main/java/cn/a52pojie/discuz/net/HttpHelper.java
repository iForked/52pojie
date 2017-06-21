package cn.a52pojie.discuz.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qtfreet00 on 2017/6/21.
 */

public class HttpHelper {
    private static HttpHelper mInstance;
    public ApiService newHttp;
    //本地ip为192.168.1.103
    private static final String BASE_URL = "http://www.52pojie.cn";

    private HttpHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        newHttp = retrofit.create(ApiService.class);
    }

    public static HttpHelper getInstance() {
        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null)
                    mInstance = new HttpHelper();
            }
        }
        return mInstance;
    }
}
