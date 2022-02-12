package com.example.test.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.model.PostResult;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<PostResult> mData;
    private OnItemClick onItemClick;

    public PostAdapter() {
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(mData.get(position).getId()));
        holder.tvTitle.setText(String.valueOf(mData.get(position).getTitle()));
        holder.itemView.setOnClickListener(v ->{
            if(onItemClick != null){
                onItemClick.onClick(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvId;
        TextView tvTitle;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    public void setData(ArrayList<PostResult> postResults) {
        this.mData = postResults;
        notifyDataSetChanged();
    }

    public interface OnItemClick {
        void onClick(PostResult postResult);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
