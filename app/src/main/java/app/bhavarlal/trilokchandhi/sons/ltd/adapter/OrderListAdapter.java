package app.bhavarlal.trilokchandhi.sons.ltd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListPaginateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemViewHolder> {
    private List<OrderListPaginateResponse.List> itemList;
    Context mCtx;
    String activity = "";
    private OrderListAdapter.OnItemClickListener listener;
    private LayoutInflater inflater;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onPdfClick(int position);

    }

    public OrderListAdapter(Context mContext, List<OrderListPaginateResponse.List> itemList,
                          OrderListAdapter.OnItemClickListener listener) {
        this.itemList = itemList;
        this.mCtx = mContext;
        this.listener = listener;
        this.inflater = LayoutInflater.from(mContext);

    }

    @Override
    public OrderListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderListAdapter.ItemViewHolder holder, int position) {
        OrderListPaginateResponse.List data = itemList.get(position);
        holder.txt_date.setText(data.getOrderDateDmy()+"");
        holder.txt_customer_name.setText(data.getCustomer().getName()+"");
        holder.item_invoice.setText((1000+data.getId())+"");
        /*holder.txt_date.setText(data.getDate_dmy()+"");
        holder.txt_lodge.setText(data.getLodging()+"");
        holder.txt_other.setText(data.getOther()+"");
        holder.txt_travel.setText(data.getTravelling()+"");
        holder.txt_food.setText(data.getFood()+"");*/
        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) listener.onItemClick(position);
        }); holder.txt_pdf.setOnClickListener(v -> {
            if (listener != null) listener.onPdfClick(position);
        });
            Log.e("Payment Mode", data.getModeOfPayment()+"");
        // Clear existing product views to avoid duplicate inflation
      /*  holder.productsContainer.removeAllViews();

        for (OrderListResponse.OrderProduct product : data.getOrderProduct()) {
            View productView = inflater.inflate(R.layout.item_product, holder.productsContainer, false);

            ((TextView) productView.findViewById(R.id.txtProductName)).setText(product.getProductDetails().getProductName());
            ((TextView) productView.findViewById(R.id.txtQuantity)).setText("Qty: " + product.getQuantity());
//            ((TextView) productView.findViewById(R.id.txtRate)).setText("Rate: ₹" + product.getLaboureAmount());
            ((TextView) productView.findViewById(R.id.txtTotal)).setText("₹" + product.getLaboureAmount());

            holder.productsContainer.addView(productView);
        }*/

//        holder.txt_delete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView item_invoice, txt_customer_name,
                txt_date, txt_pdf;
//        LinearLayout productsContainer;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_customer_name = itemView.findViewById(R.id.txt_party_name);
            item_invoice = itemView.findViewById(R.id.item_invoice);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_pdf = itemView.findViewById(R.id.txt_pdf);
//            productsContainer = itemView.findViewById(R.id.productsContainer);
        }
    }
}
