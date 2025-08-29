package app.bhavarlal.trilokchandhi.sons.ltd;

import java.util.ArrayList;

import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;

public interface OnProductListSubmittedListener {
    void onProductListSubmitted(ArrayList<OrderGenerateReq.Product> productList);
}
