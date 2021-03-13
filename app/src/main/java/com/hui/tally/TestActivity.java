package com.hui.tally;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hui.tally.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout ;        //定义SwipeRefreshLayout
    private RecyclerView mRecyclerView;                     //定义RecyclerView

    private List<String> list = new ArrayList<>();          //定义List<String>集合
    private MyAdapter adapter ;                            //定义MyAdapter对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();     //初始化对象
        addListDatas(); //为List<String>集合初始化数据
        initDatas();    //初始化数据
    }

    /*
    初始化对象
     */
    private void initView() {
        //为各个控件绑定id
        swipeRefreshLayout  =   (SwipeRefreshLayout)this.findViewById(R.id.refreshLayout);
        mRecyclerView   =   (RecyclerView)this.findViewById(R.id.recycleView);
    }

    /*
    为List<String>集合初始化数据
     */
    private void addListDatas(){
        list.add("Years");
        list.add("Months");
        list.add("Days");
        list.add("Hours");
    }

    /*
    初始化数据
     */
    private void initDatas() {
        //初始化适配器
        adapter =   new MyAdapter(list);
        //为RecyclerView绑定适配器
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这样设置的效果是组件垂直往下
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置添加、删除、移动、改变的动画效果。
                                                                // RecyclerView提供了默认的ItemAnimator实现类：DefaultItemAnimator。如果没有特殊的需求，默认使用这个动画即可。


        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String city) {
                Toast.makeText(TestActivity.this, "city:" + city + ",position:" + position, Toast.LENGTH_LONG).show();
            }
        });

        //设置进度条的颜色，不定长参数可以设置多种颜色
        //对于RefreshLayout，网上有人说最多4种颜色，不要使用android.R.color.，否则会卡死
        swipeRefreshLayout.setColorSchemeColors(
                Color.YELLOW,
                Color.RED,
                Color.GREEN);

        //设置进度条的背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //设置手指划过多少像素开始触发刷新
        swipeRefreshLayout.setDistanceToTriggerSync(100);

        //设置下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //新开线程来执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //下拉刷新的处理
                        //refreshData();
                        refreshData2();

                        //结束后停止刷新
                        swipeRefreshLayout.setRefreshing(false);
                        //关闭下拉刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } , 300);

            }
        });

    }

    private void refreshData2() {

        for(int i = 0; i < 10 ; i++){
            adapter.addData(i , "second: " + i);
        }
        adapter.notifyItemRangeChanged(0 , 10);
        mRecyclerView.scrollToPosition(3);
    }

}
