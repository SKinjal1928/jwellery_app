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
    public String payment_type = "";
    public List<Product> product;

    public  OrderGenerateReq(String user_id, String delivery_id, String customer_id, String order_date,
                            double old_fine, double old_amount, String old_bill_no,
                            double metal_r_weight, double metal_r_purity, double metal_r_fine,
                            double balance_fine, double amount, double gst_percentage,
                            double received_amount, double round_off_amount, double balance_amount,
                            String cheque_no, String cheque_date, String cheque_bank_name,
                            String comment_1, String comment_2, double other_purity, String mode_of_payment,
                            double gold_rate, double total_amount, String payment_type,
                            List<Product> product) {

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
        this.payment_type = payment_type;
        this.product = product;
    }

    public  OrderGenerateReq(String user_id, String delivery_id, String customer_id, String order_date,
                             double old_fine, double old_amount, String old_bill_no,
                             double metal_r_weight, double metal_r_purity, double metal_r_fine,
                             double balance_fine, double amount, double gst_percentage,
                             double received_amount, double round_off_amount, double balance_amount,
                             String cheque_no, String cheque_bank_name,
                             String comment_1, String comment_2, double other_purity, String mode_of_payment,
                             double gold_rate, double total_amount, String payment_type,
                             List<Product> product) {
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
        this.cheque_bank_name = cheque_bank_name;
        this.comment_1 = comment_1;
        this.comment_2 = comment_2;
        this.other_purity = other_purity;
        this.mode_of_payment = mode_of_payment;
        this.gold_rate = gold_rate;
        this.total_amount = total_amount;
        this.payment_type = payment_type;
        this.product = product;
    }

    public static class Product implements Parcelable {
        public String id = "";
        public String name = "";
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

        public Product(String id, String name, String purity, String less_weight, String gross_weight,
                       String quantity, String wastage, String fine, String laboure_rate,
                       String rate_on, String laboure_amount) {
            this.id = id;
            this.name = name;
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
            name = in.readString();
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
            dest.writeString(name);
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

}
