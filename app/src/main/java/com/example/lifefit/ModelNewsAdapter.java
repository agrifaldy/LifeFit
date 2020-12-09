package com.example.lifefit;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ModelNewsAdapter extends RecyclerView.Adapter<ModelNewsAdapter.ModelNewsAdapterViewHolder> {

    private List<ModelNews> modelNews;

    public ModelNewsAdapter(List<ModelNews> modelNews) {
        this.modelNews = modelNews;
    }

    @NonNull
    @Override
    public ModelNewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModelNewsAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_slide_news, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ModelNewsAdapterViewHolder holder, int position) {
        holder.setNewsData(modelNews.get(position));
    }

    @Override
    public int getItemCount() {
        return modelNews.size();
    }

    static class ModelNewsAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private ImageView imageNews;

        ModelNewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.iv_imagenews);
            textTitle = itemView.findViewById(R.id.tv_titlenews);
        }

        void setNewsData(ModelNews modelNews) {
            Picasso.get().load(modelNews.imageUrl).into(imageNews);
            textTitle.setText(modelNews.title);
        }

    }
}
