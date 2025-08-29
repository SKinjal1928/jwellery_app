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
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpensePaginateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseReport;

public class ExpenseReportAdapter extends RecyclerView.Adapter<ExpenseReportAdapter.ItemViewHolder> {
    private List<ExpensePaginateResponse.List> itemList;
    Context mCtx;
    String activity = "";
    private ExpenseReportAdapter.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ExpenseReportAdapter(Context mContext, List<ExpensePaginateResponse.List> itemList,
                          ExpenseReportAdapter.OnItemClickListener listener) {
        this.itemList = itemList;
        this.mCtx = mContext;
        this.activity = activity;
        this.listener = listener;

    }

    @Override
    public ExpenseReportAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseReportAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseReportAdapter.ItemViewHolder holder, int position) {
        ExpensePaginateResponse.List data = itemList.get(position);
        /*holder.txt_place.setText(data.getNamePlace()+"");
        holder.txt_date.setText(data.getDateDmy()+"");
        holder.txt_lodge.setText(data.getLodging()+"");
        holder.txt_other.setText(data.getOther()+"");
        holder.txt_travel.setText(data.getTravelling()+"");
        holder.txt_food.setText(data.getFood()+"");*/
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });

        holder.txt_delete.setVisibility(View.GONE);
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
