package byou.yadun.wallet.Chat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CoinTypeBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.wallet.BaseActivity;
import byou.yadun.wallet.zxing.utils.LogoConfig;

/**
 * 我的加好友二维码
 */
public class MyCodeActivity extends BaseActivity {

    private ImageView mImgExit;
    private TextView mTxtTitle;
    private ImageView mImageQRCode;
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int CODE_WIDTH = 440;
    private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 5;
    private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 10;
    private Uri imageFileUri;
    private Bitmap mCodeBitmap;
    private static final int IMAGE = 1;
    private String mContent;
    //    String bitName = "code.png";
    private File mImageFile;
    private Bitmap logoBitmap;
    private List<CoinTypeBean> coinTypeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycode);
        initView();
        initEvent();
        initCoinType();
    }

    private void initView() {
        mContent = getIntent().getStringExtra("mycode");
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.my_erweima));
        mImageQRCode = (ImageView) findViewById(R.id.imgQRCode);
        String imageFilePath = Environment.getExternalStorageState() + "/" + this.getString(R.string.app_name);
        // 通过路径创建保存文件
        mImageFile = new File(imageFilePath, "/" + new Date().getTime() + ".png");
//        Bitmap bitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath());
//        if (bitmap == null) {
//            getQRCode();
//        } else {
//            mImageQRCode.setImageBitmap(bitmap);
//        }
    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DisplayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setPositiveButton(getResources().getString(R.string.my_code_save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveBitmap(mCodeBitmap, "code.png");
                //requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x0002);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.my_code_logo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void getQRCode() {
        LogoConfig logoConfig = new LogoConfig();
        Bitmap logoBitmap = logoConfig.modifyLogo(
                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_white_2), BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.yadun));
//        mCodeBitmap = createCode(mContent, logoBitmap);
////            saveBitmap(mCodeBitmap,"code.png");
//        mImageQRCode.setImageBitmap(mCodeBitmap);
    }

    public void createCode(String content, Bitmap logoBitmap)
    {
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();
        int logoHaleWidth = logoWidth >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        int logoHaleHeight = logoHeight >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        // 将logo图片按martix设置的信息缩放
        Matrix m = new Matrix();
        float sx = (float) 2 * logoHaleWidth / logoWidth;
        float sy = (float) 2 * logoHaleHeight / logoHeight;
        m.setScale(sx, sy);// 设置缩放信息
        Bitmap newLogoBitmap = Bitmap.createBitmap(logoBitmap, 0, 0, logoWidth,
                logoHeight, m, false);
        int newLogoWidth = newLogoBitmap.getWidth();
        int newLogoHeight = newLogoBitmap.getHeight();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别,H为最高
        hints.put(EncodeHintType.MAX_SIZE, LOGO_WIDTH_MAX);// 设置图片的最大值
        hints.put(EncodeHintType.MIN_SIZE, LOGO_WIDTH_MIN);// 设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 1);//设置白色边距值
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_WIDTH, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /*
				 * 取值范围,可以画图理解下
				 * halfW + newLogoWidth / 2 - (halfW - newLogoWidth / 2) = newLogoWidth
				 * halfH + newLogoHeight / 2 - (halfH - newLogoHeight) = newLogoHeight
				 */
                if (x > halfW - newLogoWidth / 2 && x < halfW + newLogoWidth / 2
                        && y > halfH - newLogoHeight / 2 && y < halfH + newLogoHeight / 2) {// 该位置用于存放图片信息
					/*
					 *  记录图片每个像素信息
					 *  halfW - newLogoWidth / 2 < x < halfW + newLogoWidth / 2
					 *  --> 0 < x - halfW + newLogoWidth / 2 < newLogoWidth
					 *   halfH - newLogoHeight / 2  < y < halfH + newLogoHeight / 2
					 *   -->0 < y - halfH + newLogoHeight / 2 < newLogoHeight
					 *   刚好取值newLogoBitmap。getPixel(0-newLogoWidth,0-newLogoHeight);
					 */
                    pixels[y * width + x] = newLogoBitmap.getPixel(
                            x - halfW + newLogoWidth / 2, y - halfH + newLogoHeight / 2);
                } else {
                    pixels[y * width + x] = matrix.get(x, y) ? BLACK : WHITE;// 设置信息
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        mCodeBitmap = bitmap;
        mImageQRCode.setImageBitmap(bitmap);
//        return bitmap;
    }

    private void saveBitmap(Bitmap bmp, String imgName) {
        String fileName = System.currentTimeMillis() + ".jpg";
        File f = new File("/sdcard/" + fileName + ".png");
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            // 最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));
            showToast(getResources().getString(R.string.my_code_save_success));
        } catch (Exception e) {
         Toast.makeText(getApplicationContext(),"在保存图片时出错："+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        LogoConfig logoConfig = new LogoConfig();
        Bitmap logoBitmap = logoConfig.modifyLogo(
                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_white_2), bm);
//        mCodeBitmap = createCode(mContent, logoBitmap);
//        mImageQRCode.setImageBitmap(mCodeBitmap);
//        saveBitmap(mCodeBitmap, "code.png");
    }
//    /**
//     * 权限成功回调函数
//     *
//     * @param requestCode
//     */
//    @Override
//    public void permissionSuccess(int requestCode) {
//        super.permissionSuccess(requestCode);
//        switch (requestCode) {
//            case 0x0002:
//                saveBitmap(mCodeBitmap,"code.png");
//                break;
//        }
//
//    }

    /**
     * 获取币种类型
     */
    private void initCoinType() {
        Map<String, String> params = new HashMap<>();
        params.put("token", PreferenceUtil.getString("token", ""));
        params.put("uid", PreferenceUtil.getString("uid", ""));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/coinList.html?", params,
                new HttpManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(final String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String data = json.optString("data");
                            Log.i("Log", "币种信息：" + response);
                            coinTypeList = new Gson()
                                    .fromJson(data, new TypeToken<List<CoinTypeBean>>() {
                                    }
                                            .getType());

                            String url = HttpManager.BASE_URL;
                            for (CoinTypeBean coinTypeBean : coinTypeList) {
                                if (coinTypeBean.getName().equals(PreferenceUtil.getString("coin_type", "ydc"))) {
                                    url += coinTypeBean.getImg();
                                    break;
                                }
                            }

//                            Glide.with(MyQRCodeActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                                    LogoConfig logoConfig = new LogoConfig();
//
//                                    if (logoBitmap==null) {
//
//                                        logoBitmap = logoConfig.modifyLogo(
//                                                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_white_2)
//                                                , resource == null ? BitmapFactory.decodeResource(getResources(),
//                                                        R.mipmap.yadun) : resource);
//                                        createCode(getIntent().getStringExtra("address"), logoBitmap);
//                                    }
//                                }
//                            });
                            Glide.with(MyCodeActivity.this).load(url).placeholder(com.hyphenate.easeui.R.drawable.ease_default_expression).into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    LogoConfig logoConfig = new LogoConfig();
                                        logoBitmap = logoConfig.modifyLogo(
                                                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_white_2)
                                                ,  BitmapFactory.decodeResource(getResources(),
                                                        R.mipmap.ic1));
                                        createCode(getIntent().getStringExtra("mycode"), logoBitmap);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

}
