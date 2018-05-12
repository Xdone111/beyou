package byou.yadun.wallet.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SPUtils {
    private static final String FILE_NAME = "UserInfo";
    private static final String Image_NAME = "UserImage";
    private static String user_image = "";
    public static void save(Context context, String username, String mobile,String nickname,
                               int uid,String uuid,int code,String image,String token) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("usename",username);
        editor.putString("mobile",mobile);
        editor.putString("nickname",nickname);
        editor.putInt("uid",uid);
        editor.putString("uuid",uuid);
        editor.putInt("code",code);
        editor.putString("image",image);
        editor.putString("token",token);
        editor.commit();
    }
    public static String getuid(Context context, String uid) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(uid, "");
    }
    public static String getpsw(Context context, String psw) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(psw, "");
    }
    public static String getNickname(Context context, String nickname) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(nickname, "");
    } public static String getImage(Context context, String image) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(image, "");
    }
    public static String getToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(token, "");
    }
    public static void setUser_image(Context context,String image) {
        SharedPreferences sp = context.getSharedPreferences(Image_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userImage",image);
        editor.commit();
    }
    public static String getUser_image(Context context, String image) {
        SharedPreferences sp = context.getSharedPreferences(Image_NAME, Context.MODE_PRIVATE);
        return sp.getString(image, "");
    }
}
