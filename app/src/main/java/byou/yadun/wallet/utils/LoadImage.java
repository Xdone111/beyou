package byou.yadun.wallet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */

public class LoadImage {
    ImageView mImage;
    String mUrl;
    private static  LoadImage mLoadImage;
    /**
     * 创建缓存
     */
    private LruCache<String, Bitmap> mCaches;
    public static LoadImage getInstance(){
        if(mLoadImage==null){
            synchronized (LoadImage.class){
                if(mLoadImage==null){
                    mLoadImage = new LoadImage();
                }
            }
        }
        return  mLoadImage;
    }
    public LoadImage() {
        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cachesSize = maxMemory/8;
        mCaches = new LruCache<String ,Bitmap>(cachesSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存时调用，返回实际大小
                return value.getByteCount();
            }
        };
    }

    /**
     * 增加到缓存
     * @param url
     * @param bimap
     */
    public void addBitmapCache(String url,Bitmap bimap){
        if(getBitmapCach(url)==null){
            mCaches.put(url,bimap);
        }
    }

    /**
     * 从缓存中获取图片
     * @param url
     * @return
     */
    private Bitmap getBitmapCach(String url) {
        return mCaches.get(url);
    }

    public void getImgByAsyncTask(ImageView img, String url){
        //从缓存中获取相应的图片
        Bitmap bitmap = getBitmapCach(url);
        if(bitmap==null){
            //如果没有 去网上下载
            new ImageTask(img ,url).execute(url);
        }else{
            img.setImageBitmap(bitmap);
        }
    }

    class ImageTask extends AsyncTask<String ,Void ,Bitmap> {
        private  ImageView mImageView;
        private  String mUrl;

        public ImageTask(ImageView img, String url) {
            this.mUrl = url;
            this.mImageView = img;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            //从网络获取图片
            Bitmap bitmap = getBitmapByUrl(url);
            if(bitmap!=null){
                //添加到缓存中
                addBitmapCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(mUrl)){
                mImageView.setImageBitmap(bitmap);
            }
        }
    }


    public Bitmap getBitmapByUrl(String urlString) {
        Bitmap bitmap;
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();
            in = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(in);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
