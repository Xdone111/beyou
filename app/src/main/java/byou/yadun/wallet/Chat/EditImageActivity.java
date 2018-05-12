package byou.yadun.wallet.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.utils.PreferenceUtil;

/**
 * //编辑头像
 *
 */
public class EditImageActivity extends BaseActivity {
    private GridView gridView;
    private static final int[] avatar={R.mipmap.ic1,R.mipmap.ic2,R.mipmap.ic3,R.mipmap.ic4,R.mipmap.ic5,R.mipmap.ic6,
            R.mipmap.ic7,R.mipmap.ic8,R.mipmap.ic9,R.mipmap.ic10};
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_editimage);
        gridView= (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return avatar.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if(convertView==null){
                    imageView=new ImageView(EditImageActivity.this);
                    imageView.setAdjustViewBounds(true);
                    imageView.setMaxHeight(200);
                    imageView.setMaxWidth(200);
                    imageView.setPadding(20,20,20,20);
                }else{
                    imageView=(ImageView)convertView;
                }
                imageView.setImageResource(avatar[position]);
                return imageView;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferenceUtil.commitInt("userheadimage",position);
                finish();
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened

    }
    

}
