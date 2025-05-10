package app.bhavarlal.trilokchandhi.sons.ltd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class OrderGenerateReq implements Serializable {
    public String user_id = "";
    public String delivery_id = "";
    public String customer_id = "";
    public String order_date = "";
    public double old_fine = 0.0;
    public double old_amount = 0.0;
    public String old_bill_no = "";
    public double metal_r_weight = 0.0;
    public double metal_r_purity = 0.0;
    public double metal_r_fine = 0.0;
    public double balance_fine = 0.0;
    public double amount = 0;
    public double gst_percentage = 0;
    public double received_amount = 0;
    public double round_off_amount = 0.0;
    public double balance_amount = 0;
    public String cheque_no = "";
    public String cheque_date = "";
    public String cheque_bank_name = "";
    public String comment_1 = "";
    public String comment_2 = "";
    public double other_purity = 0.0;
    public String mode_of_payment = "";
    public double gold_rate = 0;
    public double total_amount = 0;
    public List<Product> product;

    public OrderGenerateReq(String user_id, String delivery_id, String customer_id, String order_date,
                            double old_fine, double old_amount, String old_bill_no,
                            double metal_r_weight, double metal_r_purity, double metal_r_fine,
                            double balance_fine, double amount, double gst_percentage,
                            double received_amount, double round_off_amount, double balance_amount,
                            String cheque_no, String cheque_date, String cheque_bank_name,
                            String comment_1, String comment_2, double other_purity, String mode_of_payment,
                            double gold_rate, double total_amount, List<Product> product) {
        this.user_id = user_id;
        this.delivery_id = delivery_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.old_fine = old_fine;
        this.old_amount = old_amount;
        this.old_bill_no = old_bill_no;
        this.metal_r_weight = metal_r_weight;
        this.metal_r_purity = metal_r_purity;
        this.metal_r_fine = metal_r_fine;
        this.balance_fine = balance_fine;
        this.amount = amount;
        this.gst_percentage = gst_percentage;
        this.received_amount = received_amount;
        this.round_off_amount = round_off_amount;
        this.balance_amount = balance_amount;
        this.cheque_no = cheque_no;
        this.cheque_date = cheque_date;
        this.cheque_bank_name = cheque_bank_name;
        this.comment_1 = comment_1;
        this.comment_2 = comment_2;
        this.other_purity = other_purity;
        this.mode_of_payment = mode_of_payment;
        this.gold_rate = gold_rate;
        this.total_amount = total_amount;
        this.product = product;
    }

    public static class Product implements Parcelable {
        public String id = "";
        public String purity = "";
        public String less_weight = "";
        public String gross_weight = "";
        public String quantity ="";
        public String wastage = "";
        public String fine = "";
        public String laboure_rate = "";
        public String rate_on = "";
        public String laboure_amount = "";

        public Product() {
        }

        public Product(String id, String purity, String less_weight, String gross_weight,
                       String quantity, String wastage, String fine, String laboure_rate,
                       String rate_on, String laboure_amount) {
            this.id = id;
            this.purity = purity;
            this.less_weight = less_weight;
            this.gross_weight = gross_weight;
            this.quantity = quantity;
            this.wastage = wastage;
            this.fine = fine;
            this.laboure_rate = laboure_rate;
            this.rate_on = rate_on;
            this.laboure_amount = laboure_amount;
        }

        protected Product(Parcel in) {
            id = in.readString();
            purity = in.readString();
            less_weight = in.readString();
            gross_weight = in.readString();
            quantity = in.readString();
            wastage = in.readString();
            fine = in.readString();
            laboure_rate = in.readString();
            rate_on = in.readString();
            laboure_amount = in.readString();
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(purity);
            dest.writeString(less_weight);
            dest.writeString(gross_weight);
            dest.writeString(quantity);
            dest.writeString(wastage);
            dest.writeString(fine);
            dest.writeString(laboure_rate);
            dest.writeString(rate_on);
            dest.writeString(laboure_amount);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
/*    public String user_id;
    public String delivery_id;
    public String customer_id;
    public String order_date;
    public double old_fine;
    public double old_amount;
    public String old_bill_no;
    public double metal_r_weight;
    public double metal_r_purity;
    public double metal_r_fine;
    public double balance_fine;
    public double amount;
    public double gst_percentage;
    public double received_amount;
    public double round_off_amount;
    public double balance_amount;
    public String cheque_no;
    public String cheque_date;
    public String cheque_bank_name;
    public String comment_1;
    public String comment_2;
    public double other_purity;
    public String mode_of_payment;
    public double gold_rate;
    public double total_amount;
    public double received_amount_duplicate; // To prevent clash
    public double balance_amount_duplicate;  // To prevent clash
    public List<Product> product;

    public static class Product {
        public String product_id;
        public double purity;
        public double less_weight;
        public double gross_weight;
        public int quantity;
        public double wastage;
        public double fine;
        public double laboure_rate;
        public String rate_on;
        public double laboure_amount;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public double getPurity() {
            return purity;
        }

        public void setPurity(double purity) {
            this.purity = purity;
        }

        public double getLess_weight() {
            return less_weight;
        }

        public void setLess_weight(double less_weight) {
            this.less_weight = less_weight;
        }

        public double getGross_weight() {
            return gross_weight;
        }

        public void setGross_weight(double gross_weight) {
            this.gross_weight = gross_weight;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getWastage() {
            return wastage;
        }

        public void setWastage(double wastage) {
            this.wastage = wastage;
        }

        public double getFine() {
            return fine;
        }

        public void setFine(double fine) {
            this.fine = fine;
        }

        public double getLaboure_rate() {
            return laboure_rate;
        }

        public void setLaboure_rate(double laboure_rate) {
            this.laboure_rate = laboure_rate;
        }

        public String getRate_on() {
            return rate_on;
        }

        public void setRate_on(String rate_on) {
            this.rate_on = rate_on;
        }

        public double getLaboure_amount() {
            return laboure_amount;
        }

        public void setLaboure_amount(double laboure_amount) {
            this.laboure_amount = laboure_amount;
        }
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(String delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public double getOld_fine() {
        return old_fine;
    }

    public void setOld_fine(double old_fine) {
        this.old_fine = old_fine;
    }

    public double getOld_amount() {
        return old_amount;
    }

    public void setOld_amount(double old_amount) {
        this.old_amount = old_amount;
    }

    public String getOld_bill_no() {
        return old_bill_no;
    }

    public void setOld_bill_no(String old_bill_no) {
        this.old_bill_no = old_bill_no;
    }

    public double getMetal_r_weight() {
        return metal_r_weight;
    }

    public void setMetal_r_weight(double metal_r_weight) {
        this.metal_r_weight = metal_r_weight;
    }

    public double getMetal_r_purity() {
        return metal_r_purity;
    }

    public void setMetal_r_purity(double metal_r_purity) {
        this.metal_r_purity = metal_r_purity;
    }

    public double getMetal_r_fine() {
        return metal_r_fine;
    }

    public void setMetal_r_fine(double metal_r_fine) {
        this.metal_r_fine = metal_r_fine;
    }

    public double getBalance_fine() {
        return balance_fine;
    }

    public void setBalance_fine(double balance_fine) {
        this.balance_fine = balance_fine;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getGst_percentage() {
        return gst_percentage;
    }

    public void setGst_percentage(double gst_percentage) {
        this.gst_percentage = gst_percentage;
    }

    public double getReceived_amount() {
        return received_amount;
    }

    public void setReceived_amount(double received_amount) {
        this.received_amount = received_amount;
    }

    public double getRound_off_amount() {
        return round_off_amount;
    }

    public void setRound_off_amount(double round_off_amount) {
        this.round_off_amount = round_off_amount;
    }

    public double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(double balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public String getCheque_date() {
        return cheque_date;
    }

    public void setCheque_date(String cheque_date) {
        this.cheque_date = cheque_date;
    }

    public String getCheque_bank_name() {
        return cheque_bank_name;
    }

    public void setCheque_bank_name(String cheque_bank_name) {
        this.cheque_bank_name = cheque_bank_name;
    }

    public String getComment_1() {
        return comment_1;
    }

    public void setComment_1(String comment_1) {
        this.comment_1 = comment_1;
    }

    public String getComment_2() {
        return comment_2;
    }

    public void setComment_2(String comment_2) {
        this.comment_2 = comment_2;
    }

    public double getOther_purity() {
        return other_purity;
    }

    public void setOther_purity(double other_purity) {
        this.other_purity = other_purity;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public void setMode_of_payment(String mode_of_payment) {
        this.mode_of_payment = mode_of_payment;
    }

    public double getGold_rate() {
        return gold_rate;
    }

    public void setGold_rate(double gold_rate) {
        this.gold_rate = gold_rate;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getReceived_amount_duplicate() {
        return received_amount_duplicate;
    }

    public void setReceived_amount_duplicate(double received_amount_duplicate) {
        this.received_amount_duplicate = received_amount_duplicate;
    }

    public double getBalance_amount_duplicate() {
        return balance_amount_duplicate;
    }

    public void setBalance_amount_duplicate(double balance_amount_duplicate) {
        this.balance_amount_duplicate = balance_amount_duplicate;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }*/
}
