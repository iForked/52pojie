package cn.a52pojie.discuz.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a52pojie.discuz.R;
import cn.a52pojie.discuz.constant.Constant;
import cn.a52pojie.discuz.js.ThreadDetailJsInterface;
import es.dmoral.toasty.Toasty;

/**
 * Created by qtfreet00 on 2017/6/22.
 */

public class ThreadDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ThreadDetailJsInterface mWebViewJsInterface;
    private ThreadDetailjsInterfaceHandler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_detail_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initViews();
        initListeners();
    }

    private void initListeners() {
        fab.setOnClickListener(this);
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface"})
    private void initViews() {
        try {
            Intent intent = getIntent();
            String tid = intent.getStringExtra("tid");
            final String url = String.format(Constant.FORUM_DETAIL_URL, tid);
            Log.e("qtfreet00", url);
            this.mHandler = new ThreadDetailjsInterfaceHandler(this);
            this.mWebViewJsInterface = new ThreadDetailJsInterface(webView, this.mHandler);
            WebSettings settings = webView.getSettings();
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setJavaScriptEnabled(true);
            webView.setBackgroundColor(0);

            settings.setJavaScriptCanOpenWindowsAutomatically(false);
            // webView.loadDataWithBaseURL(url, "<div style=\"height:100%; top:30%; position:relative;\" ><center><IMG src=\"file:///android_res/drawable/icon_loading_failed.png\" width=\"100\" /><br/><br/><div><font color=\"#666666\">网络连接不可用，请稍后重试</font></div></center></div>", "text/html", "utf-8", null);
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                                         @Override
                                         public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                                             webView.loadUrl(url);
                                             return true;
                                         }

                                         @Override
                                         public void onPageFinished(WebView webView, String s) {
                                             // Log.e("qtfreet00", webView.getContentDescription() + "");
                                             // imgReset();
                                             super.onPageFinished(webView, s);
                                         }
                                     }

            );
            webView.addJavascriptInterface(this.mWebViewJsInterface, "AppInterface");
        } catch (Exception e) {
            Toasty.error(this, "加载页面出错了哦", Toast.LENGTH_SHORT).show();
        }
    }

    private void imgReset() {
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}" + "})()");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Toasty.error(this, "和平真心智障", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static final class ThreadDetailjsInterfaceHandler extends Handler {
        public static final int NOTIFY_FORUM = 7;
        public static final int OPEN_AUDIO = 5;
        public static final int OPEN_GIF = 3;
        public static final int OPEN_IMAGE = 2;
        public static final int OPEN_THREAD = 8;
        public static final int OPEN_VIDEO = 4;
        public static final int REPLY_POST = 0;
        public static final int TO_LOGIN = 6;
        public static final int VIEW_USER = 1;
        private ThreadDetailActivity mActivity;

        public ThreadDetailjsInterfaceHandler(ThreadDetailActivity threadDetailActivity) {
            this.mActivity = threadDetailActivity;
        }

        public void handleMessage(Message message) {
            String str;
            switch (message.what) {
                case 0:
                    Toasty.info(this.mActivity, "和平傻逼呜呜大", Toast.LENGTH_SHORT).show();
                    // this.mActivity.mToolBar.showSoftInput((String) message.obj, true);
                    break;
                case 1:
//                    str = (String) message.obj;
//                    Intent intent = new Intent();
//                    intent.setClass(this.mActivity, ProfileActivity.class);
//                    intent.putExtra("uid", str);
//                    this.mActivity.startActivity(intent);
                    break;
                case 2:
//                    str = (String) message.obj;
//                    ArrayList arrayList = new ArrayList();
//                    arrayList.add(str);
//                    new ImageDialog(this.mActivity, arrayList, str).show();
                    break;
                case 3:
                    str = (String) message.obj;
                    break;
                case 4:
                    str = (String) message.obj;
                    break;
                case 5:
                    str = (String) message.obj;
                    break;
                case 6:
//                    if (DiscuzApp.getLoginUser().getUid() < 1) {
//                        LoginActivity.onlogin = new OnLogin() {
//                            public void loginError() {
//                            }
//
//                            public void loginSuceess() {
//                                ThreadDetailjsInterfaceHandler.this.mActivity.onLoadData();
//                            }
//                        };
//                        Intent intent2 = new Intent();
//                        intent2.setClass(this.mActivity, LoginActivity.class);
//                        this.mActivity.startActivity(intent2);
//                        break;
//                    }
                    break;
                case 7:
//                    str = (String) message.obj;
//                    if (str != null) {
//                        this.mActivity.mNavigationBar.setTitle(str);
//                        break;
//                    }
                    break;
            }
            super.handleMessage(message);
        }
    }

}
