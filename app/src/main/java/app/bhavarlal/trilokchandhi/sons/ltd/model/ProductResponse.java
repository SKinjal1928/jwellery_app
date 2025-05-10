package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {

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
    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("less_weight")
        @Expose
        private String lessWeight;
        @SerializedName("gross_weight")
        @Expose
        private String grossWeight;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("purity")
        @Expose
        private String purity;
        @SerializedName("category_type")
        @Expose
        private Integer categoryType;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_by")
        @Expose
        private Integer updatedBy;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getLessWeight() {
            return lessWeight;
        }

        public void setLessWeight(String lessWeight) {
            this.lessWeight = lessWeight;
        }

        public String getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(String grossWeight) {
            this.grossWeight = grossWeight;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getPurity() {
            return purity;
        }

        public void setPurity(String purity) {
            this.purity = purity;
        }

        public Integer getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(Integer categoryType) {
            this.categoryType = categoryType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Integer updatedBy) {
            this.updatedBy = updatedBy;
        }

    }
}
