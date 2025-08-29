package app.bhavarlal.trilokchandhi.sons.ltd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpensePaginateResponse {
    public class Data {

        @SerializedName("list")
        @Expose
        private java.util.List<List> list;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("pages")
        @Expose
        private Integer pages;
        @SerializedName("page")
        @Expose
        private Integer page;

        public java.util.List<List> getList() {
            return list;
        }

        public void setList(java.util.List<List> list) {
            this.list = list;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

    }
    public class Expense {

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
        @SerializedName("date_dmy")
        @Expose
        private String dateDmy;
        @SerializedName("lodging_comment")
        @Expose
        private Object lodgingComment;
        @SerializedName("travelling_comment")
        @Expose
        private Object travellingComment;
        @SerializedName("other_comment")
        @Expose
        private Object otherComment;

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

        public String getDateDmy() {
            return dateDmy;
        }

        public void setDateDmy(String dateDmy) {
            this.dateDmy = dateDmy;
        }

        public Object getLodgingComment() {
            return lodgingComment;
        }

        public void setLodgingComment(Object lodgingComment) {
            this.lodgingComment = lodgingComment;
        }

        public Object getTravellingComment() {
            return travellingComment;
        }

        public void setTravellingComment(Object travellingComment) {
            this.travellingComment = travellingComment;
        }

        public Object getOtherComment() {
            return otherComment;
        }

        public void setOtherComment(Object otherComment) {
            this.otherComment = otherComment;
        }

    }

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public class List {

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
        @SerializedName("travelling_date")
        @Expose
        private String travellingDate;
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
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("issue_date_dmy")
        @Expose
        private String issueDateDmy;
        @SerializedName("travelling_date_dmy")
        @Expose
        private String travellingDateDmy;
        @SerializedName("expense")
        @Expose
        private Expense expense;

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

        public String getTravellingDate() {
            return travellingDate;
        }

        public void setTravellingDate(String travellingDate) {
            this.travellingDate = travellingDate;
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getIssueDateDmy() {
            return issueDateDmy;
        }

        public void setIssueDateDmy(String issueDateDmy) {
            this.issueDateDmy = issueDateDmy;
        }

        public String getTravellingDateDmy() {
            return travellingDateDmy;
        }

        public void setTravellingDateDmy(String travellingDateDmy) {
            this.travellingDateDmy = travellingDateDmy;
        }

        public Expense getExpense() {
            return expense;
        }

        public void setExpense(Expense expense) {
            this.expense = expense;
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

}
