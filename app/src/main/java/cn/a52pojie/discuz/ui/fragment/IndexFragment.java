package cn.a52pojie.discuz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a52pojie.discuz.R;
import cn.a52pojie.discuz.bean.IndexBean;
import cn.a52pojie.discuz.bean.ThreadSimpleBean;
import cn.a52pojie.discuz.net.HttpHelper;
import cn.a52pojie.discuz.ui.adapter.ThreadAdapter;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class IndexFragment extends Fragment {
    private ThreadAdapter threadAdapter;
    private List<ThreadSimpleBean> threads = new ArrayList<>();
    @BindView(R.id.lv_thread_list)
    ListView lvThread;

    public IndexFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.threadAdapter = new ThreadAdapter(getContext(), threads);
        lvThread.setAdapter(threadAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIndex();

    }

    private void getIndex() {
        HttpHelper.getInstance().newHttp.getIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IndexBean>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull IndexBean indexBean) {
                        if (indexBean == null) {
                            Toasty.warning(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<IndexBean.VariablesBean.DataBean> data = indexBean.getVariables().getData();
                        int size = data.size();
                        for (int i = 0; i < size; i++) {
                            IndexBean.VariablesBean.DataBean dataBean = data.get(i);
                            ThreadSimpleBean bean = new ThreadSimpleBean();
                            bean.setTitle(dataBean.getSubject());
                            bean.setAuthor(dataBean.getAuthor());
                            bean.setTime(dataBean.getDateline());
                            bean.setPicture(!dataBean.getRecommendicon().isEmpty());
                            bean.setComments(dataBean.getReplies());
                            threads.add(bean);
                        }
                        threadAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toasty.warning(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
