package byou.yadun.wallet.entity;

import java.util.List;

/**
 *
 */

public class Price {
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

    public static class DataBean {
        /**
         * name : ydc
         * is_api_show : 1
         * market_price_longyin : 1.789900
         * market_price_qkm : 1.789900
         */
        private String name;
        private int is_api_show;
        private String market_price_longyin;
        private String market_price_qkm;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_api_show() {
            return is_api_show;
        }

        public void setIs_api_show(int is_api_show) {
            this.is_api_show = is_api_show;
        }

        public String getMarket_price_longyin() {
            return market_price_longyin;
        }

        public void setMarket_price_longyin(String market_price_longyin) {
            this.market_price_longyin = market_price_longyin;
        }

        public String getMarket_price_qkm() {
            return market_price_qkm;
        }

        public void setMarket_price_qkm(String market_price_qkm) {
            this.market_price_qkm = market_price_qkm;
        }
    }
}
