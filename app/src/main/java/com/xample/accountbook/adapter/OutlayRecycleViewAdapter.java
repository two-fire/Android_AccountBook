package com.xample.accountbook.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xample.accountbook.R;
import com.xample.accountbook.entity.AccountItem;

import java.util.List;

public class OutlayRecycleViewAdapter extends RecyclerView.Adapter<OutlayRecycleViewAdapter.OutlayItemViewHolder>{
    private List<AccountItem> mItems;
    private final LayoutInflater mLayoutInflater;
    private AlertDialog.Builder mDialogBuilder;
    public OutlayRecycleViewAdapter(Activity context, List<AccountItem> mItems) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mItems = mItems;
        mDialogBuilder = new AlertDialog.Builder(context);
    }

    @NonNull
    @Override
    public OutlayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OutlayItemViewHolder h = new OutlayItemViewHolder(mLayoutInflater.inflate(
                R.layout.recyclerview_item, parent, false));
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull OutlayItemViewHolder holder, int position) {
        AccountItem item = mItems.get(position);
        holder.tvCategory.setText(item.getCategory());
        holder.tvRemark.setText(item.getRemark());
        holder.tvMoney.setText(String.valueOf(item.getMoney()));
        holder.tvDate.setText(item.getDate());
        int icon = R.drawable.book_icon;
        if (icon > 0) {
            holder.imageView.setImageResource(icon);
        }
        holder.imageViewDelete.setTag(item.getId());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id = (int) view.getTag();
                mDialogBuilder.setTitle("提示");
                mDialogBuilder.setMessage("确认删除？");
                mDialogBuilder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                deleteRecord(id);
                            }
                        });

                mDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                mDialogBuilder.show();
            }
        });

    }

    private void deleteRecord(int id) {
        for (int i = mItems.size()-1; i >= 0; i--) {
            if (mItems.get(i).getId() == id) {
                mItems.remove(i);
            }
        }
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mItems==null ? 0:mItems.size();
    }

    public class OutlayItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategory;
        TextView tvRemark;
        TextView tvMoney;
        TextView tvDate;
        ImageView imageView;
        ImageView imageViewDelete;
        public OutlayItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.textViewCategory);
            tvRemark = itemView.findViewById(R.id.textViewRemark);
            tvMoney = itemView.findViewById(R.id.textViewMoney);
            tvDate = itemView.findViewById(R.id.textViewDate);
            imageView = itemView.findViewById(R.id.imageViewIcon);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

    }
}
