package com.tagam24.tagam.gumenje;

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

import com.tagam24.tagam.Constants.Constants;
import com.tagam24.tagam.Database.Db;
import com.tagam24.tagam.R;
import com.tagam24.tagam.models.Model_Food;
import com.tagam24.tagam.network.get_reciept;

import java.util.ArrayList;

public class Reciept extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    int color;
    public static ArrayList<Model_Food> list;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    public Reciept() {
    }
    public  static Handler s1,s2;
    @SuppressLint("ValidFragment")
    public Reciept(int color) {
        this.color = color;
    }
    RecycleAdapter_reciept recycleAdapter;
    Db db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        context = view.getContext();
        db=new Db(context);
        Constants.size=0;
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_dostawka_food);
        Constants.recepts.clear();
      //  Constants.food=db.get_liked_food();
        recycleAdapter = new RecycleAdapter_reciept(Constants.recepts,context);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();


        s1=new Handler(){
            @Override
            public void handleMessage(Message msg) {

                swipeRefreshLayout.setRefreshing(false);
                recycleAdapter.setData(Constants.recepts);
            }
        };

        s2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(true);
            }
        };
        get_reciept.get_Data();
        return view;

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        Constants.recepts.clear();
        Constants.size=0;
        Constants.iter=true;
        recycleAdapter.setData(Constants.recepts);
        get_reciept.get_Data();
    }
}
