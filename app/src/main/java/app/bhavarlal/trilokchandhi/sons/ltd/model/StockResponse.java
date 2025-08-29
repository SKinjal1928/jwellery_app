package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockResponse {

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

        @SerializedName("product_id")
        @Expose
        private Integer product_id;

        @SerializedName("productName")
        @Expose
        private String productName;

        @SerializedName("purity")
        @Expose
        private String purity;

        @SerializedName("finalQuantity")
        @Expose
        private Integer finalQuantity;

        @SerializedName("finalGrossWeight")
        @Expose
        private Double finalGrossWeight;

        @SerializedName("finalLessWeight")
        @Expose
        private Double finalLessWeight;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPurity() {
            return purity;
        }

        public void setPurity(String purity) {
            this.purity = purity;
        }

        public Integer getFinalQuantity() {
            return finalQuantity;
        }

        public void setFinalQuantity(Integer finalQuantity) {
            this.finalQuantity = finalQuantity;
        }

        public Double getFinalGrossWeight() {
            return finalGrossWeight;
        }

        public void setFinalGrossWeight(Double finalGrossWeight) {
            this.finalGrossWeight = finalGrossWeight;
        }

        public Double getFinalLessWeight() {
            return finalLessWeight;
        }

        public void setFinalLessWeight(Double finalLessWeight) {
            this.finalLessWeight = finalLessWeight;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getProduct_id() {
            return product_id;
        }

        public void setProduct_id(Integer product_id) {
            this.product_id = product_id;
        }

        @Override
        public String toString() {
            return productName; // This is what gets shown in the dropdown
        }
    }


}
