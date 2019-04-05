package com.tagam24.tagam.like_activity;

/**
 * Created by Sulik on 1/3/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tagam24.tagam.Constants.Constants;
import com.tagam24.tagam.Database.Db;
import com.tagam24.tagam.R;
import com.tagam24.tagam.cafe_bar.Kafe_bar_mod;
import com.tagam24.tagam.cafe_bar.cafe_recycle;
import com.tagam24.tagam.cafe_bar.show_details_bars;
import com.tagam24.tagam.network.Api;

import java.util.ArrayList;


public class RecycleAdapter_cafetliked extends RecyclerView.Adapter<RecycleAdapter_cafetliked.Reklama_viewholder> {
    ArrayList<Kafe_bar_mod> list1;
    Context cntx;

    Typeface typeface;
    Typeface typeface1;
    Typeface typeface2;
    Db db;
    public class Reklama_viewholder extends RecyclerView.ViewHolder {
        TextView name, address, worktime;
        ImageView star1, star2, star3, star4, star5;
        ImageView imageView,lock,love;
        LinearLayout l1;
        MaterialRippleLayout r1;
        LinearLayout.LayoutParams ls;

        public Reklama_viewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.cafe_name_show);
            address = (TextView) itemView.findViewById(R.id.cafe_addres_show);
            worktime = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView) itemView.findViewById(R.id.imag_cafe_show);
            star1 = (ImageView) itemView.findViewById(R.id.cafe_star_image1_show);
            star2 = (ImageView) itemView.findViewById(R.id.cafe_star_image2_show);
            star3 = (ImageView) itemView.findViewById(R.id.cafe_star_image3_show);
            star4 = (ImageView) itemView.findViewById(R.id.cafe_star_image4_show);
            star5 = (ImageView) itemView.findViewById(R.id.cafe_star_image5_show);
           l1=(LinearLayout)itemView.findViewById(R.id.linear);
            r1=(MaterialRippleLayout)itemView.findViewById(R.id.ripple);

            love=(ImageView)itemView.findViewById(R.id.love);
        }
    }

    public RecycleAdapter_cafetliked(ArrayList<Kafe_bar_mod> items, Context ctx) {
        this.list1 = items;
        this.cntx = ctx;
        db=new Db(ctx);
    }

    @Override
    public Reklama_viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_kafes, parent, false);
        final Reklama_viewholder view = new Reklama_viewholder(v);
        return view;

    }

    @Override
    public void onBindViewHolder(final Reklama_viewholder holder, final int position) {
        holder.name.setText(list1.get(position).getName());
        Typeface typebold = Typeface.createFromAsset(cntx.getAssets(), "fonts/OpenSans_Bold.ttf");
        Typeface typelight = Typeface.createFromAsset(cntx.getAssets(), "fonts/OpenSans_Light.ttf");
        Typeface typeregular = Typeface.createFromAsset(cntx.getAssets(), "fonts/OpenSans_Regular.ttf");
        Typeface typeextrabold = Typeface.createFromAsset(cntx.getAssets(), "fonts/OpenSans_ExtraBold.ttf");


        if(position==list1.size()-1&& Constants.iter==true){
           // Constants.size=list1.size();
          // get_cafe_bar.get_Data();
          //  cafe_recycle.s2.sendEmptyMessage(1);
          //  Log.d("geldi",""+position);

        }
        holder.name.setTypeface(typebold);
        Glide.with(cntx)
                .load("http://"+ Api.url+"tagam24/images/" + list1.get(position).getImage()).asBitmap().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageView);
        holder.address.setText(list1.get(position).getAddress());
        holder.address.setTypeface(typeregular);
        holder.worktime.setText(list1.get(position).getWork_time());
        holder.worktime.setTypeface(typeregular);
        final Kafe_bar_mod m=list1.get(position);
          holder.love.setImageResource(R.drawable.heart_icon);
        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 holder.love.setImageResource(R.drawable.heart_bos);db.delete_kafe_bar(m.getId());
                list1.remove(position);
                notifyDataSetChanged();
    }
        });
            holder.r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(cntx,show_details_bars.class);
                    i.putExtra("cafe_or_toy",Constants.cafemy);
                    i.putExtra("number",list1.get(position).getNumber());
                    i.putExtra("id",list1.get(position).getId());
                    i.putExtra("name",list1.get(position).getName());
                    i.putExtra("image",list1.get(position).getImage());
                    i.putExtra("image1",list1.get(position).getImage1());
                    i.putExtra("image2",list1.get(position).getImage2());
                    i.putExtra("image3",list1.get(position).getImage3());
                    i.putExtra("content",list1.get(position).getContent());
                    i.putExtra("watch",list1.get(position).getWatch());
                    i.putExtra("n_people",list1.get(position).getN_people());
                    i.putExtra("rating",list1.get(position).getRating());
                    i.putExtra("address",list1.get(position).getAddress());
                    i.putExtra("work_time",list1.get(position).getWork_time());
                    i.putExtra("work_data",list1.get(position).getWork_data());
                    i.putExtra("karta_image",list1.get(position).getKarta_image());
                    cntx.startActivity(i);
                    cafe_recycle.s3.sendEmptyMessage(1);
                }
            });
        String time;
        time = list1.get(position).getWork_time();
      //  holder.worktime.setText(time);
      //  holder.worktime.setTypeface(typeface1);
        Integer a;
        a = Integer.parseInt(list1.get(position).getRating());
        if (a == 0) {
            holder.star1.setImageResource(R.drawable.star_white);
            holder.star2.setImageResource(R.drawable.star_white);
            holder.star3.setImageResource(R.drawable.star_white);
            holder.star4.setImageResource(R.drawable.star_white);
            holder.star5.setImageResource(R.drawable.star_white);
        }
        if (a == 1) {
            holder.star1.setImageResource(R.drawable.star_icon);
            holder.star2.setImageResource(R.drawable.star_white);
            holder.star3.setImageResource(R.drawable.star_white);
            holder.star4.setImageResource(R.drawable.star_white);
            holder.star5.setImageResource(R.drawable.star_white);
        }
        if (a == 2) {
            holder.star1.setImageResource(R.drawable.star_icon);
            holder.star2.setImageResource(R.drawable.star_icon);
            holder.star3.setImageResource(R.drawable.star_white);
            holder.star4.setImageResource(R.drawable.star_white);
            holder.star5.setImageResource(R.drawable.star_white);
        }
        if (a == 3) {
            holder.star1.setImageResource(R.drawable.star_icon);
            holder.star2.setImageResource(R.drawable.star_icon);
            holder.star3.setImageResource(R.drawable.star_icon);
            holder.star4.setImageResource(R.drawable.star_white);
            holder.star5.setImageResource(R.drawable.star_white);
        }
        if (a == 4) {
            holder.star1.setImageResource(R.drawable.star_icon);
            holder.star2.setImageResource(R.drawable.star_icon);
            holder.star3.setImageResource(R.drawable.star_icon);
            holder.star4.setImageResource(R.drawable.star_icon);
            holder.star5.setImageResource(R.drawable.star_white);
        }
        if (a == 5) {
            holder.star1.setImageResource(R.drawable.star_icon);
            holder.star2.setImageResource(R.drawable.star_icon);
            holder.star3.setImageResource(R.drawable.star_icon);
            holder.star4.setImageResource(R.drawable.star_icon);
            holder.star5.setImageResource(R.drawable.star_icon);
        }




    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public void setData(ArrayList<Kafe_bar_mod> cafe){
        list1=cafe;
        notifyDataSetChanged();
    }




}