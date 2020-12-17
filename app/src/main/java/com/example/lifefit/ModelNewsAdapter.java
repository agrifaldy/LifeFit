package com.example.lifefit;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ModelNewsAdapter extends RecyclerView.Adapter<ModelNewsAdapter.ModelNewsAdapterViewHolder> {

    private List<ModelNews> modelNews;
    private Context context;
    private String[] links;

    public ModelNewsAdapter(Context context, List<ModelNews> modelNews, String[] links) {
        this.context = context;
        this.modelNews = modelNews;
        this.links = links;
    }

    @NonNull
    @Override
    public ModelNewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModelNewsAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_slide_news, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ModelNewsAdapterViewHolder holder, int position) {
        holder.setNewsData(modelNews.get(position));

        holder.linkNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(links[holder.getAdapterPosition()]));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelNews.size();
    }

    static class ModelNewsAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private ImageView imageNews;
        private CardView linkNews;

        ModelNewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.iv_imagenews);
            textTitle = itemView.findViewById(R.id.tv_titlenews);
            linkNews = itemView.findViewById(R.id.cv_link);
        }

        void setNewsData(ModelNews modelNews) {
            Picasso.get().load(modelNews.imageUrl).into(imageNews);
            textTitle.setText(modelNews.title);
        }

    }
}
