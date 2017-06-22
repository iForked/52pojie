package cn.a52pojie.discuz.util;

import com.orhanobut.hawk.Hawk;

/**
 * Created by qtfreet00 on 2017/6/22.
 */

public class LoginUtil {

    public static void save(String saltKey, String cookie, String member_uid,String cookiePre) {
        Hawk.put("saltkey", saltKey);
        Hawk.put("auth", cookie);
        Hawk.put("cookiepre",cookiePre);
        Hawk.put("member_uid", member_uid);
    }

    public static void delete() {
        Hawk.delete("saltkey");
        Hawk.delete("auth");
        Hawk.delete("member_uid");
        Hawk.delete("cookiepre");
    }

    public static boolean checkLogin() {
        String cookie = Hawk.get("auth");
        if (cookie == null || cookie.isEmpty()) {
            return false;
        }
        return true;
    }
}
