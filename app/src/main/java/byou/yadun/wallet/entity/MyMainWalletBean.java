package byou.yadun.wallet.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class MyMainWalletBean {
    @Override
    public String toString() {
        return "MyMainWalletBean{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                '}';
    }

    /**
     * msg : 成功！
     * data : {"allcoin":27.4472056,"coin":[{"name":"bec","title":"BEC","id":45,"img":"http://api.ydchain.cc/uploads/picture/20171106/2d09322f20abfbb1c8237e1dd67800a1.png","longyin":"2.270000","canuse":"5.96","dongjie":"0"},{"name":"ydc","title":"亚盾链","id":53,"img":"http://api.ydchain.cc/uploads/picture/20171106/c3a01869df07fdb0f8a698aa68e33be1.png","longyin":"1.200000","canuse":"11.598338","dongjie":"0"},{"name":"htc","title":"悍游链","id":57,"img":"http://api.ydchain.cc/uploads/picture/20171106/eb9416d7ac173037eca3d4565c2d367c.png","longyin":"0.500000","canuse":"0","dongjie":"0"},{"name":"btc","title":"比特币","id":59,"img":"http://api.ydchain.cc/uploads/picture/20171106/d5f8c76744f6c164b0f0cb2cc390c994.png","longyin":"9999.999999","canuse":"0","dongjie":"0"},{"name":"ltc","title":"莱特币","id":60,"img":"http://api.ydchain.cc/uploads/picture/20171106/9778e95f5f35e562a1466b935be253f4.png","longyin":"300.000000","canuse":"0","dongjie":"0"}]}
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "allcoin=" + allcoin +
                    ", coin=" + coin +
                    '}';
        }

        /**
         * allcoin : 27.4472056
         * coin : [{"name":"bec","title":"BEC","id":45,"img":"http://api.ydchain.cc/uploads/picture/20171106/2d09322f20abfbb1c8237e1dd67800a1.png","longyin":"2.270000","canuse":"5.96","dongjie":"0"},{"name":"ydc","title":"亚盾链","id":53,"img":"http://api.ydchain.cc/uploads/picture/20171106/c3a01869df07fdb0f8a698aa68e33be1.png","longyin":"1.200000","canuse":"11.598338","dongjie":"0"},{"name":"htc","title":"悍游链","id":57,"img":"http://api.ydchain.cc/uploads/picture/20171106/eb9416d7ac173037eca3d4565c2d367c.png","longyin":"0.500000","canuse":"0","dongjie":"0"},{"name":"btc","title":"比特币","id":59,"img":"http://api.ydchain.cc/uploads/picture/20171106/d5f8c76744f6c164b0f0cb2cc390c994.png","longyin":"9999.999999","canuse":"0","dongjie":"0"},{"name":"ltc","title":"莱特币","id":60,"img":"http://api.ydchain.cc/uploads/picture/20171106/9778e95f5f35e562a1466b935be253f4.png","longyin":"300.000000","canuse":"0","dongjie":"0"}]
         */

        private double allcoin;
        private List<CoinBean> coin;

        public double getAllcoin() {
            return allcoin;
        }

        public void setAllcoin(double allcoin) {
            this.allcoin = allcoin;
        }

        public List<CoinBean> getCoin() {
            return coin;
        }

        public void setCoin(List<CoinBean> coin) {
            this.coin = coin;
        }

        public static class CoinBean {
            @Override
            public String toString() {
                return "CoinBean{" +
                        "name='" + name + '\'' +
                        ", title='" + title + '\'' +
                        ", id=" + id +
                        ", img='" + img + '\'' +
                        ", longyin='" + longyin + '\'' +
                        ", canuse='" + canuse + '\'' +
                        ", dongjie='" + dongjie + '\'' +
                        '}';
            }

            /**
             * name : bec
             * title : BEC
             * id : 45
             * img : http://api.ydchain.cc/uploads/picture/20171106/2d09322f20abfbb1c8237e1dd67800a1.png
             * longyin : 2.270000
             * canuse : 5.96
             * dongjie : 0
             */

            private String name;
            private String title;
            private int id;
            private String img;
            private String longyin;
            private String canuse;
            private String dongjie;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLongyin() {
                return longyin;
            }

            public void setLongyin(String longyin) {
                this.longyin = longyin;
            }

            public String getCanuse() {
                return canuse;
            }

            public void setCanuse(String canuse) {
                this.canuse = canuse;
            }

            public String getDongjie() {
                return dongjie;
            }

            public void setDongjie(String dongjie) {
                this.dongjie = dongjie;
            }
        }
    }
}
