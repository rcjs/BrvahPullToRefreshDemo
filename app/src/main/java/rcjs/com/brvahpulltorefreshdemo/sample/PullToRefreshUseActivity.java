package rcjs.com.brvahpulltorefreshdemo.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import rcjs.com.brvahpulltorefreshdemo.R;
import rcjs.com.brvahpulltorefreshdemo.widget.loadmore.CustomLoadMoreView;

/**
 * Created by rcjs on 2017/12/8.
 * Description:
 */

public class PullToRefreshUseActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {


    RecyclerView signListRv;
    SwipeRefreshLayout refreshLayout;
    //  private View notDataView;
    //   private View errorView;
    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10, bt11;

    LoadingLayout loadingLayout;
    private PullToRefreshUseAdapter mPullToRefreshUseAdapter;
    private List<TestListBean.ListBean> mData = new ArrayList<>();
    private int page = 0;//请求页数
    private int pageSize = 5;//请求条数
    private String params;//请求参数
    private TestListBean mRouteListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        initView();
        initListener();
        initData();
    }


    private void initView() {
        signListRv = findViewById(R.id.sign_list_rv);
        refreshLayout = findViewById(R.id.refreshLayout);
        bt1 = findViewById(R.id.btn1);
        bt2 = findViewById(R.id.btn2);
        bt3 = findViewById(R.id.btn3);
        bt4 = findViewById(R.id.btn4);
        bt5 = findViewById(R.id.btn5);
        bt6 = findViewById(R.id.btn6);
        bt7 = findViewById(R.id.btn7);
        bt8 = findViewById(R.id.btn8);
        bt9 = findViewById(R.id.btn9);
        bt10 = findViewById(R.id.btn10);
        bt11 = findViewById(R.id.btn11);
        loadingLayout = findViewById(R.id.loadingLayout);
        loadingLayout.setStatus(LoadingLayout.Loading);

        //  notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) signListRv.getParent(), false);
        //  errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) signListRv.getParent(), false);
        initAdapter();
    }

    private void initData() {
        addTestData();
        getRouteList(params, page, pageSize);
    }

    private void initAdapter() {
        refreshLayout.setColorSchemeColors(Color.rgb(47, 189, 223));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        signListRv.setLayoutManager(layoutManager);
        mPullToRefreshUseAdapter = new PullToRefreshUseAdapter(mData, this);
        mPullToRefreshUseAdapter.setOnLoadMoreListener(this, signListRv);
        mPullToRefreshUseAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        signListRv.setAdapter(mPullToRefreshUseAdapter);
        mPullToRefreshUseAdapter.setLoadMoreView(new CustomLoadMoreView());

        /*notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });*/
    }

    public void initListener() {
        refreshLayout.setOnRefreshListener(this);
        mPullToRefreshUseAdapter.setOnItemClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
        bt11.setOnClickListener(this);

        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                addTestData();
                page = 0;
                getRouteList(params, page, pageSize);
            }
        });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    /**
     * 显示下拉刷新和第一次显示的数据
     *
     * @param list
     */
    public void showSignList(final List<TestListBean.ListBean> list) {
        loadingLayout.setStatus(LoadingLayout.Success);
        page++;
        mPullToRefreshUseAdapter.setNewData(list);


       //  mPullToRefreshUseAdapter.disableLoadMoreIfNotFullPage();

        Log.d("zjl", "list.size()"+list.size()+"---"+pageSize);
        //第一页如果不够一页就不显示没有更多数据布局
        if (list.size() < pageSize) {
            Log.d("zjl", "showloadMoreEnd");
            mPullToRefreshUseAdapter.loadMoreEnd(true);
        } else {
            Log.d("zjl", "showloadMoreComplete");
            mPullToRefreshUseAdapter.loadMoreComplete();
        }
    }

    /**
     * 没有下拉刷新和第一次显示的数据
     */
    private void noRefreshData() {
        Log.d("zjl", "noRefreshData");
       // mPullToRefreshUseAdapter.setEmptyView(notDataView);
        loadingLayout.setEmptyText("没有下拉刷新和第一次显示的数据");
        loadingLayout.setStatus(LoadingLayout.Empty);
    }

    /**
     * 有新的加载数据
     */
    public void loadSignList(List<TestListBean.ListBean> list) {
        page++;
        mPullToRefreshUseAdapter.addData(list);
        //加载数据如果不够一页就不显示没有更多数据布局（个人需求，可以设置为false）
        if (list.size() < pageSize) {
            Log.d("zjl", "loadloadMoreEnd");
            mPullToRefreshUseAdapter.loadMoreEnd(true); //隐藏上拉加载脚步局，并结束加载
        } else {
            Log.d("zjl", "loadloadMoreComplete");
            mPullToRefreshUseAdapter.loadMoreComplete(); //加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    /**
     * 没有新的加载数据
     */
    public void noMoreData() {
        Log.d("zjl", "noMoreloadMoreEnd");
        mPullToRefreshUseAdapter.loadMoreEnd(true); //隐藏上拉加载脚步局，并结束加载
    }

    /**
     * 开始下拉刷新和第一次显示
     */
    public void onRefreshStart() {
       /* refreshLayout.post(new Runnable() {
            @Override
            public void run() {*/
                mPullToRefreshUseAdapter.setEnableLoadMore(false);//设置不能加载更多
                refreshLayout.setRefreshing(true);
          /*  }
        });*/
    }

    /**
     * 结束下拉刷新和第一次显示
     */
    public void onRefreshEnd() {
       /* refreshLayout.post(new Runnable() {
            @Override
            public void run() {*/
                refreshLayout.setRefreshing(false);
        /*    }
        });*/
    }

    /**
     * 下拉刷新和第一次显示的数据失败
     */
    public void onRefreshError() {
     refreshLayout.setRefreshing(false);
        Toast.makeText(this, "刷新错误", Toast.LENGTH_SHORT);
        Log.d("zjl", "onRefreshError");
       // mPullToRefreshUseAdapter.setEmptyView(errorView);
        loadingLayout.setStatus(LoadingLayout.Error);//错误
    }

    /**
     * 开始上拉加载
     */
    public void onLoadStart() {
        refreshLayout.setEnabled(false);
    }

    /**
     * 结束上拉加载
     */
    public void onLoadEnd() {
        refreshLayout.setEnabled(true);
    }

    /**
     * 上拉加载失败
     */
    public void onLoadError() {
        refreshLayout.setEnabled(true);
        mPullToRefreshUseAdapter.loadMoreFail();
    }


    @Override
    public void onLoadMoreRequested() {
        getRouteList(params, page, pageSize);
    }

    @Override
    public void onRefresh() {
        page = 0;
        getRouteList(params, page, pageSize);
    }


    public void getRouteList(final String params, final int page, int pageSize) {
        if (page == 0) {
            //开始下拉刷新和第一次显示
            onRefreshStart();
        } else {
            //开始上拉加载
            onLoadStart();
        }

        //添加假数据
        test();

        if (!result.equals("")) {
            TestListBean testListBean = new Gson().fromJson(result, TestListBean.class);
            if (testListBean.getStatus() == 0) {
                if (page == 0) {
                    if (testListBean.getList().size() == 0) {
                        // 没有下拉刷新和第一次显示的数据
                        noRefreshData();
                        //结束下拉刷新和第一次显示
                        onRefreshEnd();
                    } else {
                        // 有下拉刷新和第一次显示的数据
                        showSignList(testListBean.getList());
                        //结束下拉刷新和第一次显示
                        onRefreshEnd();
                    }
                } else {
                    //开始上拉加载
                    if (testListBean.getList().size() == 0) {
                        // 没有新的加载数据
                        noMoreData();
                        //结束上拉加载
                        onLoadEnd();
                    } else {
                        // 有新的加载数据
                        loadSignList(testListBean.getList());
                        //结束上拉加载
                        onLoadEnd();
                    }
                }
            } else {
                if (page == 0) {
                    //下拉刷新和第一次显示的数据失败
                    onRefreshError();
                } else {
                    //上拉加载失败
                    onLoadError();
                }
            }
        } else {
            if (page == 0) {
                //下拉刷新和第一次显示的数据失败
                onRefreshError();
            } else {
                //上拉加载失败
                onLoadError();
            }
        }


    }

    //假数据
    private String result;

    private void test() {
        result = new Gson().toJson(mRouteListBean);
    }

    public void addTestData() {
        mRouteListBean = new TestListBean();
        List<TestListBean.ListBean> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            TestListBean.ListBean listBean = new TestListBean.ListBean();
            listBean.setName("仁昌居士");
            listBean.setAddress("第一次进入（满一页）");
            listBean.setTel("110");
            list.add(listBean);
        }

        mRouteListBean.setStatus(0);
        mRouteListBean.setCount(100);
        mRouteListBean.setStatusDesc("ok");
        mRouteListBean.setList(list);
    }

    @Override
    public void onClick(View v) {
        mRouteListBean = new TestListBean();
        List<TestListBean.ListBean> list = new ArrayList<>();
        switch (v.getId()) {
            case R.id.btn1:
                //第一次进入为空
                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("null");
                mRouteListBean.setList(list);
                page = 0;
                Log.d("zjl", "btn1");
                getRouteList(params, page, pageSize);
                break;
            case R.id.btn2:
                //第一次进入错误
                mRouteListBean.setStatus(-10010);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("fail");
                mRouteListBean.setList(list);
                page = 0;
                getRouteList(params, page, pageSize);
                break;
            case R.id.btn7:
                //第一次进入正常（满一页）
                for (int i = 0; i < 10; i++) {
                    TestListBean.ListBean listBean = new TestListBean.ListBean();
                    listBean.setName("仁昌居士");
                    listBean.setAddress("第一次进入（满一页）");
                    listBean.setTel("110");
                    list.add(listBean);
                }

                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("ok");
                mRouteListBean.setList(list);
                page = 0;
                getRouteList(params, page, pageSize);
                break;
            case R.id.btn10:
                //第一次进入正常（不满一页）
                for (int i = 0; i < 4; i++) {
                    TestListBean.ListBean listBean = new TestListBean.ListBean();
                    listBean.setName("仁昌居士");
                    listBean.setAddress("第一次进入（不满一页）");
                    listBean.setTel("110");
                    list.add(listBean);
                }

                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("ok");
                mRouteListBean.setList(list);
                page = 0;
                getRouteList(params, page, pageSize);
                break;
            case R.id.btn3:
                //  下拉刷新失败
                mRouteListBean.setStatus(-10010);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("fail");
                mRouteListBean.setList(list);
               // page = 0;
               // getRouteList(params, page, pageSize);
                break;
            case R.id.btn4:
                //下拉刷新为空数据
                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("null");
                mRouteListBean.setList(list);
              //  page = 0;
              //  getRouteList(params, page, pageSize);
                break;
            case R.id.btn8:
                //下拉刷新成功（满一页）
                for (int i = 0; i < 10; i++) {
                    TestListBean.ListBean listBean = new TestListBean.ListBean();
                    listBean.setName("仁昌居士");
                    listBean.setAddress("刷新成功（满一页）");
                    listBean.setTel("110");
                    list.add(listBean);
                }

                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("ok");
                mRouteListBean.setList(list);
             //   page = 0;
             //   getRouteList(params, page, pageSize);
                break;
            case R.id.btn11:
                //下拉刷新成功（不满一页）
                for (int i = 0; i < 4; i++) {
                    TestListBean.ListBean listBean = new TestListBean.ListBean();
                    listBean.setName("仁昌居士");
                    listBean.setAddress("刷新成功（不满一页）");
                    listBean.setTel("110");
                    list.add(listBean);
                }

                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("ok");
                mRouteListBean.setList(list);
              //  page = 0;
              //  getRouteList(params, page, pageSize);
                break;
            case R.id.btn5:
                //上拉加载失败
                mRouteListBean.setStatus(-10010);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("fail");
                mRouteListBean.setList(list);
                // getRouteList(params, page, pageSize);
                break;
            case R.id.btn6:
                //上拉加载无新数据
                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("null");
                mRouteListBean.setList(list);
                //  getRouteList(params, page, pageSize);
                break;
            case R.id.btn9:
                //上拉加载成功
                for (int i = 0; i < 10; i++) {
                    TestListBean.ListBean listBean = new TestListBean.ListBean();
                    listBean.setName("仁昌居士");
                    listBean.setAddress("上拉加载成功");
                    listBean.setTel("110");
                    list.add(listBean);
                }

                mRouteListBean.setStatus(0);
                mRouteListBean.setCount(100);
                mRouteListBean.setStatusDesc("ok");
                mRouteListBean.setList(list);
                //   getRouteList(params, page, pageSize);
                break;
            default:
                break;
        }
    }
}
