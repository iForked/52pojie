package cn.a52pojie.discuz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a52pojie.discuz.R;
import cn.a52pojie.discuz.bean.ForumGroup;
import cn.a52pojie.discuz.bean.ForumItem;
import cn.a52pojie.discuz.bean.ForumTitleBean;
import cn.a52pojie.discuz.net.HttpHelper;
import cn.a52pojie.discuz.ui.adapter.ForumExpListAdapter;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qtfreet00 on 2017/6/22.
 */

public class ForumFragment extends Fragment {
    private ForumExpListAdapter adapter;
    private List<ForumGroup> gData = new ArrayList<>();
    private List<List<ForumItem>> iData = new ArrayList<>();
    @BindView(R.id.lv_forum_list)
    ExpandableListView lvForum;

    public ForumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        ButterKnife.bind(this, view);
        this.adapter = new ForumExpListAdapter(gData, iData, getContext());
        lvForum.setAdapter(adapter);
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
    }

    private void getIndex() {
        HttpHelper.getInstance().newHttp.getForum()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForumTitleBean>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ForumTitleBean forumTitleBean) {
                        try {
                            if (forumTitleBean == null) {
                                Toasty.warning(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            List<ForumTitleBean.VariablesBean.ForumlistBean> forumlist = forumTitleBean.getVariables().getForumlist();
                            List<ForumTitleBean.VariablesBean.CatlistBean> catlist = forumTitleBean.getVariables().getCatlist();
                            for (ForumTitleBean.VariablesBean.CatlistBean catlistBean : catlist) {
                                ForumGroup forumGroup = new ForumGroup();
                                forumGroup.setName(catlistBean.getName());
                                forumGroup.setFid(catlistBean.getFid());
                                gData.add(forumGroup);
                                List<String> forums = catlistBean.getForums();
                                List<ForumItem> item = new ArrayList<ForumItem>();
                                for (String fid : forums) {
                                    for (ForumTitleBean.VariablesBean.ForumlistBean forumlistBean : forumlist) {
                                        if (forumlistBean.getFid().equals(fid)) {
                                            ForumItem forumItem = new ForumItem();
                                            forumItem.setFid(fid);
                                            forumItem.setName(forumlistBean.getName());
                                            forumItem.setDescription(forumlistBean.getDescription());
                                            forumItem.setPosts(forumlistBean.getPosts());
                                            forumItem.setThreads(forumlistBean.getThreads());
                                            forumItem.setTodayPosts(forumlistBean.getTodayposts());
                                            item.add(forumItem);
                                        }
                                    }
                                }
                                iData.add(item);
                            }
                            adapter.notifyDataSetChanged();
                            int count =lvForum.getCount();
                            if(count>0){
                                for (int i = 0; i <count; i++) {
                                    lvForum.expandGroup(i);
                                }
                            }

                        } catch (Exception e) {
                            Toasty.warning(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                        }
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
