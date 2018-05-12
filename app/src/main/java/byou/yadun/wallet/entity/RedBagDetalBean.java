package byou.yadun.wallet.entity;


import java.util.List;

/**
 * Created by Administrator on 2017/10/31.
 */

public class RedBagDetalBean {

    /**
     * msg : 查询成功
     * data : {"id":180,"userid":341,"coinname":"ydc","num":2,"mum":"0.01","endtime":1510397340,"status":0,"surplus":1,"original_balance":"0.005","people":[{"mum":"0.00500000","addtime":"2017-11-10 18:49:09","nickname":"风"}]}
     * code : 1
     */

    private String msg;
    private DataBean data;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * id : 180
         * userid : 341
         * coinname : ydc
         * num : 2
         * mum : 0.01
         * endtime : 1510397340
         * status : 0
         * surplus : 1
         * original_balance : 0.005
         * people : [{"mum":"0.00500000","addtime":"2017-11-10 18:49:09","nickname":"风"}]
         */

        private int id;
        private int userid;
        private String coinname;
        private int num;
        private String mum;
        private int endtime;
        private int status;
        private int surplus;
        private String original_balance;
        private List<PeopleBean> people;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCoinname() {
            return coinname;
        }

        public void setCoinname(String coinname) {
            this.coinname = coinname;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMum() {
            return mum;
        }

        public void setMum(String mum) {
            this.mum = mum;
        }

        public int getEndtime() {
            return endtime;
        }

        public void setEndtime(int endtime) {
            this.endtime = endtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSurplus() {
            return surplus;
        }

        public void setSurplus(int surplus) {
            this.surplus = surplus;
        }

        public String getOriginal_balance() {
            return original_balance;
        }

        public void setOriginal_balance(String original_balance) {
            this.original_balance = original_balance;
        }

        public List<PeopleBean> getPeople() {
            return people;
        }

        public void setPeople(List<PeopleBean> people) {
            this.people = people;
        }

        public static class PeopleBean {
            /**
             * mum : 0.00500000
             * addtime : 2017-11-10 18:49:09
             * nickname : 风
             */

            private String mum;
            private String addtime;
            private String username;

            public String getMum() {
                return mum;
            }

            public void setMum(String mum) {
                this.mum = mum;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getUsernam() {
                return username;
            }

            public void setUsernam(String username) {
                this.username = username;
            }
        }
    }
}
