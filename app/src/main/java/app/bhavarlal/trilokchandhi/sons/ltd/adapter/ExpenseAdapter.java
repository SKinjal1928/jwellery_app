package app.bhavarlal.trilokchandhi.sons.ltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ItemViewHolder> {
    private List<ExpenseListResponse.Datum> itemList;
    Context mCtx;
    String activity = "";
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);

    }

    public ExpenseAdapter(Context mContext, List<ExpenseListResponse.Datum> itemList,
                          OnItemClickListener listener) {
        this.itemList = itemList;
        this.mCtx = mContext;
        this.activity = activity;
        this.listener = listener;

    }

    @Override
    public ExpenseAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseAdapter.ItemViewHolder holder, int position) {
        ExpenseListResponse.Datum data = itemList.get(position);
        holder.txt_place.setText(data.getNamePlace()+"");
        holder.txt_date.setText(data.getDate_dmy()+"");
        holder.txt_lodge.setText(data.getLodging()+"");
        holder.txt_other.setText(data.getOther()+"");
        holder.txt_travel.setText(data.getTravelling()+"");
        holder.txt_food.setText(data.getFood()+"");
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });

        holder.txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_delete, txt_place, txt_date, txt_lodge, txt_travel, txt_other, txt_food;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            txt_food = itemView.findViewById(R.id.txt_food);
            txt_place = itemView.findViewById(R.id.itemPlace);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_lodge = itemView.findViewById(R.id.txt_lodge);
            txt_travel = itemView.findViewById(R.id.txt_travel);
            txt_other = itemView.findViewById(R.id.txt_other);
        }
    }
}