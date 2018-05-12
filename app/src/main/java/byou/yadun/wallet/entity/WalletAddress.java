package byou.yadun.wallet.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class WalletAddress {
    private String msg;
    private DataBeanX data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBeanX {

        private String xnb;
        private CoinListBean coin_list;
        private UserQianbaoListBean userQianbaoList;

        public String getXnb() {
            return xnb;
        }

        public void setXnb(String xnb) {
            this.xnb = xnb;
        }

        public CoinListBean getCoin_list() {
            return coin_list;
        }

        public void setCoin_list(CoinListBean coin_list) {
            this.coin_list = coin_list;
        }

        public UserQianbaoListBean getUserQianbaoList() {
            return userQianbaoList;
        }

        public void setUserQianbaoList(UserQianbaoListBean userQianbaoList) {
            this.userQianbaoList = userQianbaoList;
        }

        public static class CoinListBean {
            private AecBean aec;
            private BtcBean btc;
            private YdcBean ydc;
            private LtcBean ltc;

            public AecBean getAec() {
                return aec;
            }

            public void setAec(AecBean aec) {
                this.aec = aec;
            }

            public BtcBean getBtc() {
                return btc;
            }

            public void setBtc(BtcBean btc) {
                this.btc = btc;
            }

            public YdcBean getYdc() {
                return ydc;
            }

            public void setYdc(YdcBean ydc) {
                this.ydc = ydc;
            }

            public LtcBean getLtc() {
                return ltc;
            }

            public void setLtc(LtcBean ltc) {
                this.ltc = ltc;
            }

            public static class AecBean {
                private int id;
                private String name;
                private String title;
                private String img;
                private String dj_zj;
                private String dj_yh;
                private String dj_mm;
                private String dj_dk;
                private int status;
                private String type;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getDj_zj() {
                    return dj_zj;
                }

                public void setDj_zj(String dj_zj) {
                    this.dj_zj = dj_zj;
                }

                public String getDj_yh() {
                    return dj_yh;
                }

                public void setDj_yh(String dj_yh) {
                    this.dj_yh = dj_yh;
                }

                public String getDj_mm() {
                    return dj_mm;
                }

                public void setDj_mm(String dj_mm) {
                    this.dj_mm = dj_mm;
                }

                public String getDj_dk() {
                    return dj_dk;
                }

                public void setDj_dk(String dj_dk) {
                    this.dj_dk = dj_dk;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class BtcBean {
                private int id;
                private String name;
                private String title;
                private String img;
                private String dj_zj;
                private String dj_yh;
                private String dj_mm;
                private String dj_dk;
                private int status;
                private String type;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getDj_zj() {
                    return dj_zj;
                }

                public void setDj_zj(String dj_zj) {
                    this.dj_zj = dj_zj;
                }

                public String getDj_yh() {
                    return dj_yh;
                }

                public void setDj_yh(String dj_yh) {
                    this.dj_yh = dj_yh;
                }

                public String getDj_mm() {
                    return dj_mm;
                }

                public void setDj_mm(String dj_mm) {
                    this.dj_mm = dj_mm;
                }

                public String getDj_dk() {
                    return dj_dk;
                }

                public void setDj_dk(String dj_dk) {
                    this.dj_dk = dj_dk;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class YdcBean {
                private int id;
                private String name;
                private String title;
                private String img;
                private String dj_zj;
                private String dj_yh;
                private String dj_mm;
                private String dj_dk;
                private int status;
                private String type;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getDj_zj() {
                    return dj_zj;
                }

                public void setDj_zj(String dj_zj) {
                    this.dj_zj = dj_zj;
                }

                public String getDj_yh() {
                    return dj_yh;
                }

                public void setDj_yh(String dj_yh) {
                    this.dj_yh = dj_yh;
                }

                public String getDj_mm() {
                    return dj_mm;
                }

                public void setDj_mm(String dj_mm) {
                    this.dj_mm = dj_mm;
                }

                public String getDj_dk() {
                    return dj_dk;
                }

                public void setDj_dk(String dj_dk) {
                    this.dj_dk = dj_dk;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class LtcBean {

                private int id;
                private String name;
                private String title;
                private String img;
                private String dj_zj;
                private String dj_yh;
                private String dj_mm;
                private String dj_dk;
                private int status;
                private String type;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

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

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getDj_zj() {
                    return dj_zj;
                }

                public void setDj_zj(String dj_zj) {
                    this.dj_zj = dj_zj;
                }

                public String getDj_yh() {
                    return dj_yh;
                }

                public void setDj_yh(String dj_yh) {
                    this.dj_yh = dj_yh;
                }

                public String getDj_mm() {
                    return dj_mm;
                }

                public void setDj_mm(String dj_mm) {
                    this.dj_mm = dj_mm;
                }

                public String getDj_dk() {
                    return dj_dk;
                }

                public void setDj_dk(String dj_dk) {
                    this.dj_dk = dj_dk;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class UserQianbaoListBean {
            private int total;
            private int per_page;
            private int current_page;
            private List<DataBean> data;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPer_page() {
                return per_page;
            }

            public void setPer_page(int per_page) {
                this.per_page = per_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean implements Serializable{
                private String id;
                private int userid;
                private String coinname;
                private String name;
                private String addr;
                private int sort;
                private String addtime;
                private int endtime;
                private int status;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAddr() {
                    return addr;
                }

                public void setAddr(String addr) {
                    this.addr = addr;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getAddtime() {
                    return addtime;
                }

                public void setAddtime(String addtime) {
                    this.addtime = addtime;
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

                @Override
                public String toString() {
                    return "DataBean{" +
                            "id='" + id + '\'' +
                            ", userid=" + userid +
                            ", coinname='" + coinname + '\'' +
                            ", name='" + name + '\'' +
                            ", addr='" + addr + '\'' +
                            ", sort=" + sort +
                            ", addtime='" + addtime + '\'' +
                            ", endtime=" + endtime +
                            ", status=" + status +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "UserQianbaoListBean{" +
                        "total=" + total +
                        ", per_page=" + per_page +
                        ", current_page=" + current_page +
                        ", data=" + data +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBeanX{" +
                    "xnb='" + xnb + '\'' +
                    ", coin_list=" + coin_list +
                    ", userQianbaoList=" + userQianbaoList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WalletAddress{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", code=" + code +
                '}';
    }
}
