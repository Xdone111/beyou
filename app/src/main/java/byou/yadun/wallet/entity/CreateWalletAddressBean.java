package byou.yadun.wallet.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class CreateWalletAddressBean {

    /**
     * msg : ok
     * data : {"xnb":"ydc","coin_list":{"bec":{"id":45,"name":"bec","type":"qbb","title":"BEC","status":1},"ydc":{"id":53,"name":"ydc","type":"qbb","title":"亚盾链","status":1},"tdc":{"id":50,"name":"tdc","type":"qbb","title":"天地币","status":1}},"user_coin":{"balance":0,"wallet_address":""},"zr_jz":"1","qianbao":"YSEzKBbVPwsx8NAfn96gfNT8oup1SA4ySM","unsure":0,"transaction":0,"list":{"total":0,"per_page":10,"current_page":1,"data":[]},"list_m":{"total":0,"per_page":7,"current_page":1,"data":[]},"list_w":{"total":0,"per_page":7,"current_page":1,"data":[]},"list_t":{"total":0,"per_page":7,"current_page":1,"data":[]}}
     * code : 1
     */

    private String msg;
    private DataBean data;
    private int code;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * xnb : ydc
         * coin_list : {"bec":{"id":45,"name":"bec","type":"qbb","title":"BEC","status":1},"ydc":{"id":53,"name":"ydc","type":"qbb","title":"亚盾链","status":1},"tdc":{"id":50,"name":"tdc","type":"qbb","title":"天地币","status":1}}
         * user_coin : {"balance":0,"wallet_address":""}
         * zr_jz : 1
         * qianbao : YSEzKBbVPwsx8NAfn96gfNT8oup1SA4ySM
         * unsure : 0
         * transaction : 0
         * list : {"total":0,"per_page":10,"current_page":1,"data":[]}
         * list_m : {"total":0,"per_page":7,"current_page":1,"data":[]}
         * list_w : {"total":0,"per_page":7,"current_page":1,"data":[]}
         * list_t : {"total":0,"per_page":7,"current_page":1,"data":[]}
         */

        private String xnb;
        private CoinListBean coin_list;
        private UserCoinBean user_coin;
        private String zr_jz;
        private String qianbao;
        private int unsure;
        private int transaction;
        private ListBean list;
        private ListMBean list_m;
        private ListWBean list_w;
        private ListTBean list_t;

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

        public UserCoinBean getUser_coin() {
            return user_coin;
        }

        public void setUser_coin(UserCoinBean user_coin) {
            this.user_coin = user_coin;
        }

        public String getZr_jz() {
            return zr_jz;
        }

        public void setZr_jz(String zr_jz) {
            this.zr_jz = zr_jz;
        }

        public String getQianbao() {
            return qianbao;
        }

        public void setQianbao(String qianbao) {
            this.qianbao = qianbao;
        }

        public int getUnsure() {
            return unsure;
        }

        public void setUnsure(int unsure) {
            this.unsure = unsure;
        }

        public int getTransaction() {
            return transaction;
        }

        public void setTransaction(int transaction) {
            this.transaction = transaction;
        }

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public ListMBean getList_m() {
            return list_m;
        }

        public void setList_m(ListMBean list_m) {
            this.list_m = list_m;
        }

        public ListWBean getList_w() {
            return list_w;
        }

        public void setList_w(ListWBean list_w) {
            this.list_w = list_w;
        }

        public ListTBean getList_t() {
            return list_t;
        }

        public void setList_t(ListTBean list_t) {
            this.list_t = list_t;
        }

        public static class CoinListBean {
            /**
             * bec : {"id":45,"name":"bec","type":"qbb","title":"BEC","status":1}
             * ydc : {"id":53,"name":"ydc","type":"qbb","title":"亚盾链","status":1}
             * tdc : {"id":50,"name":"tdc","type":"qbb","title":"天地币","status":1}
             */

            private BecBean bec;
            private YdcBean ydc;
            private TdcBean tdc;

            public BecBean getBec() {
                return bec;
            }

            public void setBec(BecBean bec) {
                this.bec = bec;
            }

            public YdcBean getYdc() {
                return ydc;
            }

            public void setYdc(YdcBean ydc) {
                this.ydc = ydc;
            }

            public TdcBean getTdc() {
                return tdc;
            }

            public void setTdc(TdcBean tdc) {
                this.tdc = tdc;
            }

            public static class BecBean {
                /**
                 * id : 45
                 * name : bec
                 * type : qbb
                 * title : BEC
                 * status : 1
                 */

                private int id;
                private String name;
                private String type;
                private String title;
                private int status;

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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }

            public static class YdcBean {
                /**
                 * id : 53
                 * name : ydc
                 * type : qbb
                 * title : 亚盾链
                 * status : 1
                 */

                private int id;
                private String name;
                private String type;
                private String title;
                private int status;

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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }

            public static class TdcBean {
                /**
                 * id : 50
                 * name : tdc
                 * type : qbb
                 * title : 天地币
                 * status : 1
                 */

                private int id;
                private String name;
                private String type;
                private String title;
                private int status;

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

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }

        public static class UserCoinBean {
            /**
             * balance : 0
             * wallet_address :
             */

            private int balance;
            private String wallet_address;

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public String getWallet_address() {
                return wallet_address;
            }

            public void setWallet_address(String wallet_address) {
                this.wallet_address = wallet_address;
            }
        }

        public static class ListBean {
            /**
             * total : 0
             * per_page : 10
             * current_page : 1
             * data : []
             */

            private int total;
            private int per_page;
            private int current_page;
            private List<?> data;

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

            public List<?> getData() {
                return data;
            }

            public void setData(List<?> data) {
                this.data = data;
            }
        }

        public static class ListMBean {
            /**
             * total : 0
             * per_page : 7
             * current_page : 1
             * data : []
             */

            private int total;
            private int per_page;
            private int current_page;
            private List<?> data;

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

            public List<?> getData() {
                return data;
            }

            public void setData(List<?> data) {
                this.data = data;
            }
        }

        public static class ListWBean {
            /**
             * total : 0
             * per_page : 7
             * current_page : 1
             * data : []
             */

            private int total;
            private int per_page;
            private int current_page;
            private List<?> data;

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

            public List<?> getData() {
                return data;
            }

            public void setData(List<?> data) {
                this.data = data;
            }
        }

        public static class ListTBean {
            /**
             * total : 0
             * per_page : 7
             * current_page : 1
             * data : []
             */

            private int total;
            private int per_page;
            private int current_page;
            private List<?> data;

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

            public List<?> getData() {
                return data;
            }

            public void setData(List<?> data) {
                this.data = data;
            }
        }
    }
}
