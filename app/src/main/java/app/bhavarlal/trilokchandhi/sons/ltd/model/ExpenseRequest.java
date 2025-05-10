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

        // constructor, getters, setters

        public ExpenseItem(String namePlace, String date, String lodging, String travelling, String food, String other) {
            this.namePlace = namePlace;
            this.date = date;
            this.lodging = lodging;
            this.travelling = travelling;
            this.food = food;
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
