package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.processing.Generated;

public class OrderListResponse implements Serializable {
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
    public class Datum implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("delivery_id")
        @Expose
        private Integer deliveryId;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("other_purity")
        @Expose
        private String otherPurity;
        @SerializedName("old_fine")
        @Expose
        private String oldFine;
        @SerializedName("old_amount")
        @Expose
        private String oldAmount;
        @SerializedName("old_bill_no")
        @Expose
        private String oldBillNo;
        @SerializedName("mode_of_payment")
        @Expose
        private String modeOfPayment;
        @SerializedName("metal_r_weight")
        @Expose
        private String metalRWeight;
        @SerializedName("metal_r_purity")
        @Expose
        private String metalRPurity;
        @SerializedName("metal_r_fine")
        @Expose
        private String metalRFine;
        @SerializedName("balance_fine")
        @Expose
        private Double balanceFine;
        @SerializedName("gold_rate")
        @Expose
        private String goldRate;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("gst_percentage")
        @Expose
        private String gstPercentage;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("received_amount")
        @Expose
        private String receivedAmount;
        @SerializedName("round_off_amount")
        @Expose
        private Double roundOffAmount;
        @SerializedName("balance_amount")
        @Expose
        private Double balanceAmount;
        @SerializedName("cheque_no")
        @Expose
        private String chequeNo;
        @SerializedName("cheque_date")
        @Expose
        private String chequeDate;
        @SerializedName("cheque_bank_name")
        @Expose
        private String chequeBankName;
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("comment_1")
        @Expose
        private String comment1;
        @SerializedName("comment_2")
        @Expose
        private String comment2;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("customer")
        @Expose
        private Customer customer;
        @SerializedName("order_product")
        @Expose
        private List<OrderProduct> orderProduct;
        @SerializedName("order_date_dmy")
        @Expose
        private String orderDateDmy;
        @SerializedName("delivery")
        @Expose
        private List<Delivery> delivery;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(Integer deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOtherPurity() {
            return otherPurity;
        }

        public void setOtherPurity(String otherPurity) {
            this.otherPurity = otherPurity;
        }

        public String getOldFine() {
            return oldFine;
        }

        public void setOldFine(String oldFine) {
            this.oldFine = oldFine;
        }

        public String getOldAmount() {
            return oldAmount;
        }

        public void setOldAmount(String oldAmount) {
            this.oldAmount = oldAmount;
        }

        public String getOldBillNo() {
            return oldBillNo;
        }

        public void setOldBillNo(String oldBillNo) {
            this.oldBillNo = oldBillNo;
        }

        public String getModeOfPayment() {
            return modeOfPayment;
        }

        public void setModeOfPayment(String modeOfPayment) {
            this.modeOfPayment = modeOfPayment;
        }

        public String getMetalRWeight() {
            return metalRWeight;
        }

        public void setMetalRWeight(String metalRWeight) {
            this.metalRWeight = metalRWeight;
        }

        public String getMetalRPurity() {
            return metalRPurity;
        }

        public void setMetalRPurity(String metalRPurity) {
            this.metalRPurity = metalRPurity;
        }

        public String getMetalRFine() {
            return metalRFine;
        }

        public void setMetalRFine(String metalRFine) {
            this.metalRFine = metalRFine;
        }

        public Double getBalanceFine() {
            return balanceFine;
        }

        public void setBalanceFine(Double balanceFine) {
            this.balanceFine = balanceFine;
        }

        public String getGoldRate() {
            return goldRate;
        }

        public void setGoldRate(String goldRate) {
            this.goldRate = goldRate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getGstPercentage() {
            return gstPercentage;
        }

        public void setGstPercentage(String gstPercentage) {
            this.gstPercentage = gstPercentage;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getReceivedAmount() {
            return receivedAmount;
        }

        public void setReceivedAmount(String receivedAmount) {
            this.receivedAmount = receivedAmount;
        }

        public Double getRoundOffAmount() {
            return roundOffAmount;
        }

        public void setRoundOffAmount(Double roundOffAmount) {
            this.roundOffAmount = roundOffAmount;
        }

        public Double getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(Double balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        public String getChequeNo() {
            return chequeNo;
        }

        public void setChequeNo(String chequeNo) {
            this.chequeNo = chequeNo;
        }

        public String getChequeDate() {
            return chequeDate;
        }

        public void setChequeDate(String chequeDate) {
            this.chequeDate = chequeDate;
        }

        public String getChequeBankName() {
            return chequeBankName;
        }

        public void setChequeBankName(String chequeBankName) {
            this.chequeBankName = chequeBankName;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getComment1() {
            return comment1;
        }

        public void setComment1(String comment1) {
            this.comment1 = comment1;
        }

        public String getComment2() {
            return comment2;
        }

        public void setComment2(String comment2) {
            this.comment2 = comment2;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public List<OrderProduct> getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(List<OrderProduct> orderProduct) {
            this.orderProduct = orderProduct;
        }

        public String getOrderDateDmy() {
            return orderDateDmy;
        }

        public void setOrderDateDmy(String orderDateDmy) {
            this.orderDateDmy = orderDateDmy;
        }

        public List<Delivery> getDelivery() {
            return delivery;
        }

        public void setDelivery(List<Delivery> delivery) {
            this.delivery = delivery;
        }

    }
    public class User implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("profile_picture")
        @Expose
        private String profilePicture;
        @SerializedName("date_of_birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("gender")
        @Expose
        private Integer gender;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("adhar_no")
        @Expose
        private String adharNo;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdharNo() {
            return adharNo;
        }

        public void setAdharNo(String adharNo) {
            this.adharNo = adharNo;
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
    public static class Customer implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("status")
        @Expose
        private Integer status;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }
    public static class OrderProduct implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("delivery_id")
        @Expose
        private Integer deliveryId;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("gross_weight")
        @Expose
        private String grossWeight;
        @SerializedName("less_weight")
        @Expose
        private String lessWeight;
        @SerializedName("purity")
        @Expose
        private String purity;
        @SerializedName("wastage")
        @Expose
        private String wastage;
        @SerializedName("fine")
        @Expose
        private String fine;
        @SerializedName("laboure_rate")
        @Expose
        private String laboureRate;
        @SerializedName("rate_on")
        @Expose
        private String rateOn;
        @SerializedName("laboure_amount")
        @Expose
        private String laboureAmount;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("product_details")
        @Expose
        private ProductDetails productDetails;

        public OrderProduct(Integer id, String purity, String less_weight, String gross_weight,
                       String quantity, String wastage, String fine, String laboure_rate,
                       String rate_on, String laboure_amount) {
            this.id = id;
            this.purity = purity;
            this.lessWeight = less_weight;
            this.grossWeight = gross_weight;
            this.quantity = quantity;
            this.wastage = wastage;
            this.fine = fine;
            this.laboureRate = laboure_rate;
            this.rateOn = rate_on;
            this.laboureAmount = laboure_amount;
        }
        public OrderProduct(Integer id, Integer productId, Integer userId, Integer deliveryId,
                            Integer orderId, String quantity, String grossWeight,
                            String lessWeight, String purity, String wastage, String fine,
                            String laboureRate, String rateOn, String laboureAmount,
                            ProductDetails productDetails) {
            this.id = id;
            this.productId = productId;
            this.userId = userId;
            this.deliveryId = deliveryId;
            this.orderId = orderId;
            this.quantity = quantity;
            this.grossWeight = grossWeight;
            this.lessWeight = lessWeight;
            this.purity = purity;
            this.wastage = wastage;
            this.fine = fine;
            this.laboureRate = laboureRate;
            this.rateOn = rateOn;
            this.laboureAmount = laboureAmount;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.productDetails = productDetails;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(Integer deliveryId) {
            this.deliveryId = deliveryId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(String grossWeight) {
            this.grossWeight = grossWeight;
        }

        public String getLessWeight() {
            return lessWeight;
        }

        public void setLessWeight(String lessWeight) {
            this.lessWeight = lessWeight;
        }

        public String getPurity() {
            return purity;
        }

        public void setPurity(String purity) {
            this.purity = purity;
        }

        public String getWastage() {
            return wastage;
        }

        public void setWastage(String wastage) {
            this.wastage = wastage;
        }

        public String getFine() {
            return fine;
        }

        public void setFine(String fine) {
            this.fine = fine;
        }

        public String getLaboureRate() {
            return laboureRate;
        }

        public void setLaboureRate(String laboureRate) {
            this.laboureRate = laboureRate;
        }

        public String getRateOn() {
            return rateOn;
        }

        public void setRateOn(String rateOn) {
            this.rateOn = rateOn;
        }

        public String getLaboureAmount() {
            return laboureAmount;
        }

        public void setLaboureAmount(String laboureAmount) {
            this.laboureAmount = laboureAmount;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public ProductDetails getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(ProductDetails productDetails) {
            this.productDetails = productDetails;
        }

    }
    public class Delivery implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("issue_date")
        @Expose
        private String issueDate;
        @SerializedName("from_location")
        @Expose
        private String fromLocation;
        @SerializedName("to_location")
        @Expose
        private String toLocation;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("issue_date_dmy")
        @Expose
        private String issueDateDmy;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public String getIssueDateDmy() {
            return issueDateDmy;
        }

        public void setIssueDateDmy(String issueDateDmy) {
            this.issueDateDmy = issueDateDmy;
        }

    }
    public  static class ProductDetails implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public ProductDetails(Integer id, String productName) {
            this.id = id;
            this.productName = productName;
        }

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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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
