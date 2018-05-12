package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.TradeNoteResponse;

/**
 *
 */
public class SendNoteDetailsActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private TextView mTxtTime;
    private TextView mTxtCoin;
    private TextView mTxtAddress;
    private TextView mTxtCount;
    private TextView mTxtPresent;
    private TextView mTxtPractical;
    private TextView mTxtState;
    private TextView mTxtLabel;
    private TextView mTxtRemark;
    private Button mBtnAdd;
    private TradeNoteResponse.DataBeanXXX.ListBean.DataBean mNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_note_details);
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.trade_details_name));
        mTxtTime = (TextView) findViewById(R.id.transfer_time);
        mTxtCoin = (TextView) findViewById(R.id.coin);
        mTxtAddress = (TextView) findViewById(R.id.trade_address);
        mTxtCount = (TextView) findViewById(R.id.trade_num);
        mTxtPresent = (TextView) findViewById(R.id.trade_present);
        mTxtPractical = (TextView) findViewById(R.id.practical_num);
        mTxtState = (TextView) findViewById(R.id.state);
        mTxtLabel = (TextView) findViewById(R.id.label);
        mTxtRemark = (TextView) findViewById(R.id.remark);
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnAdd = (Button) findViewById(R.id.btnAddAddress);
    }

    private void initEvent() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendNoteDetailsActivity.this,IncreaseAddressActivity.class);
                intent.putExtra("address",mNote.getUsername());
                if(mNote.getLabel()!=null){
                    intent.putExtra("label",mNote.getLabel());
                }
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mNote = (TradeNoteResponse.DataBeanXXX.ListBean.DataBean) getIntent().getSerializableExtra("note");
        mTxtTime.setText(transferTime(mNote.getAddtime()));
        mTxtCoin.setText(mNote.getCoinname());
        mTxtAddress.setText(mNote.getUsername());
        mTxtCount.setText(remove(mNote.getNum()));
        mTxtPresent.setText(remove(mNote.getFee()));
        mTxtPractical.setText(remove(mNote.getMum()));
        if(mNote.getLabel()!=null&&mNote.getLabel().length()>0){
            mTxtLabel.setText(mNote.getLabel());
        }else{
            mTxtLabel.setText(getResources().getString(R.string.label_null));
        }
        if(mNote.getRemark()!=null&&mNote.getRemark().length()>0){
            mTxtRemark.setText(mNote.getRemark());
        }else{
            mTxtRemark.setText(getResources().getString(R.string.remark_null));
        }
        if(mNote.getZc_type().equals("0")){
            if(mNote.getStatus()==1){
                mTxtState.setText(getResources().getString(R.string.trade_details_success));
            }else{
                mTxtState.setText(getResources().getString(R.string.trade_details_ing));
            }
        }else{
            if(mNote.getStatus()==1){
                mTxtState.setText(getResources().getString(R.string.trade_tx_success));
            }else{
                mTxtState.setText(getResources().getString(R.string.trade_tx_ing));
            }
        }

    }
}
