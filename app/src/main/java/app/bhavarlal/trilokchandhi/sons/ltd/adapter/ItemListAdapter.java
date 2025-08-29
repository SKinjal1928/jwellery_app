package app.bhavarlal.trilokchandhi.sons.ltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
/*Work@2819
work281900@gmail.com*/
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    private List<OrderGenerateReq.Product> itemList;
    Context mCtx;
    private ItemListAdapter.OnItemClickListener listener;
    private LayoutInflater inflater;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteItemClick(int position);
    }

    public ItemListAdapter(Context mContext, List<OrderGenerateReq.Product> itemList,
                              ItemListAdapter.OnItemClickListener listener) {
        this.itemList = itemList;
        this.mCtx = mContext;
        this.listener = listener;
        this.inflater = LayoutInflater.from(mContext);

    }

    @Override
    public ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ItemListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemListAdapter.ItemViewHolder holder, int position) {
        OrderGenerateReq.Product data = itemList.get(position);
        holder.txtProductName.setText(data.name+"");
        holder.txtQuantity.setText(data.quantity+"");
        holder.txt_net_wt.setText(String.format(Locale.US, "%.3f", (Double.parseDouble(data.gross_weight)-Double.parseDouble(data.less_weight)))+"");
        holder.txt_p_w.setText(String.valueOf(Double.parseDouble(data.purity)+"+"+Double.parseDouble(data.wastage))+"");
        holder.txt_fine.setText(new DecimalFormat("0.000").format(Double.parseDouble(data.fine))+"");
        holder.txt_labour_amt.setText(Math.round(Double.parseDouble(data.laboure_amount))+"");
        /*holder.txt_date.setText(data.getDate_dmy()+"");
        holder.txt_lodge.setText(data.getLodging()+"");
        holder.txt_other.setText(data.getOther()+"");
        holder.txt_travel.setText(data.getTravelling()+"");
        holder.txt_food.setText(data.getFood()+"");*/
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onDeleteItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtQuantity,
                txt_net_wt, txt_p_w, txt_fine, txt_labour_amt;
//        LinearLayout productsContainer;

        ImageView img_delete;
        public ItemViewHolder(View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txt_net_wt = itemView.findViewById(R.id.txt_net_wt);
            txt_p_w = itemView.findViewById(R.id.txt_p_w);
            txt_fine = itemView.findViewById(R.id.txt_fine);
            txt_labour_amt = itemView.findViewById(R.id.txt_labour_amt);
            img_delete = itemView.findViewById(R.id.btn_delete);
//            productsContainer = itemView.findViewById(R.id.productsContainer);
        }
    }

}
