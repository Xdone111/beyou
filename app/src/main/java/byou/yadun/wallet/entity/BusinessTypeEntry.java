package byou.yadun.wallet.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDONE on 2018/1/16.
 */

public class BusinessTypeEntry  implements Serializable{

    /**
     * code : 1
     * data : [{"id":5,"business_name":"菊花赵信3123366","business_description":"铲墙一怔8","business_about":"长枪一阵，随后抢出玉龙","business_address":"下路草丛buff出","contact_name":"赵信8","contact_phone":"15555555558","email":"4544546@qq.com8","cover_path":"/uploads/picture/20161227/59c34d6938aee5d9c361ffd991970399.jpg","status":1,"regtime":1482738333,"update_time":1482819958,"coin_type":"1,43,44,48","remark":"xiaolang8","category_name":2,"country":2,"province":6,"longitude":"100.688515","dimension":"13.764428","point":11,"sort":0}]
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

    public static class DataBean  implements Serializable{
        /**
         * id : 5
         * business_name : 菊花赵信3123366
         * business_description : 铲墙一怔8
         * business_about : 长枪一阵，随后抢出玉龙
         * business_address : 下路草丛buff出
         * contact_name : 赵信8
         * contact_phone : 15555555558
         * email : 4544546@qq.com8
         * cover_path : /uploads/picture/20161227/59c34d6938aee5d9c361ffd991970399.jpg
         * status : 1
         * regtime : 1482738333
         * update_time : 1482819958
         * coin_type : 1,43,44,48
         * remark : xiaolang8
         * category_name : 2
         * country : 2
         * province : 6
         * longitude : 100.688515
         * dimension : 13.764428
         * point : 11
         * sort : 0
         */

        private int id;
        private String business_name;
        private String business_description;
        private String business_about;
        private String business_address;
        private String contact_name;
        private String contact_phone;
        private String email;
        private String cover_path;
        private int status;
        private int regtime;
        private int update_time;
        private String coin_type;
        private String remark;
        private int category_name;
        private int country;
        private int province;
        private String longitude;
        private String dimension;
        private int point;
        private int sort;

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

        public String getBusiness_description() {
            return business_description;
        }

        public void setBusiness_description(String business_description) {
            this.business_description = business_description;
        }

        public String getBusiness_about() {
            return business_about;
        }

        public void setBusiness_about(String business_about) {
            this.business_about = business_about;
        }

        public String getBusiness_address() {
            return business_address;
        }

        public void setBusiness_address(String business_address) {
            this.business_address = business_address;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCover_path() {
            return cover_path;
        }

        public void setCover_path(String cover_path) {
            this.cover_path = cover_path;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRegtime() {
            return regtime;
        }

        public void setRegtime(int regtime) {
            this.regtime = regtime;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public String getCoin_type() {
            return coin_type;
        }

        public void setCoin_type(String coin_type) {
            this.coin_type = coin_type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCategory_name() {
            return category_name;
        }

        public void setCategory_name(int category_name) {
            this.category_name = category_name;
        }

        public int getCountry() {
            return country;
        }

        public void setCountry(int country) {
            this.country = country;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
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

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
