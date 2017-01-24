package com.jetcloud.hgbw.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jetcloud.hgbw.R;
import com.jetcloud.hgbw.adapter.TakeFoodParentAdapter;
import com.jetcloud.hgbw.app.HgbwUrl;
import com.jetcloud.hgbw.bean.TakeFoodInfo;
import com.jetcloud.hgbw.utils.SharedPreferenceUtils;
import com.jetcloud.hgbw.utils.ShopCarUtil;
import com.jetcloud.hgbw.view.CustomProgressDialog;
import com.jetcloud.hgbw.view.widget.XListView;

import org.json.JSONException;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.jetcloud.hgbw.app.HgbwStaticString.PER_PAGE_ALL_NUM;

public class TakeFoodFragment extends BaseFragment implements XListView.IXListViewListener{
	private static final String TAG_LOG = TakeFoodFragment.class.getSimpleName();
	private static TakeFoodFragment takeFoodFragment;
	private TakeFoodParentAdapter adapter;
	private CustomProgressDialog progress;
	private boolean isFirst = true;

	private XListView mListView;
	private TextView tv_empty;
	private LinearLayout activity_my_order;
	private List<TakeFoodInfo.OrdersBean> ordersBeenList;
	private Handler mHandler;
	private int mIndex = 0;
	private int mRefreshIndex = 0;
	private int perPageNum;
	int page = 0;
	public static TakeFoodFragment newInstance() {
		if (takeFoodFragment == null) {
			takeFoodFragment = new TakeFoodFragment();
		}
		return takeFoodFragment;
	}
	@Override
	public View initRootView(LayoutInflater inflater, ViewGroup container) {
		// TODO Auto-generated method stub
		return View.inflate(getActivity(), R.layout.fragment_takefood, null);
	}

	@Override
	protected void initView() {
		topbar.setCenterText("取餐列表");
		mListView = getView(R.id.xlv_takefood);
		tv_empty = getView(R.id.tv_empty);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setAutoLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setRefreshTime(getTime());
		//隐藏加载更多
		mListView.setPullLoadEnable(false);
	}

	@Override
	public void initData() {
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (!isFirst) {
			Log.i(TAG_LOG, "2 onHiddenChanged: " + hidden);
		}
		if (!hidden && !isFirst) {
			if (isLogin()){
				getOrderRequest(page);
			}
		}
		isFirst = false;
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onResume() {
		Log.i(TAG_LOG, "2 onResume: ");
		if (isLogin()){
			getOrderRequest(page);
		}
		ShopCarUtil.ChangeCorner(getActivity(), SharedPreferenceUtils.getShopCarNumber());
		super.onResume();
	}

	private boolean isLogin() {
		if (SharedPreferenceUtils.getIdentity().equals(SharedPreferenceUtils.WITHOUT_LOGIN)) {
			mListView.setVisibility(View.GONE);
			tv_empty.setVisibility(View.VISIBLE);
			tv_empty.setText("你还未登陆");
			return false;
		} else {
			mListView.setVisibility(View.VISIBLE);
			tv_empty.setVisibility(View.GONE);
			return true;
		}
	}

	private String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(getTime());
	}


	/**
	 * 处理json数据
	 */
	private void getOrderDataFromJson(String result) throws JSONException {
		Gson gson = new Gson();
		TakeFoodInfo takeFoodInfo = gson.fromJson(result, TakeFoodInfo.class);
		List<TakeFoodInfo.OrdersBean> newOrdersBeenList = takeFoodInfo.getOrders();

		if (newOrdersBeenList == null || newOrdersBeenList.isEmpty()) {
			mListView.setVisibility(View.GONE);
			tv_empty.setVisibility(View.VISIBLE);
			tv_empty.setText("你还未有待取餐");
		} else {
			if (page == 0) {
				ordersBeenList = newOrdersBeenList;
				adapter = new TakeFoodParentAdapter(getActivity(), ordersBeenList);
				Log.i(TAG_LOG, "getOrderDataFromJson: " + ordersBeenList.size());
				mListView.setAdapter(adapter);
				perPageNum = ordersBeenList.size();
				onLoad();

			} else {
				adapter.addNewData(newOrdersBeenList);
				perPageNum = ordersBeenList.size() + newOrdersBeenList.size();
				onLoad();
			}
		}
	}


	private void getOrderRequest(int page) {
		final RequestParams params = new RequestParams(HgbwUrl.GET_ORDER_URL);
		//缓存时间
		params.addBodyParameter("identity", SharedPreferenceUtils.getIdentity());
		params.addBodyParameter("state", "1");
		params.addBodyParameter("page", String.valueOf(page));
		params.setCacheMaxAge(1000 * 60);

		x.task().run(new Runnable() {
			@Override
			public void run() {
				x.http().get(params, new Callback.CacheCallback<String>() {

					private boolean hasError = false;
					private String result = null;


					@Override
					public boolean onCache(String result) {
						this.result = result;
						return false; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
					}

					@Override
					public void onSuccess(String result) {
						// 注意: 如果服务返回304 或 onCache 选择了信任缓存, 这时result为null.
						if (result != null) {
							this.result = result;
						}
					}

					@Override
					public void onError(Throwable ex, boolean isOnCallback) {
						hasError = true;
						Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
						Log.e(TAG_LOG, "getOrderRequest onError: " + ex.getMessage());
						if (ex instanceof HttpException) { // 网络错误
							HttpException httpEx = (HttpException) ex;
							int responseCode = httpEx.getCode();
							String responseMsg = httpEx.getMessage();
							String errorResult = httpEx.getResult();
							Log.e(TAG_LOG, "getOrderRequest onError " + " code: " + responseCode + " message: " + responseMsg);
						} else { // 其他错误
							mListView.setVisibility(View.GONE);
							tv_empty.setVisibility(View.VISIBLE);
							tv_empty.setText("你还未有待取餐");
						}
					}

					@Override
					public void onCancelled(CancelledException cex) {
						Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onFinished() {
//						progress.dismiss();
						if (!hasError && result != null) {
							Log.i(TAG_LOG, "getOrderRequest onFinished: " + result);
							try {
								getOrderDataFromJson(result);
							} catch (JSONException e) {
								e.printStackTrace();
								Log.e(TAG_LOG, "getOrderRequest json error: " + e.getMessage());
							}
						}
					}

				});
//				x.task().post(new Runnable() {
//					@Override
//					public void run() {
//						progress = new CustomProgressDialog(getActivity(), "请稍后", R.drawable.fram2);
//						progress.show();
//					}
//				});
			}
		});

	}
	@Override
	public void onRefresh() {
		page = 0;
		getOrderRequest(page);
	}

	@Override
	public void onLoadMore() {
		Log.i(TAG_LOG, "onLoadMore: " + perPageNum);
		if (perPageNum > PER_PAGE_ALL_NUM ){
			mListView.setPullLoadEnable(true);
			getOrderRequest(++ page);
		} else {
			mListView.setPullLoadEnable(false);
		}
	}
}
