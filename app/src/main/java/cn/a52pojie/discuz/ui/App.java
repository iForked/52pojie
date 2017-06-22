package cn.a52pojie.discuz.ui;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by qtfreet00 on 2017/6/22.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        QbSdk.initX5Environment(this, null);
    }
}
