package app.bhavarlal.trilokchandhi.sons.ltd.model;
public class OrderHolder {
    private static OrderListResponse.Datum currentOrder;

    public static void setOrder(OrderListResponse.Datum order) {
        currentOrder = order;
    }

    public static OrderListResponse.Datum getOrder() {
        return currentOrder;
    }

    public static void clear() {
        currentOrder = null;
    }
}

