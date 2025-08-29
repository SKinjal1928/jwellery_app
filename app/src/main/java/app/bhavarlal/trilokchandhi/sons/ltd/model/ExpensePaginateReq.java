package app.bhavarlal.trilokchandhi.sons.ltd.model;

public class ExpensePaginateReq {
    String delivery_id, user_id;
    int page, limit;

    public ExpensePaginateReq(String delivery_id, String user_id, int page, int limit) {
        this.delivery_id = delivery_id;
        this.user_id = user_id;
        this.page = page;
        this.limit = limit;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
