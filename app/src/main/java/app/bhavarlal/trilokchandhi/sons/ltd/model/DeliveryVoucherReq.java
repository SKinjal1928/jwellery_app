package app.bhavarlal.trilokchandhi.sons.ltd.model;

public class DeliveryVoucherReq {
    String type, user_id;

    public DeliveryVoucherReq(String type, String user_id) {
        this.type = type;
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
