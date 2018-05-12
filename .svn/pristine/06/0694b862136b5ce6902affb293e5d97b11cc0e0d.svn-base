package byou.yadun.wallet.Chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacket.widget.ChatRowRandomPacket;
import com.easemob.redpacket.widget.ChatRowRedPacket;
import com.easemob.redpacket.widget.ChatRowRedPacketAck;
import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.easemob.redpacketui.utils.RPRedPacketUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import byou.yadun.wallet.ByHelper;
import byou.yadun.wallet.R;
import byou.yadun.wallet.domain.EmojiconExampleGroupData;
import byou.yadun.wallet.domain.RobotUser;
import byou.yadun.wallet.entity.RedBagDetalBean;
import byou.yadun.wallet.entity.RedBagReceiveBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;
import byou.yadun.wallet.MainActivity;
import byou.yadun.wallet.widget.ChatRowVoiceCall;
import byou.yadun.wallet.widget.EaseChatRowRecall;
import byou.yadun.wallet.widget.LuckeyDialog;

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int MESSAGE_TYPE_RECALL = 9;
    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int MESSAGE_TYPE_RECV_RANDOM = 11;
    private static final int MESSAGE_TYPE_SEND_RANDOM = 12;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code
    private String[] avatar = {"http://api.ydchain.cc/app/ic1.png",
            "http://api.ydchain.cc/app/ic2.png",
            "http://api.ydchain.cc/app/ic3.png",
            "http://api.ydchain.cc/app/ic4.png",
            "http://api.ydchain.cc/app/ic5.png",
            "http://api.ydchain.cc/app/ic6.png",
            "http://api.ydchain.cc/app/ic7.png",
            "http://api.ydchain.cc/app/ic8.png",
            "http://api.ydchain.cc/app/ic9.png",
            "http://api.ydchain.cc/app/ic10.png"};
    /**
     * if it is chatBot
     */
    private boolean isRobot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState,
                ByHelper.getInstance().getModel().isMsgRoaming() && (chatType != EaseConstant.CHATTYPE_CHATROOM));
    }

    @Override
    protected void setUpView() {
        setChatFragmentHelper(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            Map<String, RobotUser> robotMap = ByHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }
        super.setUpView();
        // set click listener
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        if (chatType != Constant.CHATTYPE_CHATROOM) {
            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
        }
        if (chatType == Constant.CHATTYPE_SINGLE) {
            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        }
        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
//        //聊天室暂时不支持红包功能
        //red packet code : 注册红包菜单选项
//        if (chatType != Constant.CHATTYPE_CHATROOM) {
//            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
//        }
        //end of red packet code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;

                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);
                    break;
                case ContextMenuActivity.RESULT_CODE_RECALL://recall
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMMessage msgNotification = EMMessage.createTxtSendMessage(" ", contextMenuMessage.getTo());
                                EMTextMessageBody txtBody = new EMTextMessageBody(getResources().getString(R.string.msg_recall_by_self));
                                msgNotification.addBody(txtBody);
                                msgNotification.setMsgTime(contextMenuMessage.getMsgTime());
                                msgNotification.setLocalTime(contextMenuMessage.getMsgTime());
                                msgNotification.setAttribute(Constant.MESSAGE_TYPE_RECALL, true);
                                EMClient.getInstance().chatManager().recallMessage(contextMenuMessage);
                                EMClient.getInstance().chatManager().saveMessage(msgNotification);
                                messageList.refresh();
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                    break;

                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
        //设置要发送扩展消息用户昵称
//        message.setAttribute(Constant.USER_NAME, SPUtils.getNickname(MyApplication.applicationContext,""));
        message.setAttribute(Constant.USER_NAME, PreferenceUtil.getString("usernickName", new PreferenceUtil().getUser("account")));
        //设置要发送扩展消息用户头像
        message.setAttribute(Constant.HEAD_IMAGE_URL, avatar[PreferenceUtil.getInt("userheadimage", 1)]);

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult(
                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
                    REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
            startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
        if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
            openRedPacket(getActivity(), chatType, message, toChatUsername, messageList);
            return true;
        }
        //end of red packet code
        return false;
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
            if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                RedPacketUtil.receiveRedPacketAckMessage(message);
                messageList.refresh();
            }
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                startVideoCall();
                break;
            //red packet code : 进入发红包页面
            case ITEM_RED_PACKET:
//            //注意：不再支持原有的startActivityForResult进入红包相关页面
                int itemType;
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//                    ToastUtil.showToast("单聊");
                    itemType = RPConstant.RP_ITEM_TYPE_SINGLE;
                    //小额随机红包
                    //itemType = RPConstant.RP_ITEM_TYPE_RANDOM;
                } else {
//                    ToastUtil.showToast("群聊");
                    itemType = RPConstant.RP_ITEM_TYPE_GROUP;
                }
                startRedPacket(itemType, toChatUsername);
                break;
            //end of red packet code
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 11;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //messagee recall
                else if (message.getBooleanAttribute(Constant.MESSAGE_TYPE_RECALL, false)) {
                    return MESSAGE_TYPE_RECALL;
                }
                //red packet code : 红包消息、红包回执消息以及转账消息的chatrow type
                else if (RedPacketUtil.isRandomRedPacket(message)) {
                    //小额随机红包
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RANDOM : MESSAGE_TYPE_SEND_RANDOM;
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
                    //发送红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET : MESSAGE_TYPE_SEND_RED_PACKET;
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    //领取红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET_ACK : MESSAGE_TYPE_SEND_RED_PACKET_ACK;
                }
                //end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
                //recall message
                else if (message.getBooleanAttribute(Constant.MESSAGE_TYPE_RECALL, false)) {
                    return new EaseChatRowRecall(getActivity(), message, position, adapter);
                }
                //red packet code : 红包消息、红包回执消息以及转账消息的chat row
                else if (RedPacketUtil.isRandomRedPacket(message)) {//小额随机红包
                    return new ChatRowRandomPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//红包消息
                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//红包回执消息
                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
                }
                //end of red packet code
            }
            return null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //调用该方法可防止红包SDK引起的内存泄漏
        RPRedPacketUtil.getInstance().detachView();
    }

    public void openRedPacket(final FragmentActivity activity, final int chatType, final EMMessage message, final String toChatUsername, final EaseChatMessageList messageList) {
        try {
            if (chatType == EaseConstant.CHATTYPE_GROUP) {
                //第一个参数，红包id扩展参数
                getRBCountGroup(Integer.parseInt(message.getStringAttribute("NXDREDBAG")), toChatUsername, message.getStringAttribute(Constant.USER_NAME, "好友"), message.getFrom());
            } else {
                getRBCount(Integer.parseInt(message.getStringAttribute("NXDREDBAG")), toChatUsername, message.getStringAttribute(Constant.USER_NAME, "好友"), message.getFrom());

            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCanceledOnTouchOutside(false);
        RPRedPacketUtil.getInstance().openRedPacket(wrapperRedPacketInfo(chatType, message), activity, new RPRedPacketUtil.RPOpenPacketCallback() {
            @Override
            public void onSuccess(String senderId, String senderNickname, String myAmount) {
                //领取红包成功 发送消息到聊天窗口
                String receiverId = EMClient.getInstance().getCurrentUser();
                //设置默认值为id
                String receiverNickname = receiverId;
                EaseUser receiverUser = EaseUserUtils.getUserInfo(receiverId);
                if (receiverUser != null) {
                    receiverNickname = TextUtils.isEmpty(receiverUser.getNick()) ? receiverUser.getUsername() : receiverUser.getNick();
                }
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    if (!isRandomRedPacket(message)) {
                        EMMessage msg = EMMessage.createTxtSendMessage(String.format(activity.getResources().getString(com.easemob.redpacket.R.string.msg_someone_take_red_packet), receiverNickname), toChatUsername);
                        msg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, true);
                        msg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_RECEIVER_NAME, receiverNickname);
                        msg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_SENDER_NAME, senderNickname);
                        EMClient.getInstance().chatManager().sendMessage(msg);
                    }
                } else {
                    sendRedPacketAckMessage(message, senderId, senderNickname, receiverId, receiverNickname, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            messageList.refresh();
                        }

                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void showLoading() {
                progressDialog.show();
            }

            @Override
            public void hideLoading() {
                progressDialog.dismiss();
            }

            @Override
            public void onError(String code, String message) {
//                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 封装拆红包所需参数
     *
     * @param chatType 聊天类型
     * @param message  EMMessage
     * @return RedPacketInfo
     */
    private static RedPacketInfo wrapperRedPacketInfo(int chatType, EMMessage message) {
        String redPacketId = message.getStringAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_ID, "");
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.redPacketId = redPacketId;
        redPacketInfo.messageDirect = getMessageDirect(message);
        redPacketInfo.chatType = chatType;
        return redPacketInfo;
    }

    private static String getMessageDirect(EMMessage message) {
        String messageDirect;
        if (message.direct() == EMMessage.Direct.SEND) {
            messageDirect = com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_DIRECT_SEND;
        } else {
            messageDirect = com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_DIRECT_RECEIVE;
        }
        return messageDirect;
    }

    /**
     * 判断红包类型是否为小额随机红包
     *
     * @param message EMMessage
     * @return true or false
     */
    public static boolean isRandomRedPacket(EMMessage message) {
        String redPacketType = message.getStringAttribute(com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_ATTR_RED_PACKET_TYPE, "");
        return !TextUtils.isEmpty(redPacketType) && redPacketType.equals(com.easemob.redpacketsdk.constant.RPConstant.RED_PACKET_TYPE_RANDOM);
    }

    /**
     * 使用cmd消息发送领到红包之后的回执消息
     */
    private static void sendRedPacketAckMessage(final EMMessage message, final String senderId, final String senderNickname, String receiverId, final String receiverNickname, final EMCallBack callBack) {
        //创建透传消息
        final EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        cmdMsg.setChatType(EMMessage.ChatType.Chat);
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(com.easemob.redpacketsdk.constant.RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        cmdMsg.addBody(cmdBody);
        cmdMsg.setTo(senderId);
        //设置扩展属性
        cmdMsg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, true);
        cmdMsg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_SENDER_NAME, senderNickname);
        cmdMsg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_RECEIVER_NAME, receiverNickname);
        cmdMsg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_SENDER_ID, senderId);
        cmdMsg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_RECEIVER_ID, receiverId);
        cmdMsg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_GROUP_ID, message.getTo());
        cmdMsg.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                //保存消息到本地
                EMMessage sendMessage = EMMessage.createTxtSendMessage("content", message.getTo());
                sendMessage.setChatType(EMMessage.ChatType.GroupChat);
                sendMessage.setFrom(message.getFrom());
                sendMessage.setTo(message.getTo());
                sendMessage.setMsgId(UUID.randomUUID().toString());
                sendMessage.setMsgTime(cmdMsg.getMsgTime());
                sendMessage.setUnread(false);//去掉未读的显示
                sendMessage.setDirection(EMMessage.Direct.SEND);
                sendMessage.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, true);
                sendMessage.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_SENDER_NAME, senderNickname);
                sendMessage.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_RECEIVER_NAME, receiverNickname);
                sendMessage.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_SENDER_ID, senderId);
                EMClient.getInstance().chatManager().saveMessage(sendMessage);
                callBack.onSuccess();
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /* fromname消息来自谁
    *redbag红包id
    */
    public void showrb(final String fromname, final int redbag) {
        LuckeyDialog.Builder builder = new LuckeyDialog.Builder(getActivity(), R.style.Dialog);//调用style中的Diaog
        builder.setName(fromname);
        builder.setOpenButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //领取红包成功 发送消息到聊天窗口
                String receiverId = EMClient.getInstance().getCurrentUser();
                //设置默认值为id
                String receiverNickname = receiverId;
                EaseUser receiverUser = EaseUserUtils.getUserInfo(receiverId);
                if (receiverUser != null) {
                    receiverNickname = TextUtils.isEmpty(receiverUser.getNick()) ? receiverUser.getUsername() : receiverUser.getNick();
                }
                EMMessage msg = EMMessage.createTxtSendMessage(String.format(getActivity().getResources().getString(com.easemob.redpacket.R.string.msg_someone_take_red_packet), receiverNickname), toChatUsername);
                msg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, true);
                msg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_RECEIVER_NAME, receiverNickname);
                msg.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_SENDER_NAME, fromname);
                if (chatType == EaseConstant.CHATTYPE_GROUP) {
                    msg.setChatType(EMMessage.ChatType.GroupChat);
                    receiveGroupRedbag(redbag, getActivity(), fromname, msg);
                } else {
                    receiveRedbag(redbag, getActivity(), fromname, msg);
                }
                dialog.dismiss();

            }
        });

        builder.setCloseButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });


        android.app.Dialog dialog = builder.create();
        Window dialogWindow = dialog.getWindow();


        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        dialog.show();
    }

    /*
        单聊查询红包
    * rbid红包id
    * a聊天对象
    * b发起聊天人
    * c消息来自谁
    * */
    public void getRBCount(final int rbid, String a, final String b, final String c) {
        Log.d("单聊查询红包", "红包id" + rbid + "验证码" + PreferenceUtil.getString("uid", " ") + "  " + PreferenceUtil.getString("token", " "));
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("packet_id", rbid + "");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/inquired.html?", parmas,
                new HttpManager.ResultCallback<RedBagDetalBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast(getResources().getString(R.string.forbidden_net));
                    }

                    @Override
                    public void onResponse(RedBagDetalBean response) {
//                        ToastUtil.showToast(response.toString());
                        if (response.getData().getSurplus() == 0) {
                            Intent intent1 = new Intent(getActivity(), RedBagDetalActivity.class);
                            intent1.putExtra("redbagid", rbid);
                            startActivity(intent1);
                        } else {
                            if (toChatUsername.equals(c)) {
                                showrb(c, rbid);
                            } else {
//                                Toast.makeText(getActivity(), "本人在拆红包", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), RedBagDetalActivity.class);
                                intent.putExtra("redbagid", rbid);
                                intent.putExtra("redbagfromname", b);
                                startActivity(intent);
                            }
                        }
                    }
                });
    }

    /*
        群聊查询红包
    * rbid红包id
    * b发起聊天人
    * c消息来自谁
    * */
    public void getRBCountGroup(final int rbid, String a, final String b, final String c) {
        Log.d("群聊红包查询", "a" + a + "b" + b + "c" + c);
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("packet_id", rbid + "");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/inquired.html?", parmas,
                new HttpManager.ResultCallback<RedBagDetalBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtil.showToast(getResources().getString(R.string.forbidden_net));
                    }

                    @Override
                    public void onResponse(RedBagDetalBean response) {
                        List temp=new ArrayList();
                        for (int i=0;i<response.getData().getPeople().size();i++){
                            temp.add(response.getData().getPeople().get(i).getUsernam());
                        }
                        if (response.getData().getSurplus() == 0 || PreferenceUtil.getInt("YJQG", 0) == rbid||temp.contains(new PreferenceUtil().getUser("account"))) {
                            Intent intent1 = new Intent(getActivity(), RedBagDetalActivity.class);
                            intent1.putExtra("redbagid", rbid);
                            intent1.putExtra("redbagfromname", c);
                            startActivity(intent1);
                        } else {
                            showrb(c, rbid);
                        }
                    }
                });
    }

    //发红包
    public void startRedPacket(int itemType, final String toChatUsername) {
        if (itemType == com.easemob.redpacketsdk.constant.RPConstant.RP_ITEM_TYPE_GROUP) {
            //nxd进入群发红包
            Intent intent1 = new Intent(getActivity(), RedBagGroupActivity.class);
            intent1.putExtra("tored", toChatUsername);//发送对象
            getActivity().startActivity(intent1);
        } else {
            Intent intent1 = new Intent(getActivity(), RedBagActivity.class);
            intent1.putExtra("tored", toChatUsername);
            getActivity().startActivity(intent1);
        }
    }

    public void receiveRedbag(final int num, final Context context, final String fromname, final EMMessage msg) {
        Log.d("登录红包id", num + "");
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("packet_id", num + "");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/receive.html?", parmas,
                new HttpManager.ResultCallback<RedBagReceiveBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(RedBagReceiveBean response) {
//                        if (response.getCode().equals("1")){
//                            ToastUtil.showToast(response.getMsg());
//                        }else {
                        if (response.getCode().equals("1")) {
                            EMClient.getInstance().chatManager().sendMessage(msg);
                        }
                        Intent intent1 = new Intent(context, RedBagDetalActivity.class);
                        intent1.putExtra("redbagid", num);
                        intent1.putExtra("redbaginfo", response.getMsg());
                        intent1.putExtra("redbagfromname", fromname);
                        startActivity(intent1);
//                        }
                    }
                });
    }

    //接收群红包
    public void receiveGroupRedbag(final int num, final Context context, final String fromname, final EMMessage msg) {
        Log.d("登录红包id", num + "");
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("packet_id", num + "");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/receive.html?", parmas,
                new HttpManager.ResultCallback<RedBagReceiveBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(RedBagReceiveBean response) {
                        ToastUtil.showToast(response.toString());
                        if (response.getCode().equals("1")) {
                            EMClient.getInstance().chatManager().sendMessage(msg);
                            //记录是否已经抢过该红包
                            PreferenceUtil.commitInt("YJQG", num);
                        }
                        ToastUtil.showToast(response.getMsg());
//                        }else {
                        Intent intent1 = new Intent(context, RedBagDetalActivity.class);
                        intent1.putExtra("redbagid", num);
                        intent1.putExtra("redbaginfo", response.getMsg());
                        intent1.putExtra("redbagfromname", fromname);
                        startActivity(intent1);
//                        }
                    }
                });
    }
}
