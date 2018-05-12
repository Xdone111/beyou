package byou.yadun.wallet.entity;

/**
 * Created by Administrator on 2017/10/30.
 */

public class SendRedBagBean {
    @Override
    public String toString() {
        return "SendRedBagBean{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }

    /**
     * msg : 发红包成功！
     * data : {"Packet_id":"13"}
     * code : 1
     */

    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "Packet_id='" + Packet_id + '\'' +
                    '}';
        }

        /**
         * Packet_id : 13
         */

        private String Packet_id;

        public String getPacket_id() {
            return Packet_id;
        }

        public void setPacket_id(String Packet_id) {
            this.Packet_id = Packet_id;
        }
    }
}
