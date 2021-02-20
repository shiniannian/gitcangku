package com.hui.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.tally.R;
import com.hui.tally.db.AccountBean;

import java.util.Calendar;
import java.util.List;

public class AccountAdapter extends BaseAdapter {     //ListView的适配器
    Context context;
    List<AccountBean>mDatas;        //传入数据源
    LayoutInflater inflater;        //加载布局
    int year,month,day;
    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();     //得到Calendar对象
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getCount() {             //返回ListView的长度（如果返回1，只显示一行）
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {       //返回指定位置的对象
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {       //返回指定的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {     //一屏ListView有多少条item，就创建多少个convertView（这个convertView其实就是最关键的部分 原理上讲当ListView滑动的过程中会有item被滑出屏幕而不再被使用 这时候Android会回收这个条目的view 这个view也就是这里的convertView）
        ViewHolder holder = null;                                               //ViewGroup作用：存放每一个被加载出来的视图
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mainlv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);     //给convertView设置绑定的tag（标签）
        }else{
            holder = (ViewHolder) convertView.getTag();     //这里设置holder的作用就是对convertView中的控件进行了统一的管理
        }
        AccountBean bean = mDatas.get(position);            //得到指定位置的对应的数据源
        holder.typeIv.setImageResource(bean.getsImageId());
        holder.typeTv.setText(bean.getTypename());
        holder.beizhuTv.setText(bean.getBeizhu());
        holder.moneyTv.setText("￥ "+bean.getMoney());
        if (bean.getYear()==year&&bean.getMonth()==month&&bean.getDay()==day) {
            String time = bean.getTime().split(" ")[1];     //加个空格分割
            holder.timeTv.setText("今天 "+time);
        }else {
            holder.timeTv.setText(bean.getTime());
        }
        return convertView;
    }

    class ViewHolder{               //把用到的所有item先传入这个类当中（1个图片，4个TextView）
        ImageView typeIv;
        TextView typeTv,beizhuTv,timeTv,moneyTv;
        public ViewHolder(View view){
            typeIv = view.findViewById(R.id.item_mainlv_iv);
            typeTv = view.findViewById(R.id.item_mainlv_tv_title);
            timeTv = view.findViewById(R.id.item_mainlv_tv_time);
            beizhuTv = view.findViewById(R.id.item_mainlv_tv_beizhu);
            moneyTv = view.findViewById(R.id.item_mainlv_tv_money);

        }
    }
}
