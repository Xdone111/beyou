package byou.yadun.wallet.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class ShopInfo {
    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private int id;
        private String business_name;
        private String coin_type;
        private String contact_name;
        private String contact_phone;
        private int status;
        private String regtime;
        private String cover_path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getCoin_type() {
            return coin_type;
        }

        public void setCoin_type(String coin_type) {
            this.coin_type = coin_type;
        }

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getCover_path() {
            return cover_path;
        }

        public void setCover_path(String cover_path) {
            this.cover_path = cover_path;
        }
    }
}
