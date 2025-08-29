package app.bhavarlal.trilokchandhi.sons.ltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;

public class ItemProductAdapter extends RecyclerView.Adapter<ItemProductAdapter.ItemViewHolder> {
    private List<OrderListResponse.OrderProduct> itemList;
    Context mCtx;
    String activity = "";
    private ItemProductAdapter.OnItemClickListener listener;
    private LayoutInflater inflater;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ItemProductAdapter(Context mContext, List<OrderListResponse.OrderProduct> itemList,
                        ItemProductAdapter.OnItemClickListener listener) {
        this.itemList = itemList;
        this.mCtx = mContext;
        this.listener = listener;
        this.inflater = LayoutInflater.from(mContext);

    }

    @Override
    public ItemProductAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_list, parent, false);
        return new ItemProductAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemProductAdapter.ItemViewHolder holder, int position) {
        OrderListResponse.OrderProduct data = itemList.get(position);
        holder.txt_date.setVisibility(View.GONE);
        holder.txt_customer_name.setText(data.getProductDetails().getProductName()+"");
        holder.item_invoice.setVisibility(View.GONE);
        /*holder.txt_date.setText(data.getDate_dmy()+"");
        holder.txt_lodge.setText(data.getLodging()+"");
        holder.txt_other.setText(data.getOther()+"");
        holder.txt_travel.setText(data.getTravelling()+"");
        holder.txt_food.setText(data.getFood()+"");*/
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });
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
//            productsContainer = itemView.findViewById(R.id.productsContainer);
        }
    }
}
