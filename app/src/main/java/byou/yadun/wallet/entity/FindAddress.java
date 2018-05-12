package byou.yadun.wallet.entity;

/**
 *
 */

public class FindAddress {
    private String msg;
    private String data;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "FindAddress{" +
                "msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
