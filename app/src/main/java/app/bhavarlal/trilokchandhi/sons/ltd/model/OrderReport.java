package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderReport {
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
    public class ProductDetails {

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
    public class User {

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
        private Object profilePicture;
        @SerializedName("date_of_birth")
        @Expose
        private Object dateOfBirth;
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

        public Object getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(Object profilePicture) {
            this.profilePicture = profilePicture;
        }

        public Object getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Object dateOfBirth) {
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
    public class OrderProduct {

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
    public class Delivery {

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
    public class Data {

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
        private Object oldFine;
        @SerializedName("old_amount")
        @Expose
        private Object oldAmount;
        @SerializedName("old_bill_no")
        @Expose
        private Object oldBillNo;
        @SerializedName("mode_of_payment")
        @Expose
        private String modeOfPayment;
        @SerializedName("metal_r_weight")
        @Expose
        private Object metalRWeight;
        @SerializedName("metal_r_purity")
        @Expose
        private Object metalRPurity;
        @SerializedName("metal_r_fine")
        @Expose
        private Object metalRFine;
        @SerializedName("balance_fine")
        @Expose
        private Object balanceFine;
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
        private Object roundOffAmount;
        @SerializedName("balance_amount")
        @Expose
        private String balanceAmount;
        @SerializedName("cheque_no")
        @Expose
        private Object chequeNo;
        @SerializedName("cheque_date")
        @Expose
        private Object chequeDate;
        @SerializedName("cheque_bank_name")
        @Expose
        private Object chequeBankName;
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

        public Object getOldFine() {
            return oldFine;
        }

        public void setOldFine(Object oldFine) {
            this.oldFine = oldFine;
        }

        public Object getOldAmount() {
            return oldAmount;
        }

        public void setOldAmount(Object oldAmount) {
            this.oldAmount = oldAmount;
        }

        public Object getOldBillNo() {
            return oldBillNo;
        }

        public void setOldBillNo(Object oldBillNo) {
            this.oldBillNo = oldBillNo;
        }

        public String getModeOfPayment() {
            return modeOfPayment;
        }

        public void setModeOfPayment(String modeOfPayment) {
            this.modeOfPayment = modeOfPayment;
        }

        public Object getMetalRWeight() {
            return metalRWeight;
        }

        public void setMetalRWeight(Object metalRWeight) {
            this.metalRWeight = metalRWeight;
        }

        public Object getMetalRPurity() {
            return metalRPurity;
        }

        public void setMetalRPurity(Object metalRPurity) {
            this.metalRPurity = metalRPurity;
        }

        public Object getMetalRFine() {
            return metalRFine;
        }

        public void setMetalRFine(Object metalRFine) {
            this.metalRFine = metalRFine;
        }

        public Object getBalanceFine() {
            return balanceFine;
        }

        public void setBalanceFine(Object balanceFine) {
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

        public Object getRoundOffAmount() {
            return roundOffAmount;
        }

        public void setRoundOffAmount(Object roundOffAmount) {
            this.roundOffAmount = roundOffAmount;
        }

        public String getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(String balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        public Object getChequeNo() {
            return chequeNo;
        }

        public void setChequeNo(Object chequeNo) {
            this.chequeNo = chequeNo;
        }

        public Object getChequeDate() {
            return chequeDate;
        }

        public void setChequeDate(Object chequeDate) {
            this.chequeDate = chequeDate;
        }

        public Object getChequeBankName() {
            return chequeBankName;
        }

        public void setChequeBankName(Object chequeBankName) {
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
    public class Customer {

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


}
