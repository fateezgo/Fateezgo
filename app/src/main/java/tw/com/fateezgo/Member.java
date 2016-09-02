package tw.com.fateezgo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/9/2.
 */
public class Member {
    private boolean isLogin = false;
    private int uid = 0;
    private String name ="not set";
    private String password = "not set";
    private String phone = "not set";
    private String email = "not set";
    private boolean isMaster = false;

    private static Member member = null;
    public static Member getInstance() {
        if (member == null) {
            member = new Member();
        }
        return member;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public int uid() {
        return uid;
    }

    public String name() {
        return name;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMemberData(Context context, boolean isLogin, int uid, String name, String password, String phone, String email, boolean isMaster) {
        SharedPreferences sp = context.getSharedPreferences("mem", Context.MODE_PRIVATE);

        this.isLogin = isLogin;
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.isMaster = isMaster;

        sp.edit().putBoolean("isLogin", isLogin)
                .putInt("uid", uid)
                .putString("name", name)
                .putString("passwd", password)
                .putString("phone", phone)
                .putString("email", email)
                .putBoolean("isMaster", isMaster)
                .commit();
    }

    public void getMemberData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("mem", Context.MODE_PRIVATE);

        isLogin = sp.getBoolean("isLogin", false);
        uid = sp.getInt("uid", 0);
        name = sp.getString("name", "");
        password = sp.getString("passwd", "");
        phone = sp.getString("phone", "");
        email = sp.getString("email", "");
        isMaster = sp.getBoolean("isMaster", false);
    }
}
