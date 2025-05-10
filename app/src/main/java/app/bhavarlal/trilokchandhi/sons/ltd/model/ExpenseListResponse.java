package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpenseListResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("delivery_id")
        @Expose
        private Integer deliveryId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("name_place")
        @Expose
        private String namePlace;
        @SerializedName("lodging")
        @Expose
        private String lodging;
        @SerializedName("travelling")
        @Expose
        private String travelling;
        @SerializedName("food")
        @Expose
        private String food;
        @SerializedName("other")
        @Expose
        private String other;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(Integer deliveryId) {
            this.deliveryId = deliveryId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNamePlace() {
            return namePlace;
        }

        public void setNamePlace(String namePlace) {
            this.namePlace = namePlace;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
