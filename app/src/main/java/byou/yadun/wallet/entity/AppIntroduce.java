package byou.yadun.wallet.entity;

/**
 *
 */

public class AppIntroduce {
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
        private int id;
        private String web_name;
        private String web_title;
        private String web_icp;
        private int web_state;
        private String about_app;
        private String pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWeb_name() {
            return web_name;
        }

        public void setWeb_name(String web_name) {
            this.web_name = web_name;
        }

        public String getWeb_title() {
            return web_title;
        }

        public void setWeb_title(String web_title) {
            this.web_title = web_title;
        }

        public String getWeb_icp() {
            return web_icp;
        }

        public void setWeb_icp(String web_icp) {
            this.web_icp = web_icp;
        }

        public int getWeb_state() {
            return web_state;
        }

        public void setWeb_state(int web_state) {
            this.web_state = web_state;
        }

        public String getAbout_app() {
            return about_app;
        }

        public void setAbout_app(String about_app) {
            this.about_app = about_app;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
