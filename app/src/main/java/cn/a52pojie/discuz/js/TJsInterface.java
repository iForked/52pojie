package cn.a52pojie.discuz.js;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebView;

import cn.a52pojie.discuz.ui.activity.ThreadDetailActivity;

/**
 * Created by qtfreet00 on 2017/6/22.
 */

public class TJsInterface {

        private ThreadDetailActivity.TjsInterfaceHandler mHandler;
        protected String mHtmlResult = "";
        private int mImageMode = 1;
        private WebView mWebView;

        public TJsInterface(WebView webView, ThreadDetailActivity.TjsInterfaceHandler threadDetailjsInterfaceHandler) {
            this.mWebView = webView;
            this.mHandler = threadDetailjsInterfaceHandler;
        }

//        @JavascriptInterface
//        public int isImageMode() {
//            return Config.getInstance().mAppInfo.getImageMode();
//        }

        @JavascriptInterface
        public void notifyForum(String str) {
            this.mHandler.obtainMessage(7, str).sendToTarget();
        }

        @JavascriptInterface
        public void openAudio(String str) {
            this.mHandler.obtainMessage(5, str).sendToTarget();
        }

        @JavascriptInterface
        public void openGif(String str) {
            this.mHandler.obtainMessage(3, str).sendToTarget();
        }

        @JavascriptInterface
        public void openImage(String str) {
            this.mHandler.obtainMessage(2, str).sendToTarget();
        }

        @JavascriptInterface
        public void openThread(String str) {
            this.mHandler.obtainMessage(8, str).sendToTarget();
        }

        @JavascriptInterface
        public void openVideo(String str) {
            this.mHandler.obtainMessage(4, str).sendToTarget();
        }

        @JavascriptInterface
        public void replayPost(String str) {
            this.mHandler.obtainMessage(0, str).sendToTarget();
        }

        public void setImageMode(int i) {
            this.mImageMode = i;
        }

        @JavascriptInterface
        public void toLogin() {
            this.mHandler.obtainMessage(6, null).sendToTarget();
        }

        @JavascriptInterface
        public void viewUser(String str) {
            Log.i("KONG", str);
            this.mHandler.obtainMessage(1, str).sendToTarget();
        }

}
