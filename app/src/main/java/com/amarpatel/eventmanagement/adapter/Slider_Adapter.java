package com.amarpatel.eventmanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.amarpatel.eventmanagement.R;
import com.amarpatel.eventmanagement.Slider_Item;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class Slider_Adapter extends RecyclerView.Adapter<Slider_Adapter.viewholder> {

    private ArrayList<Slider_Item> list;
    private ViewPager2 viewPager2;
    int current = 0;

    public Slider_Adapter(ArrayList<Slider_Item> list, ViewPager2 viewPager2) {
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slideritem_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        if (current>2){
            current = 0;
        }
        Slider_Item model = list.get(current);
        current++;
        holder.imageView.setImageResource(model.getImage());
        if (position == list.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        void setImage(Slider_Item sliderItems){
            imageView.setImageResource(sliderItems.getImage());
        }

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }

    private Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };
}
