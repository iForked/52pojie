package cn.a52pojie.discuz.net;

import cn.a52pojie.discuz.bean.ForumTitleBean;
import cn.a52pojie.discuz.bean.IndexBean;

import cn.a52pojie.discuz.bean.LoginBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by qtfreet00 on 2017/6/21.
 */

public interface ApiService {
    @GET("/api/mobile/index.php?charset=utf-8&version=3&mobile=no&module=hotthread")
    Observable<IndexBean> getIndex();

    @GET("/api/mobile/index.php?charset=utf-8&version=3&mobile=no&module=hotthread")
    Observable<IndexBean> loadMore(@Query("page") int i);

    @POST("/api/mobile/index.php?loginfield=auto&charset=utf-8&version=3&loginsubmit=yes&mobile=no&module=login")
    Observable<LoginBean> login(@Query("charset") String charset, @Query("username") String username, @Query("password") String password);

    @POST("/api/mobile/index.php?loginfield=auto&charset=utf-8&version=3&loginsubmit=yes&mobile=no&module=login")
    Observable<LoginBean> loginWithQuestion(@Query("questionid") int id, @Query("charset") String charset, @Query("username") String username, @Query("password") String password, @Query("answer") String answer);

    @GET("/api/mobile/index.php?charset=utf-8&version=3&mobile=no&module=forumindex")
    Observable<ForumTitleBean> getForum();
}
