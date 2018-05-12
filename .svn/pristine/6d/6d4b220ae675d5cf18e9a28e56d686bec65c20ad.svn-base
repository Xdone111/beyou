package byou.yadun.wallet.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import byou.yadun.wallet.R;

/**
 * Created by Administrator on 2017/10/31.
 */

public class LuckeyDialog extends android.app.Dialog {

    public LuckeyDialog(Context context) {
        super(context);
    }

    public LuckeyDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String name;//发红包者的名称
        private String celebrate;//祝福语
        private Button red_page;

        //拆红包按钮
        private String openButtonText;
        private OnClickListener openButtonClickListener;

        //关闭按钮
        private String closeButtonText;
        private OnClickListener closeButtonClickListener;

        public Builder(Context context, int dialog) {
            this.context = context;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param name
         * @return
         */
        public Builder setName(int name) {
            this.name = (String) context.getText(name);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param name
         * @return
         */

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param closeButtonText
         * @return
         */
        public Builder setCloseButton(int closeButtonText,
                                      OnClickListener listener) {
            this.closeButtonText = (String) context
                    .getText(closeButtonText);
            this.closeButtonClickListener = listener;
            return this;
        }

        public Builder setCloseButton(String closeButtonText,
                                      OnClickListener listener) {
            this.closeButtonText = closeButtonText;
            this.closeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param openButtonText
         * @return
         */
        public Builder setOpenButton(int openButtonText,
                                     OnClickListener listener) {
            this.openButtonText = (String) context
                    .getText(openButtonText);
            this.openButtonClickListener = listener;
            return this;
        }

        public Builder setOpenButton(String openButtonText,
                                     OnClickListener listener) {
            this.openButtonText = openButtonText;
            this.openButtonClickListener = listener;
            return this;
        }

        public LuckeyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局
            final LuckeyDialog dialog = new LuckeyDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.redbag, null);

            red_page = (Button) layout.findViewById(R.id.open_btn);
             AnimationDrawable animationDrawable = (AnimationDrawable)red_page.getBackground();
            animationDrawable.start();

            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //设置发红包者姓名
            ((TextView) layout.findViewById(R.id.name11)).setText(name);

            //设置拆红包的按钮
            if (openButtonText != null) {
                ((Button) layout.findViewById(R.id.open_btn))
                        .setText(openButtonText);
                if (openButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.open_btn))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    openButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.open_btn).setVisibility(
                        View.GONE);
            }

            //设置关闭按钮
            if (closeButtonText != null) {
                ((Button) layout.findViewById(R.id.close))
                        .setText(closeButtonText);
                if (closeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.close))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    closeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.close).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

    }
}