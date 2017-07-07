package cn.a52pojie.discuz.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a52pojie.discuz.R;
import cn.a52pojie.discuz.bean.IndexBean;
import cn.a52pojie.discuz.bean.ThreadSimpleBean;
import cn.a52pojie.discuz.net.HttpHelper;
import cn.a52pojie.discuz.ui.activity.ThreadDetailActivity;
import cn.a52pojie.discuz.ui.adapter.ThreadAdapter;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class IndexFragment extends Fragment implements ThreadAdapter.onThreadClick {
    private ThreadAdapter threadAdapter;
    private List<ThreadSimpleBean> threads = new ArrayList<>();
    @BindView(R.id.lv_thread_list)
    ListView lvThread;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private int page = 1;

    public IndexFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.threadAdapter = new ThreadAdapter(getContext(), threads);
        lvThread.setAdapter(threadAdapter);
        threadAdapter.setOnThreadClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getIndex();
        setRefresh();
    }

    private void setRefresh() {
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getIndex();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadMore();
            }
        });
    }

    private void getIndex() {
        threads.clear();
        this.page = 1;
        Observable<IndexBean> index = HttpHelper.getInstance().newHttp.getIndex();
        startRequest(index);
    }

    private void loadMore() {
        page += 1;
        Observable<IndexBean> more = HttpHelper.getInstance().newHttp.loadMore(page);
        startRequest(more);
    }

    private void startRequest(Observable<IndexBean> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IndexBean>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull IndexBean indexBean) {
                        if (indexBean == null) {
                            Toasty.warning(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishRefresh();
                            return;
                        }
                        List<IndexBean.VariablesBean.DataBean> data = indexBean.getVariables().getData();
                        if (data == null) {
                            Toasty.info(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadmore();
                            return;
                        }
                        for (IndexBean.VariablesBean.DataBean dataBean : data) {
                            ThreadSimpleBean bean = new ThreadSimpleBean();
                            bean.setTitle(dataBean.getSubject());
                            bean.setAuthor(dataBean.getAuthor());
                            bean.setTime(dataBean.getDateline());
                            bean.setPicture(!dataBean.getRecommendicon().isEmpty());
                            bean.setComments(dataBean.getReplies());
                            bean.setTid(dataBean.getTid());
                            threads.add(bean);
                        }
                        threadAdapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toasty.warning(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishRefresh();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onItemClick(String tid) {
        Intent intent = new Intent(getActivity(), ThreadDetailActivity.class);
        intent.putExtra("tid", tid);
        startActivity(intent);
    }
}
