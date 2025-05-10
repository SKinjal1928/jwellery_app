package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDeliveryVoucher {

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
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("issue_date_formatted")
        @Expose
        private String issueDateFormatted;
        @SerializedName("issue_date")
        @Expose
        private String issueDate;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("from_location")
        @Expose
        private String fromLocation;
        @SerializedName("to_location")
        @Expose
        private String toLocation;
        @SerializedName("salesman_name")
        @Expose
        private String salesmanName;
        @SerializedName("cash")
        @Expose
        private String cash;
        @SerializedName("salesman_address")
        @Expose
        private String salesmanAddress;
        @SerializedName("total_NW")
        @Expose
        private String totalNW;
        @SerializedName("total_quantity")
        @Expose
        private Integer totalQuantity;
        @SerializedName("salesman_adhar")
        @Expose
        private String salesmanAdhar;
        @SerializedName("salesman_profile_photo")
        @Expose
        private String salesmanProfilePhoto;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getIssueDateFormatted() {
            return issueDateFormatted;
        }

        public void setIssueDateFormatted(String issueDateFormatted) {
            this.issueDateFormatted = issueDateFormatted;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFromLocation() {
            return fromLocation;
        }

        public void setFromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
        }

        public String getToLocation() {
            return toLocation;
        }

        public void setToLocation(String toLocation) {
            this.toLocation = toLocation;
        }

        public String getSalesmanName() {
            return salesmanName;
        }

        public void setSalesmanName(String salesmanName) {
            this.salesmanName = salesmanName;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getSalesmanAddress() {
            return salesmanAddress;
        }

        public void setSalesmanAddress(String salesmanAddress) {
            this.salesmanAddress = salesmanAddress;
        }

        public String getTotalNW() {
            return totalNW;
        }

        public void setTotalNW(String totalNW) {
            this.totalNW = totalNW;
        }

        public Integer getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(Integer totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public String getSalesmanAdhar() {
            return salesmanAdhar;
        }

        public void setSalesmanAdhar(String salesmanAdhar) {
            this.salesmanAdhar = salesmanAdhar;
        }

        public String getSalesmanProfilePhoto() {
            return salesmanProfilePhoto;
        }

        public void setSalesmanProfilePhoto(String salesmanProfilePhoto) {
            this.salesmanProfilePhoto = salesmanProfilePhoto;
        }

    }

}
