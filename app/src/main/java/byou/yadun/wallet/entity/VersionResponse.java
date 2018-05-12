package byou.yadun.wallet.entity;

/**
 *
 */

public class VersionResponse {
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
        private String version;
        private String download_address;
        private String content;
        private int is_upgrade;
        private int client;
        private String version_name;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDownload_address() {
            return download_address;
        }

        public void setDownload_address(String download_address) {
            this.download_address = download_address;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_upgrade() {
            return is_upgrade;
        }

        public void setIs_upgrade(int is_upgrade) {
            this.is_upgrade = is_upgrade;
        }

        public int getClient() {
            return client;
        }

        public void setClient(int client) {
            this.client = client;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }
    }
}
