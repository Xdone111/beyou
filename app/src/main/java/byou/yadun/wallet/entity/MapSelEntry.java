package byou.yadun.wallet.entity;

import java.util.List;

/**
 * Created by XDONE on 2018/1/15.
 */

public class MapSelEntry {


    /**
     * code : 1
     * data : [{"category_id":1,"category_name":"美食"},{"category_id":2,"category_name":"电影"},{"category_id":3,"category_name":"酒店住宿"},{"category_id":4,"category_name":"休闲娱乐"},{"category_id":5,"category_name":"KTV"},{"category_id":6,"category_name":"周边游"},{"category_id":7,"category_name":"丽人"},{"category_id":8,"category_name":"美发"}]
     * msg : 操作成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * category_id : 1
         * category_name : 美食
         */

        private int category_id;
        private String category_name;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
}
