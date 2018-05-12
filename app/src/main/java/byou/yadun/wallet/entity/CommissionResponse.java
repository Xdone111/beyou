package byou.yadun.wallet.entity;

/**
 *
 */
public class CommissionResponse {
    private String msg;
    private String code;
    private Commission data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Commission getData() {
        return data;
    }

    public void setData(Commission data) {
        this.data = data;
    }

   public static class Commission{
        private String zc_fee;

        public String getZc_fee() {
            return zc_fee;
        }

        public void setZc_fee(String zc_fee) {
            this.zc_fee = zc_fee;
        }

        @Override
        public String toString() {
            return "Commission{" +
                    "zc_fee='" + zc_fee + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommissionResponse{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
