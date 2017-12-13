package rcjs.com.brvahpulltorefreshdemo.sample;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import rcjs.com.brvahpulltorefreshdemo.R;


/**
 * Created by rcjs on 2017/12/8.
 * Description:适配器
 */


public class PullToRefreshUseAdapter extends BaseQuickAdapter<TestListBean.ListBean, PullToRefreshUseAdapter.MyViewHolder> {
    private Context context;

    public PullToRefreshUseAdapter(List<TestListBean.ListBean> list, Context context) {
        super(R.layout.item_sign_list, list);
        this.context = context;

    }


    @Override
    protected void convert(final MyViewHolder holder, final TestListBean.ListBean item) {
        holder.nameTv.setText(item.getName());
        holder.addressTv.setText(item.getAddress());
    }


    public class MyViewHolder extends BaseViewHolder {
        TextView nameTv;
        TextView addressTv;


        public MyViewHolder(View view) {
            super(view);
            nameTv = view.findViewById(R.id.name_tv);
            addressTv = view.findViewById(R.id.address_tv);
        }

    }
}