package com.hyphenate.easeui.widget.chatrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMNormalFileMessageBody;
import com.hyphenate.easeui.ui.EaseShowNormalFileActivity;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.FileUtils;
import com.hyphenate.util.TextFormater;

import java.io.File;

public class EaseChatRowFile extends EaseChatRow{

    protected TextView fileNameView;
	protected TextView fileSizeView;
    protected TextView fileStateView;
    
    protected EMCallBack sendfileCallBack;
    
    protected boolean isNotifyProcessed;
    private EMNormalFileMessageBody fileMessageBody;

    public EaseChatRowFile(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
	    inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
	            com.hyphenate.easeui.R.layout.ease_row_received_file : com.hyphenate.easeui.R.layout.ease_row_sent_file, this);
	}

	@Override
	protected void onFindViewById() {
	    fileNameView = (TextView) findViewById(com.hyphenate.easeui.R.id.tv_file_name);
        fileSizeView = (TextView) findViewById(com.hyphenate.easeui.R.id.tv_file_size);
        fileStateView = (TextView) findViewById(com.hyphenate.easeui.R.id.tv_file_state);
        percentageView = (TextView) findViewById(com.hyphenate.easeui.R.id.percentage);
	}


	@Override
	protected void onSetUpView() {
	    fileMessageBody = (EMNormalFileMessageBody) message.getBody();
        String filePath = fileMessageBody.getLocalUrl();
        fileNameView.setText(fileMessageBody.getFileName());
        fileSizeView.setText(TextFormater.getDataSize(fileMessageBody.getFileSize()));
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            File file = new File(filePath);
            if (file.exists()) {
                fileStateView.setText(com.hyphenate.easeui.R.string.Have_downloaded);
            } else {
                fileStateView.setText(com.hyphenate.easeui.R.string.Did_not_download);
            }
            return;
        }

        // until here, to sending message
        handleSendMessage();
	}

	/**
	 * handle sending message
	 */
    protected void handleSendMessage() {
        setMessageSendCallback();
        switch (message.status()) {
        case SUCCESS:
            progressBar.setVisibility(INVISIBLE);
            if(percentageView != null)
                percentageView.setVisibility(INVISIBLE);
            statusView.setVisibility(INVISIBLE);
            break;
        case FAIL:
            progressBar.setVisibility(INVISIBLE);
            if(percentageView != null)
                percentageView.setVisibility(INVISIBLE);
            statusView.setVisibility(VISIBLE);
            break;
        case INPROGRESS:
            progressBar.setVisibility(VISIBLE);
            if(percentageView != null){
                percentageView.setVisibility(VISIBLE);
                percentageView.setText(message.progress() + "%");
            }
            statusView.setVisibility(INVISIBLE);
            break;
        default:
            progressBar.setVisibility(INVISIBLE);
            if(percentageView != null)
                percentageView.setVisibility(INVISIBLE);
            statusView.setVisibility(VISIBLE);
            break;
        }
    }
	

	@Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        String filePath = fileMessageBody.getLocalUrl();
        File file = new File(filePath);
        if (file.exists()) {
            // open files if it exist
            FileUtils.openFile(file, (Activity) context);
        } else {
            // download the file
            context.startActivity(new Intent(context, EaseShowNormalFileActivity.class).putExtra("msg", message));
        }
        if (message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked() && message.getChatType() == ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
}