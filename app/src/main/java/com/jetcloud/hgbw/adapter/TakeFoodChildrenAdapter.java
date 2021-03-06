package com.jetcloud.hgbw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jetcloud.hgbw.R;
import com.jetcloud.hgbw.app.HgbwStaticString;
import com.jetcloud.hgbw.app.HgbwUrl;
import com.jetcloud.hgbw.bean.TakeFoodInfo;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Cqing on 2017/1/3.
 */

public class TakeFoodChildrenAdapter extends BaseAdapter {
    private static final String TAG_LOG = TakeFoodChildrenAdapter.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    private List<TakeFoodInfo.OrdersBean.FoodInfoBean.FoodsBean> data;
    private int productNum = 1;
    private Context context;


    public TakeFoodChildrenAdapter(Context context, List<TakeFoodInfo.OrdersBean.FoodInfoBean.FoodsBean> data) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_take_food_children, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_food_title.setText(data.get(i).getFood_name());
        double priceGcb = Double.valueOf(data.get(i).getFood_price_vr9());
        double priceRmb = Double.valueOf(data.get(i).getFood_price_rmb());
        if (data.get(i).getFood_pay_way().equals(HgbwStaticString.PAY_WAY_VR9)) {
            holder.tv_money.setText(context.getString(R.string.gcb_display, priceGcb));
        } else {
            holder.tv_money.setText(context.getString(R.string.rmb_display, priceRmb));
        }
        int num = Integer.parseInt(data.get(i).getNum());
        holder.tv_num.setText(context.getString(R.string.take_food_num, num));
                    ImageOptions imageOptions = new ImageOptions.Builder()
                    .setFailureDrawableId(R.drawable.ic_launcher)
                    .build();
            x.image().bind(holder.img_food, HgbwUrl.HOME_URL + data.get(i).getFood_pic(), imageOptions);
        return view;
    }

    private static class ViewHolder {
        @ViewInject(R.id.img_food)
        ImageView img_food;
        @ViewInject(R.id.tv_food_title)
        TextView tv_food_title;
        @ViewInject(R.id.tv_money)
        TextView tv_money;
        @ViewInject(R.id.tv_num)
        TextView tv_num;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
