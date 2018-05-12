/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package byou.yadun.wallet.wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.hyphenate.chat.EMClient;

import byou.yadun.wallet.ByHelper;
import byou.yadun.wallet.R;
import byou.yadun.wallet.utils.ToastUtil;
import byou.yadun.wallet.utils.Utils;
import byou.yadun.wallet.zxing.camera.CameraManager;
import byou.yadun.wallet.zxing.decode.DecodeThread;
import byou.yadun.wallet.zxing.utils.BeepManager;
import byou.yadun.wallet.zxing.utils.CaptureActivityHandler;
import byou.yadun.wallet.zxing.utils.InactivityTimer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Hashtable;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback,OnClickListener {
	private static final String TAG = CaptureActivity.class.getSimpleName();
	
	private static final int REQUEST_CODE = 100;

	private static final int PARSE_BARCODE_FAIL = 300;
	
	private static final int PARSE_BARCODE_SUC = 200;

	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;

	private SurfaceView scanPreview = null;
	private RelativeLayout scanContainer;
	private RelativeLayout scanCropView;
	private ImageView scanLine;

	private Rect mCropRect = null;
	
	private boolean isFlashlightOpen;

	/**
	 * 图片的路径
	 */
	private String photoPath;

	private Handler mHandler = new MyHandler(this);
	private Bitmap scanBitmap;

	class MyHandler extends Handler {

		private WeakReference<Activity> activityReference;

		public MyHandler(Activity activity) {

			activityReference = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(final Message msg) {
			switch (msg.what) {
				case PARSE_BARCODE_SUC: // 解析图片成功
//					Toast.makeText(activityReference.get(),
//							"Analytical success，result：" + msg.obj, Toast.LENGTH_SHORT).show();
					Bundle bundle = new Bundle();
					bundle.putString("result", msg.obj.toString());
//					ToastUtil.showToast("图片解析"+msg.obj.toString());
					if (getIntent().getStringExtra("address").equals("saoyisao")){
						addContact(msg.obj.toString());
						finish();
					}else {
						startActivity(new Intent(CaptureActivity.this, ResultActivity.class).putExtras(bundle));
					}

					break;
				case PARSE_BARCODE_FAIL:// 解析图片失败
					Toast.makeText(activityReference.get(), "Resolution image failed",
							Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
			}
			super.handleMessage(msg);
		}
	}
	
	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	private boolean isHasSurface = false;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_capture);

		scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
		scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
		scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
		scanLine = (ImageView) findViewById(R.id.capture_scan_line);

		inactivityTimer = new InactivityTimer(this);
		beepManager = new BeepManager(this);

		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.9f);
		animation.setDuration(4500);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		scanLine.startAnimation(animation);
		
		findViewById(R.id.capture_flashlight).setOnClickListener(this);
		findViewById(R.id.capture_scan_photo).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// CameraManager must be initialized here, not in onCreate(). This is
		// necessary because we don't
		// want to open the camera driver and measure the screen size if we're
		// going to show the help on
		// first launch. That led to bugs where the scanning rectangle was the
		// wrong size and partially
		// off screen.
		cameraManager = new CameraManager(getApplication());

		handler = null;

		if (isHasSurface) {
			// The activity was paused but not stopped, so the surface still
			// exists. Therefore
			// surfaceCreated() won't be called, so init the camera here.
			initCamera(scanPreview.getHolder());
		} else {
			// Install the callback and wait for surfaceCreated() to init the
			// camera.
			scanPreview.getHolder().addCallback(this);
		}

		inactivityTimer.onResume();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		beepManager.close();
		cameraManager.closeDriver();
		if (!isHasSurface) {
			scanPreview.getHolder().removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!isHasSurface) {
			isHasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isHasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	/**
	 * A valid barcode has been found, so give an indication of success and show
	 * the results.
	 * 
	 * @param rawResult
	 *            The contents of the barcode.
	 * 
	 * @param bundle
	 *            The extras
	 */
	public void handleDecode(Result rawResult, Bundle bundle) {
		inactivityTimer.onActivity();
		beepManager.playBeepSoundAndVibrate();

		bundle.putInt("width", mCropRect.width());
		bundle.putInt("height", mCropRect.height());
		bundle.putString("result", rawResult.getText());
//		ToastUtil.showToast("扫描结果"+rawResult.getText());
		try{
			if (getIntent().getStringExtra("address").equals("saoyisao")){
				addContact(rawResult.getText());
				finish();
			}else {
				startActivity(new Intent(CaptureActivity.this, ResultActivity.class).putExtras(bundle));
			}
		}catch (Exception e){
			startActivity(new Intent(CaptureActivity.this, ResultActivity.class).putExtras(bundle));
		}

//		startActivity(new Intent(CaptureActivity.this, ResultActivity.class).putExtras(bundle));
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			// Creating the handler starts the preview, which can also throw a
			// RuntimeException.
			if (handler == null) {
				handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
			}

			initCrop();
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			// Barcode Scanner has seen crashes in the wild of this variety:
			// java.?lang.?RuntimeException: Fail to connect to camera service
			Log.w(TAG, "Unexpected error initializing camera", e);
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		// camera error
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage("相机打开出错，请稍后重试");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}

		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
	}

	public Rect getCropRect() {
		return mCropRect;
	}

	/**
	 * 初始化截取的矩形区域
	 */
	private void initCrop() {
		int cameraWidth = cameraManager.getCameraResolution().y;
		int cameraHeight = cameraManager.getCameraResolution().x;

		/** 获取布局中扫描框的位置信息 */
		int[] location = new int[2];
		scanCropView.getLocationInWindow(location);

		int cropLeft = location[0];
		int cropTop = location[1] - getStatusBarHeight();

		int cropWidth = scanCropView.getWidth();
		int cropHeight = scanCropView.getHeight();

		/** 获取布局容器的宽高 */
		int containerWidth = scanContainer.getWidth();
		int containerHeight = scanContainer.getHeight();

		/** 计算最终截取的矩形的左上角顶点x坐标 */
		int x = cropLeft * cameraWidth / containerWidth;
		/** 计算最终截取的矩形的左上角顶点y坐标 */
		int y = cropTop * cameraHeight / containerHeight;

		/** 计算最终截取的矩形的宽度 */
		int width = cropWidth * cameraWidth / containerWidth;
		/** 计算最终截取的矩形的高度 */
		int height = cropHeight * cameraHeight / containerHeight;

		/** 生成最终的截取的矩形 */
		mCropRect = new Rect(x, y, width + x, height + y);
	}

	private int getStatusBarHeight() {
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode == RESULT_OK) {

			switch (requestCode) {

				case REQUEST_CODE:

					String[] proj = { MediaStore.Images.Media.DATA };
					// 获取选中图片的路径
					Cursor cursor = getContentResolver().query(intent.getData(),
							proj, null, null, null);

					if (cursor.moveToFirst()) {

						int column_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						photoPath = cursor.getString(column_index);
						if (photoPath == null) {
							photoPath = Utils.getPath(getApplicationContext(),
									intent.getData());
							Log.i("123path  Utils", photoPath);
						}
						Log.i("123path", photoPath);

					}

					cursor.close();

					new Thread(new Runnable() {

						@Override
						public void run() {

							Result result = scanningImage(photoPath);
							// String result = decode(photo_path);
							if (result == null) {
								Looper.prepare();
								Toast.makeText(getApplicationContext(), "图片格式有误", Toast.LENGTH_SHORT)
										.show();
								Looper.loop();
							} else {
								Log.i("123result", result.toString());
								// 数据返回
								String recode = recode(result.toString());
								Intent data = new Intent();
								data.putExtra("result", recode);
								setResult(300, data);
								Message m = mHandler.obtainMessage();
								m.what = PARSE_BARCODE_SUC;
								m.obj = recode;
								mHandler.sendMessage(m);
								finish();
							}
						}
					}).start();
					break;
			}
		}
	}
	//
	private String recode(String str) {
		String formart = "";

		try {
			boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
					.canEncode(str);
			if (ISO) {
				formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
				Log.i("1234      ISO8859-1", formart);
			} else {
				formart = str;
				Log.i("1234      stringExtra", str);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formart;
	}
	//解析二维码图片
	protected Result scanningImage(String path) {
		if (TextUtils.isEmpty(path)) {

			return null;

		}
		// DecodeHintType 和EncodeHintType
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 先获取原大小
		scanBitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false; // 获取新的大小

		int sampleSize = (int) (options.outHeight / (float) 200);

		if (sampleSize <= 0)
			sampleSize = 1;
		options.inSampleSize = sampleSize;
		scanBitmap = BitmapFactory.decodeFile(path, options);



		RGBLuminanceSource1 source = new RGBLuminanceSource1(scanBitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {

			return reader.decode(bitmap1, hints);

		} catch (NotFoundException e) {

			e.printStackTrace();

		} catch (ChecksumException e) {

			e.printStackTrace();

		} catch (FormatException e) {

			e.printStackTrace();

		}

		return null;

	}
	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
		case R.id.capture_scan_photo: // 图片识别
			Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
			if (Build.VERSION.SDK_INT < 19) {
				innerIntent.setAction(Intent.ACTION_GET_CONTENT);
			} else {
				innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			}

			innerIntent.setType("image/*");

			Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");

			CaptureActivity.this
					.startActivityForResult(wrapperIntent, REQUEST_CODE);
			break;

		case R.id.capture_flashlight:
			if (isFlashlightOpen) {
				cameraManager.setTorch(false); // 关闭闪光灯
				isFlashlightOpen = false;
			}
			else {
				cameraManager.setTorch(true); // 打开闪光灯
				isFlashlightOpen = true;
			}
			break;
		default:
			break;
		}
	}
	//加好友
	public void addContact(final String name){
		if(EMClient.getInstance().getCurrentUser().equals(name)){
//			new EaseAlertDialog(this, R.string.not_add_myself).show();
			ToastUtil.showToast(getResources().getString(R.string.not_add_myself));
			return;
		}

		if(ByHelper.getInstance().getContactList().containsKey(name)){
			//let the user know the contact already in your contact list
			if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(name)){
//				new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
				ToastUtil.showToast(getResources().getString(R.string.user_already_in_contactlist));
				return;
			}
//			new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
			ToastUtil.showToast(getResources().getString(R.string.This_user_is_already_your_friend));
			return;
		}


		new Thread(new Runnable() {
			public void run() {

				try {
					//demo use a hardcode reason here, you need let user to input if you like
					String s = getResources().getString(R.string.Add_a_friend);
					EMClient.getInstance().contactManager().addContact(name, s);
					runOnUiThread(new Runnable() {
						public void run() {
							String s1 = getResources().getString(R.string.send_successful);
							Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
						}
					});
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							String s2 = getResources().getString(R.string.Request_add_buddy_failure);
							Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		}).start();
	}
}

