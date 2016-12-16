package com.jetcloud.hgbw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jetcloud.hgbw.bean.GoodsInfo;
import com.jetcloud.hgbw.bean.MachineInfo;
import com.jetcolud.hgbw.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

public class ShopCarFragmentAdapter extends BaseExpandableListAdapter {
	private static final String TAG_LOG = ShopCarFragmentAdapter.class.getSimpleName();
	private Context context;
	private CheckInterface checkInterface;
	private ModifyCountInterface modifyCountInterface;
	private List<MachineInfo> groups;
	private Map<String, List<GoodsInfo>> children;

	public void setCheckInterface(CheckInterface checkInterface) {
		this.checkInterface = checkInterface;
	}

	public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
		this.modifyCountInterface = modifyCountInterface;
	}

	public ShopCarFragmentAdapter(Context context, List<MachineInfo> groups, Map<String, List<GoodsInfo>> children) {
		super();
		this.context = context;
		this.groups = groups;
		this.children = children;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		String groupId = groups.get(groupPosition).getId();
		return children.get(groupId).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		List<GoodsInfo> products = children.get(groups.get(groupPosition).getId());
		return products.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		final GroupViewHolder groupViewHolder;
		if (convertView == null){
			convertView = View.inflate(context, R.layout.shopcarfragmentlist_grop_item, null);
			groupViewHolder = new GroupViewHolder(convertView);
			convertView.setTag(groupViewHolder);
		} else {
			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		final MachineInfo machineInfo = (MachineInfo) getGroup(groupPosition);

		groupViewHolder.tvMachineTitle.setText(machineInfo.getName());
		groupViewHolder.cbGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				boolean isGroupSelected = ((CheckBox) view).isChecked();
				machineInfo.setSelected(isGroupSelected);
				checkInterface.checkGroup(groupPosition, isGroupSelected);
			}
		});
		groupViewHolder.cbGroup.setChecked(machineInfo.isSelected());
		notifyDataSetChanged();
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {
		final ChildViewHolder childViewHolder;
		if (convertView == null){
			convertView = View.inflate(context, R.layout.shopcarfragmentlist_child_item, null);
			childViewHolder = new ChildViewHolder(convertView);
			convertView.setTag(childViewHolder);
		} else {
			childViewHolder = (ChildViewHolder) convertView.getTag();
		}

		final GoodsInfo goodsInfo = (GoodsInfo) getChild(groupPosition, childPosition);

		//如果食物不为空
		if (goodsInfo != null){
			ImageOptions imageOptions = new ImageOptions.Builder()
				.setFailureDrawableId(R.drawable.ic_launcher)
				.build();
			x.image().bind(childViewHolder.imgFood, goodsInfo.getImageUrl(), imageOptions);
			childViewHolder.tvFoodTitle.setText(String.valueOf(goodsInfo.getTitle()));
			childViewHolder.tvFoodContent.setText(String.valueOf(goodsInfo.getContent()));
			childViewHolder.tvMoney.setText(context.getString(R.string.rmb_display, goodsInfo.getMoney()));
			childViewHolder.tvNum.setText(String.valueOf(goodsInfo.getNum()));
			childViewHolder.cbChild.setChecked(goodsInfo.isSelected());
			childViewHolder.cbChild.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					boolean isChildSelected = ((CheckBox) view).isChecked();
					goodsInfo.setSelected(isChildSelected);
					childViewHolder.cbChild.setChecked(isChildSelected);
					checkInterface.checkChild(groupPosition, childPosition, isChildSelected);
				}
			});
			childViewHolder.tvBtnAdd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					modifyCountInterface.doIncrease(groupPosition, childPosition, childViewHolder.tvNum, childViewHolder.cbChild.isChecked());
				}
			});
			childViewHolder.tvBtnDec.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					modifyCountInterface.doDecrease(groupPosition, childPosition, childViewHolder.tvNum, childViewHolder.cbChild.isChecked());
				}
			});
		}

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int i, int i1) {
		return false;
	}

	/**
	 * 复选框接口
	 */
	public interface CheckInterface {
		/**
		 * 组选框状态改变触发的事件
		 *
		 * @param groupPosition 组元素位置
		 * @param isChecked     组元素选中与否
		 */
		void checkGroup(int groupPosition, boolean isChecked);

		/**
		 * 子选框状态改变时触发的事件
		 *
		 * @param groupPosition 组元素位置
		 * @param childPosition 子元素位置
		 * @param isChecked     子元素选中与否
		 */
		void checkChild(int groupPosition, int childPosition, boolean isChecked);
	}
	/**
	 * 改变数量的接口
	 */
	public interface ModifyCountInterface {
		/**
		 * 增加操作
		 *
		 * @param groupPosition 组元素位置
		 * @param childPosition 子元素位置
		 * @param showCountView 用于展示变化后数量的View
		 * @param isChecked     子元素选中与否
		 */
		void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

		/**
		 * 删减操作
		 *
		 * @param groupPosition 组元素位置
		 * @param childPosition 子元素位置
		 * @param showCountView 用于展示变化后数量的View
		 * @param isChecked     子元素选中与否
		 */
		void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

		/**
		 * 删除子item
		 *
		 * @param groupPosition
		 * @param childPosition
		 */
		void childDelete(int groupPosition, int childPosition);
	}

	private static class GroupViewHolder {
		@ViewInject(R.id.tv_machine_title)
		TextView tvMachineTitle;
		@ViewInject(R.id.cb_group)
		CheckBox cbGroup;
		GroupViewHolder (View view){
			x.view().inject(this, view);
		}
	}
	private static class ChildViewHolder {
		@ViewInject(R.id.img_food)
		ImageView imgFood;
		@ViewInject(R.id.cb_child)
		CheckBox cbChild;
		@ViewInject(R.id.tv_food_title)
		TextView tvFoodTitle;
		@ViewInject(R.id.tv_food_content)
		TextView tvFoodContent;
		@ViewInject(R.id.tv_btn_dec)
		TextView tvBtnDec;
		@ViewInject(R.id.tv_btn_add)
		TextView tvBtnAdd;
		@ViewInject(R.id.tv_num)
		TextView tvNum;
		@ViewInject(R.id.tv_money)
		TextView tvMoney;


		ChildViewHolder(View view){
			x.view().inject(this, view);
		}
	}
}
