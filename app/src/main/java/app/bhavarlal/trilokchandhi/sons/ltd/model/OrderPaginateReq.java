package app.bhavarlal.trilokchandhi.sons.ltd.model;

public class OrderPaginateReq {
    String user_id, today_date, start_date, end_date;

    int page, limit;
    public OrderPaginateReq(String user_id, String today_date,
                            String start_date, String end_date, int page, int limit) {
        this.user_id = user_id;
        this.today_date = today_date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.page = page;
        this.limit = limit;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToday_date() {
        return today_date;
    }

    public void setToday_date(String today_date) {
        this.today_date = today_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
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
