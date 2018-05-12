package byou.yadun.wallet.entity;

/**
 * Created by Android on 2017/8/7.
 */

public class CoinTypeBean {


    /**
     * name : btc
     * title : 比特币
     * type : rgb
     * id : 44
     */

    private String name;
    private String title;
    private String type;
    private String id;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
