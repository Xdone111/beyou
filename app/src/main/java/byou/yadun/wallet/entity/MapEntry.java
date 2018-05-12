package byou.yadun.wallet.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDONE on 2018/1/10.
 */

public class MapEntry implements  Serializable {


    /**
     * code : 1
     * data : [{"business_name":"德玛西亚辣条店","id":4,"longitude":"","dimension":""},{"business_name":"菊花赵信3123366","id":5,"longitude":"100.688515","dimension":"13.764428"},{"business_name":"提莫蘑菇店661","id":6,"longitude":"101.021679","dimension":"13.324816"},{"business_name":"卖剑","id":7,"longitude":"100.435265","dimension":"13.644411"},{"business_name":"小卡水果店","id":8,"longitude":"101.478737","dimension":"12.94615"},{"business_name":"黑暗之女-安妮","id":9,"longitude":"100.586646","dimension":"13.760605"},{"business_name":"test1","id":10,"longitude":"","dimension":""}]
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

    public static class DataBean implements Serializable {
        /**
         * business_name : 德玛西亚辣条店
         * id : 4
         * longitude :
         * dimension :
         */

        private String business_name;
        private int id;
        private String longitude;
        private String dimension;

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }


        public DataBean(String business_name, int id, String longitude, String dimension) {
            this.business_name = business_name;
            this.id = id;
            this.longitude = longitude;
            this.dimension = dimension;
        }
    }
}
