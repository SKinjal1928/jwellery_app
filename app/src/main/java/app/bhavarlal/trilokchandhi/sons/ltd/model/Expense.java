package app.bhavarlal.trilokchandhi.sons.ltd.model;

public class Expense {
    String name_place;
    String date;
    int advance_cash;
    int lodging;
    int travelling;
    int food;
    int other;

    public Expense(String name_place, String date, int advance_cash, int lodging, int travelling, int food, int other) {
        this.name_place = name_place;
        this.date = date;
        this.advance_cash = advance_cash;
        this.lodging = lodging;
        this.travelling = travelling;
        this.food = food;
        this.other = other;
    }

    public void setName_place(String name_place) {
        this.name_place = name_place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAdvance_cash(int advance_cash) {
        this.advance_cash = advance_cash;
    }

    public void setLodging(int lodging) {
        this.lodging = lodging;
    }

    public void setTravelling(int travelling) {
        this.travelling = travelling;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setOther(int other) {
        this.other = other;
    }
}
