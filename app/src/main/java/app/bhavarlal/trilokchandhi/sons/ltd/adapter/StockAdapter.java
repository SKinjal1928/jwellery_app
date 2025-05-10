package app.bhavarlal.trilokchandhi.sons.ltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.StockResponse;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ItemViewHolder> {
    private List<StockResponse.Datum> itemList;
    Context mCtx;
    String activity = "";
    private StockAdapter.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);

    }

    public StockAdapter(Context mContext, List<StockResponse.Datum> itemList) {
        this.itemList = itemList;
        this.mCtx = mContext;
        this.activity = activity;
        this.listener = listener;

    }

    @Override
    public StockAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stock, parent, false);
        return new StockAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockAdapter.ItemViewHolder holder, int position) {
        StockResponse.Datum data = itemList.get(position);
        holder.txt_p_name.setText(data.getProductName()+"");
        holder.txt_quantity.setText(data.getFinalQuantity()+"");
        holder.txt_purity.setText(data.getPurity()+"");
        holder.txt_g_wt.setText(data.getFinalGrossWeight()+"");
        holder.txt_less_wt.setText(data.getFinalLessWeight()+"");
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_p_name, txt_quantity, txt_purity, txt_g_wt, txt_less_wt;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_p_name = itemView.findViewById(R.id.itemProduct);
            txt_quantity = itemView.findViewById(R.id.txt_qty);
            txt_g_wt = itemView.findViewById(R.id.txt_gwt);
            txt_less_wt = itemView.findViewById(R.id.txt_less_wt);
            txt_purity = itemView.findViewById(R.id.txt_purity);
        }
    }
}
