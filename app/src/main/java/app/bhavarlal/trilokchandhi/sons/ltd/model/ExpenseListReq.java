package app.bhavarlal.trilokchandhi.sons.ltd.model;

public class ExpenseListReq {
    String delivery_id, user_id;

    public ExpenseListReq(String delivery_id, String user_id) {
        this.delivery_id = delivery_id;
        this.user_id = user_id;
    }

    public String getdelivery_id() {
        return delivery_id;
    }

    public void setdelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
