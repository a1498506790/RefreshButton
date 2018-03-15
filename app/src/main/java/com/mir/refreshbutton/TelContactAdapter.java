package com.mir.refreshbutton;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.SelectableTextHelper;

import java.util.List;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018-03-15
 * @desc
 */

class TelContactAdapter extends RecyclerView.Adapter<TelContactAdapter.TelContactViewHolder>{

    private List<TelContactBean> mList;
    private Context mContext;
    private SelectableTextHelper mTextHelper;


    public TelContactAdapter(Context context, List<TelContactBean> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public TelContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rlv_tel_contact, parent, false);
        return new TelContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TelContactViewHolder holder, final int position) {
        mTextHelper = new SelectableTextHelper.Builder(holder.textTel)
                .setSelectedColor(mContext.getResources().getColor(R.color.selected_blue))
                .setCursorHandleSizeInDp(20)
                .setCursorHandleColor(mContext.getResources().getColor(R.color.cursor_handle_color))
                .build();
        TelContactBean bean = mList.get(position);
        holder.textTel.setText(bean.getTelNumber());
        holder.textName.setText(bean.getName());
        holder.lltContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class TelContactViewHolder extends RecyclerView.ViewHolder{
        TextView textName, textTel;
        LinearLayout lltContent;
        public TelContactViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textTel = itemView.findViewById(R.id.text_tel);
            lltContent = itemView.findViewById(R.id.llt_content);
        }
    }

    public void setNewData(List<TelContactBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public List<TelContactBean> getData(){
        return mList;
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
