package com.hui.tally.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hui.tally.R;

import java.util.List;
/*创建适配器类继承RecycleView的Adapter，步骤为：
(1） 继承RecyclerView.Adapter，并且在Adapter里面声明ViewHolder类继承RecyclerView.ViewHolder，最后把自己的ViewHolder类丢进自己的Adapter类的泛型中去。
(2)在自定义ViewHolder类的构造方法中可以通过ID找到布局的控件，控件需要声明为自定义ViewHolder类的成员变量。
(3)实现RecyclerView.Adapter的所有未实现的函数，onCreateViewHolder主要负责加载布局（加载的时候注意要把父布局写到参数里去），创建自定义ViewHolder类的对象；
    onBindViewHolder主要负责把数据设置到Item的控件中；getItemCount主要负责得到数据的数目。
(4) 最好把数据声明为成员变量，在构造函数里面传进来。
(5)由于RecycleView原生不支持点击事件，需要自己添加接口进行回调。
* */



//(1)继承RecyclerView.Adapter，并且在Adapter里面声明ViewHolder类继承RecyclerView.ViewHolder，
// 最后把自己的ViewHolder类丢进自己的Adapter类的泛型中去。
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mDatas;                    //list集合
    private LayoutInflater inflater;                //用于加载布局
    private  OnItemClickListener listener;          //自定义接口


    /*
    (5)由于RecycleView原生不支持点击事件，需要自己添加接口进行回调。
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    /*
    (4)最好把数据声明为成员变量，在构造函数里面传进来。
     */
    public MyAdapter(List<String> datas){

        mDatas = datas;
    }

    /**
     (3)实现RecyclerView.Adapter的所有未实现的函数，
     onCreateViewHolder主要负责加载布局（加载的时候注意要把父布局写到参数里去），
     创建自定义ViewHolder类的对象；
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return new ViewHolder(view);    //创建一个使用布局文件加载出view，然后使用这个view来创建一个ViewHolder对象返回
    }

    /*
    (3)onBindViewHolder主要负责把数据设置到Item的控件中
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    /*
    (3)getItemCount主要负责得到数据的数目
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /*
    为list添加数据
     */
    public  void addData(int position,String city){

        mDatas.add(position,city);
        notifyItemInserted(position);

    }

    /*
    为list删除数据
     */
    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    public void notifyItemsChange(int start,int itemCount){
        notifyItemRangeChanged(start,itemCount);

    }


    /**
     * (1)声明ViewHolder类继承RecyclerView.ViewHolder
     * （2）在自定义ViewHolder类的构造方法中可以通过ID找到布局的控件，
     * 控件需要声明为自定义ViewHolder类的成员变量。
     */
    class  ViewHolder extends  RecyclerView.ViewHolder{

        private TextView textView;      //定义item中的textView
        //初始化
        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            textView = (TextView) itemView.findViewById(R.id.text);
            //设置监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        listener.onClick(v,getLayoutPosition(),mDatas.get(getLayoutPosition()));
                    }

                }
            });


        }
    }

    /*
    (5)由于RecycleView原生不支持点击事件，需要自己添加接口进行回调。
     */
    public interface  OnItemClickListener{
        void onClick(View v, int position, String city);
    }

}
