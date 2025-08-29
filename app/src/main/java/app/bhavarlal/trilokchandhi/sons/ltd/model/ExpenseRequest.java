package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseRequest {
    @SerializedName("delivery_id")
    int deliveryId;
    @SerializedName("user_id")
    int userId;
    List<ExpenseItem> expense;

    public ExpenseRequest(int deliveryId, int userId, List<ExpenseItem> expense) {
        this.deliveryId = deliveryId;
        this.userId = userId;
        this.expense = expense;
    }
    public static class ExpenseItem {
        @SerializedName("name_place")
        String namePlace;
        @SerializedName("date")
        String date;
        @SerializedName("lodging")
        String lodging;
        @SerializedName("travelling")
        String travelling;
        @SerializedName("food")
        String food;
        @SerializedName("other")
        String other;
        @SerializedName("lodging_comment")
        String lodging_comment;
        @SerializedName("travelling_comment")
        String travelling_comment ;
        @SerializedName("other_comment")
        String other_comment  ;

        public String getLodging_comment() {
            return lodging_comment;
        }

        public void setLodging_comment(String lodging_comment) {
            this.lodging_comment = lodging_comment;
        }

        public String getTravelling_comment() {
            return travelling_comment;
        }

        public void setTravelling_comment(String travelling_comment) {
            this.travelling_comment = travelling_comment;
        }

        public String getOther_comment() {
            return other_comment;
        }

        public void setOther_comment(String other_comment) {
            this.other_comment = other_comment;
        }
// constructor, getters, setters

        public ExpenseItem(String namePlace, String date, String lodging, String travelling,
                           String food, String other, String lodge_comment, String travel_comment,
                           String other_comment) {
            this.namePlace = namePlace;
            this.date = date;
            this.lodging = lodging;
            this.travelling = travelling;
            this.food = food;
            this.lodging_comment = lodge_comment;
            this.travelling_comment = travel_comment;
            this.other_comment= other_comment;
            this.other = other;
        }

        public String getNamePlace() {
            return namePlace;
        }

        public void setNamePlace(String namePlace) {
            this.namePlace = namePlace;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLodging() {
            return lodging;
        }

        public void setLodging(String lodging) {
            this.lodging = lodging;
        }

        public String getTravelling() {
            return travelling;
        }

        public void setTravelling(String travelling) {
            this.travelling = travelling;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ExpenseItem> getExpense() {
        return expense;
    }

    public void setExpense(List<ExpenseItem> expense) {
        this.expense = expense;
    }

    public static class ExpenseItemUpdate {
        @SerializedName("name_place")
        String namePlace;
        @SerializedName("id")
        String id;
        @SerializedName("date")
        String date;
        @SerializedName("lodging")
        String lodging;
        @SerializedName("travelling")
        String travelling;
        @SerializedName("food")
        String food;
        @SerializedName("other")
        String other;

        // constructor, getters, setters

        public ExpenseItemUpdate(String id, String namePlace, String date, String lodging, String travelling,
                                 String food, String other) {
            this.namePlace = namePlace;
            this.date = date;
            this.lodging = lodging;
            this.travelling = travelling;
            this.food = food;
            this.other = other;
            this.id = id;
        }

        public String getNamePlace() {
            return namePlace;
        }

        public void setNamePlace(String namePlace) {
            this.namePlace = namePlace;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLodging() {
            return lodging;
        }

        public void setLodging(String lodging) {
            this.lodging = lodging;
        }

        public String getTravelling() {
            return travelling;
        }

        public void setTravelling(String travelling) {
            this.travelling = travelling;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
