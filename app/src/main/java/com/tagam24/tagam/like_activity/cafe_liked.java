package com.tagam24.tagam.like_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tagam24.tagam.Constants.Constants;
import com.tagam24.tagam.Database.Db;
import com.tagam24.tagam.R;
import com.tagam24.tagam.models.Model_Food;

import java.util.ArrayList;


public class cafe_liked extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    int color;
    public static ArrayList<Model_Food> list;
    Context context;
SwipeRefreshLayout swipeRefreshLayout;
    public cafe_liked() {
    }
    Db db;
public  static Handler s1,s2;
    @SuppressLint("ValidFragment")
    public cafe_liked(int color) {
        this.color = color;
    }
 RecycleAdapter_cafeliked recycleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        context = view.getContext();
        db=new Db(context);
        Constants.size=0;
     swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
     swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_dostawka_food);
        Constants.cafe_like.clear();
        Constants.cafe_like=db.get_liked_cafe();
        recycleAdapter = new RecycleAdapter_cafeliked(Constants.cafe_like,context);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();

          //    Toast.makeText(context,""+Constants.cafe.size(),Toast.LENGTH_SHORT).show();
        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
           recycleAdapter.setData(Constants.cafe_like);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
              swipeRefreshLayout.setRefreshing(true);
            }
        };

        return view;

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
   //     recycleAdapter.setData(Constants.cafe);
        //get_food.get_Data();
    }
}
