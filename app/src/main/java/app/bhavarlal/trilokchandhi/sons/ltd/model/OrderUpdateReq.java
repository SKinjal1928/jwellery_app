package app.bhavarlal.trilokchandhi.sons.ltd.model;

import java.util.List;

public class OrderUpdateReq {

        public String user_id = "5";
        public String id = "";
        public String delivery_id = "1";
        public String customer_id = "1";
        public String order_date = "02-05-2025";
        public double old_fine = 10.5;
        public double old_amount = 5000;
        public String old_bill_no = "OLD123";
        public double metal_r_weight = 20.0;
        public double metal_r_purity = 91.6;
        public double metal_r_fine = 18.32;
        public double balance_fine = 1.68;
        public double amount = 6000;
        public double gst_percentage = 3;
        public double received_amount = 5000;
        public double round_off_amount = 0.5;
        public double balance_amount = 1000;
        public String cheque_no = "123456";
        public String cheque_date = "2025-05-01";
        public String cheque_bank_name = "ABC Bank";
        public String comment_1 = "First comment";
        public String comment_2 = "Second comment";
        public double other_purity = 75.0;
        public String mode_of_payment = "GST";
        public double gold_rate = 6500;
        public double total_amount = 6100;
        public List<Product> product;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public OrderUpdateReq(String user_id, String id, String delivery_id, String customer_id,
                          String order_date, double old_fine, double old_amount, String old_bill_no,
                          double metal_r_weight, double metal_r_purity, double metal_r_fine,
                          double balance_fine, double amount, double gst_percentage,
                          double received_amount, double round_off_amount, double balance_amount,
                          String cheque_no, String cheque_date, String cheque_bank_name,
                          String comment_1, String comment_2, double other_purity,
                          String mode_of_payment, double gold_rate, double total_amount,
                          List<Product> product) {
        this.user_id = user_id;
        this.id = id;
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

    public static class Product {
            public String id = "1";
            public double purity = 91.6;
            public double less_weight = 0.5;
            public double gross_weight = 10.0;
            public int quantity = 1;
            public double wastage = 2.0;
            public double fine = 9.2;
            public double laboure_rate = 100;
            public String rate_on = "per gram";
            public double laboure_amount = 1000;

        public Product(String id, double purity, double less_weight, double gross_weight, int quantity,
                       double wastage, double fine, double laboure_rate, String rate_on,
                       double laboure_amount) {
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

        public double getLaboure_amount() {
            return laboure_amount;
        }

        public void setLaboure_amount(double laboure_amount) {
            this.laboure_amount = laboure_amount;
        }

        public String getRate_on() {
            return rate_on;
        }

        public void setRate_on(String rate_on) {
            this.rate_on = rate_on;
        }

        public double getLaboure_rate() {
            return laboure_rate;
        }

        public void setLaboure_rate(double laboure_rate) {
            this.laboure_rate = laboure_rate;
        }

        public double getFine() {
            return fine;
        }

        public void setFine(double fine) {
            this.fine = fine;
        }

        public double getWastage() {
            return wastage;
        }

        public void setWastage(double wastage) {
            this.wastage = wastage;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getGross_weight() {
            return gross_weight;
        }

        public void setGross_weight(double gross_weight) {
            this.gross_weight = gross_weight;
        }

        public double getLess_weight() {
            return less_weight;
        }

        public void setLess_weight(double less_weight) {
            this.less_weight = less_weight;
        }

        public double getPurity() {
            return purity;
        }

        public void setPurity(double purity) {
            this.purity = purity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
