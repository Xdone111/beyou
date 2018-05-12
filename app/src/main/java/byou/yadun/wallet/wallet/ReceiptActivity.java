package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CoinTypeBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.zxing.utils.LogoConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 接受
 */
public class ReceiptActivity extends BaseActivity {
    private TextView mTxtTitle;
    private ImageView mImgExit;
    private ImageView mImgCatalog;
    private ImageView mImgQRCode;
    private EditText mEdtCount;
    private EditText mEdtLable;
    private EditText mEdtRemark;
    private Button mBtnCreateCode;
    private Spinner mSpnCurrency;
    private ArrayAdapter<String> mAdapter;
    private List<String> mList;

    private List<CoinTypeBean> coinTypeList = new ArrayList<>();
    /**
     * 黑点颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 白色
     */
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 正方形二维码宽度
     */
    private static final int CODE_WIDTH = 440;
    /**
     * LOGO宽度值,最大不能大于二维码20%宽度值,大于可能会导致二维码信息失效
     */
    private static final int LOGO_WIDTH_MAX = CODE_WIDTH / 5;
    /**
     * LOGO宽度值,最小不能小于二维码10%宽度值,小于影响Logo与二维码的整体搭配
     */
    private static final int LOGO_WIDTH_MIN = CODE_WIDTH / 10;
    /**
     * 生成的二维码图片存储的URI
     */
    private Uri imageFileUri;
    private String mContent;
    private String mNumber;
    private File mImageFile;
    String bitName = "code.png";
    private String mNewContent;
    private Bitmap logoBitmap;
    private int firstIn = 0;   //是否是第一次进入   设置初始二维码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        initView();
        initData();
        initEvent();
        initCoinType();
    }

    private void initView() {
        Intent intent = getIntent();
        mContent = intent.getStringExtra("address");
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.receipt_name));
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mImgCatalog = (ImageView) findViewById(R.id.content_title_img);
        //mImgCatalog.setVisibility(View.VISIBLE);
        mEdtCount = (EditText) findViewById(R.id.edtNumberCount);
        mEdtCount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mEdtLable = (EditText) findViewById(R.id.edtLable);
        mEdtRemark = (EditText) findViewById(R.id.edtRemark);
        mSpnCurrency = (Spinner) findViewById(R.id.spinnerCurrency);
        mList = getData();
        mAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner_currency, mList);
        mSpnCurrency.setAdapter(mAdapter);
        mBtnCreateCode = (Button) findViewById(R.id.btnCreateCode);
        mImgQRCode = (ImageView) findViewById(R.id.imgQRCode);
        String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        mImageFile = new File(imageFilePath, "/" + bitName);
//        Glide.with(this).load(mImageFile.getAbsolutePath()).into(mImgQRCode);
//        Bitmap bitmap = BitmapFactory.decodeFile();
//        if (bitmap == null) {
//            getQRCode();
//        } else {
//            mImgQRCode.setImageBitmap(bitmap);
//        }
        // getQRCode();
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("YDC");
        data.add("mYDC");
        data.add("uYDC");
        return data;
    }

    public void getQRCode() {
//        try {
//            LogoConfig logoConfig = new LogoConfig();
//            Bitmap logoBitmap = logoConfig.modifyLogo(
//                    BitmapFactory.decodeResource(getResources(),R.mipmap.icon_white_2), BitmapFactory
//                            .decodeResource(getResources(),
//                                    R.mipmap.yadun));
//            Bitmap codeBitmap = createCode(mContent, logoBitmap);
//            mImgQRCode.setImageBitmap(codeBitmap);
//            saveBitmap(codeBitmap,"code.png");
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
    }

    private void initData() {
    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mImgCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceiptActivity.this, ReceiptExplainActivity.class));
            }
        });
        mBtnCreateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMCode();
            }
        });
        mSpnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mNumber = ".000000";
                        break;
                    case 1:
                        mNumber = ".000";
                        break;
                    case 2:
                        mNumber = null;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public Bitmap createCode(String content, Bitmap bmp)
            throws WriterException {
        int logoWidth = bmp.getWidth();
        int logoHeight = bmp.getHeight();
        int logoHaleWidth = logoWidth >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        int logoHaleHeight = logoHeight >= CODE_WIDTH ? LOGO_WIDTH_MIN
                : LOGO_WIDTH_MAX;
        // 将logo图片按martix设置的信息缩放
        Matrix m = new Matrix();
        float sx = (float) 2 * logoHaleWidth / logoWidth;
        float sy = (float) 2 * logoHaleHeight / logoHeight;
        m.setScale(sx, sy);// 设置缩放信息
        Bitmap newLogoBitmap = Bitmap.createBitmap(bmp, 0, 0, logoWidth,
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
        BitMatrix matrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_WIDTH, hints);
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
//        logoBitmap.recycle();
//        newLogoBitmap = null;
        return bitmap;
    }

    private void saveBitmap(Bitmap bitmap, String bitName) {
        //获取与应用相关联的路径
//        String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
//        File imageFile = new File(imageFilePath,"/" + bitName);// 通过路径创建保存文件
        imageFileUri = Uri.fromFile(mImageFile);
        if (mImageFile.exists()) {
            mImageFile.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(mImageFile);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
        }
    }


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

//                            Glide.with(ReceiptActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
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
//                                    }
//                                    if (firstIn==0){
//                                        createMCode();
//                                        firstIn++;
//                                    }
//                                }
//                            });
                            Glide.with(ReceiptActivity.this).load(url).placeholder(com.hyphenate.easeui.R.drawable.ease_default_expression).into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    LogoConfig logoConfig = new LogoConfig();
//
                                    if (logoBitmap==null) {

                                        logoBitmap = logoConfig.modifyLogo(
                                                BitmapFactory.decodeResource(getResources(), R.mipmap.icon_white_2)
                                                , BitmapFactory.decodeResource(getResources(),
                                                        R.mipmap.yadun) );
                                    }
                                    if (firstIn==0){
                                        createMCode();
                                        firstIn++;
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    @Override
    protected void onDestroy()  {
        try {

            super.onDestroy();
        }catch (Exception e){

        }
    }

    //生成二维码
    private void createMCode(){
        String remark = mEdtRemark.getText().toString();
        String lable = mEdtLable.getText().toString();
        String count = mEdtCount.getText().toString();
        if (remark.contains("&") || lable.contains("&")) {
            showToast(getResources().getString(R.string.receipt_forbidden_char));
        } else {
            mNewContent = mContent + (count.isEmpty()?"":("?amount=" + count))
                    + (lable.isEmpty()?"":"&label=" + lable)
                    + (remark.isEmpty()?"":"&message=" + remark);
            try {
                Bitmap codeBitmap = createCode(mNewContent, logoBitmap);
                mImgQRCode.setImageBitmap(codeBitmap);
                saveBitmap(codeBitmap, "code.png");
            } catch (WriterException e) {
                e.printStackTrace();
            }

            showToast(getResources().getString(R.string.receipt_new_qrcode));
        }
    }
}
