package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.bumptech.glide.Glide;

import java.util.List;

public class EmojiconGridAdapter extends ArrayAdapter<EaseEmojicon>{

    private EaseEmojicon.Type emojiconType;


    public EmojiconGridAdapter(Context context, int textViewResourceId, List<EaseEmojicon> objects, EaseEmojicon.Type emojiconType) {
        super(context, textViewResourceId, objects);
        this.emojiconType = emojiconType;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            if(emojiconType == EaseEmojicon.Type.BIG_EXPRESSION){
                convertView = View.inflate(getContext(), com.hyphenate.easeui.R.layout.ease_row_big_expression, null);
            }else{
                convertView = View.inflate(getContext(), com.hyphenate.easeui.R.layout.ease_row_expression, null);
            }
        }

        ImageView imageView = (ImageView) convertView.findViewById(com.hyphenate.easeui.R.id.iv_expression);
        TextView textView = (TextView) convertView.findViewById(com.hyphenate.easeui.R.id.tv_name);
        EaseEmojicon emojicon = getItem(position);
        if(textView != null && emojicon.getName() != null){
            textView.setText(emojicon.getName());
        }

        if(EaseSmileUtils.DELETE_KEY.equals(emojicon.getEmojiText())){
            imageView.setImageResource(com.hyphenate.easeui.R.drawable.ease_delete_expression);
        }else{
            if(emojicon.getIcon() != 0){
                imageView.setImageResource(emojicon.getIcon());
            }else if(emojicon.getIconPath() != null){
                Glide.with(getContext()).load(emojicon.getIconPath()).placeholder(com.hyphenate.easeui.R.drawable.ease_default_expression).into(imageView);
            }
        }
        
        
        return convertView;
    }
    
}