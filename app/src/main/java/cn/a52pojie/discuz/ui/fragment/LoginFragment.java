package cn.a52pojie.discuz.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a52pojie.discuz.R;
import cn.a52pojie.discuz.bean.LoginBean;
import cn.a52pojie.discuz.net.HttpHelper;
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
public class LoginFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.iv_clean_phone)
    ImageView iv_clean_phone;
    @BindView(R.id.clean_password)
    ImageView clean_password;
    @BindView(R.id.iv_show_pwd)
    ImageView iv_show_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.service)
    View service;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.et_question)
    Spinner et_question;
    @BindView(R.id.et_answer)
    EditText et_answer;
    @BindView(R.id.display_question)
    LinearLayout display;
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private int questionId = 0;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        intiView(view);
        initListener();
        return view;
    }

    private void intiView(View view) {
        ButterKnife.bind(this, view);
        int screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
    }

    private void initListener() {
        iv_clean_phone.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_question.setOnItemSelectedListener(this);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });
        et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && iv_clean_phone.getVisibility() == View.GONE) {
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    iv_clean_phone.setVisibility(View.GONE);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9?!@#$%^&*()-,:;+_=.~`/><|\\?]+")) {
                    String temp = s.toString();
                    Toasty.info(getActivity(), getString(R.string.please_input_limit_pwd), Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_password.setSelection(s.length());
                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        root.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("qtfreet00", "up------>" + (oldBottom - bottom));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);
                    zoomIn(logo, (oldBottom - bottom) - keyHeight);
                    service.setVisibility(View.INVISIBLE);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("qtfreet00", "down------>" + (bottom - oldBottom));
                    //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);
                    zoomOut(logo, (bottom - oldBottom) - keyHeight);
                    service.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                et_mobile.setText("");
                break;
            case R.id.clean_answer:
                et_answer.setText("");
                break;
            case R.id.clean_password:
                et_password.setText("");
                break;
            case R.id.iv_show_pwd:
                if (et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_pwd.setImageResource(R.mipmap.pass_visuable);
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_pwd.setImageResource(R.mipmap.pass_gone);
                }
                String pwd = et_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    et_password.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                String username = et_mobile.getText().toString();
                String password = et_password.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toasty.info(getActivity(), "请输入账号和密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginNow(username, password);
                break;
        }
    }

    private void loginNow(String username, String password) {
        Log.e("qtfreet00", "login now");
        Observable<LoginBean> login;
        if (questionId == 0) {
            login = HttpHelper.getInstance().newHttp.login("utf-8", username, password);
        } else {
            String answer = et_answer.getText().toString();
            if (answer.isEmpty()) {
                Toasty.warning(getActivity(), "请输入安全问题", Toast.LENGTH_SHORT).show();
                return;
            }
            login = HttpHelper.getInstance().newHttp.loginWithQuestion(questionId, "utf-8", username, password, answer);
        }
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginBean loginBean) {
                        if (loginBean == null) {
                            Toasty.error(getActivity(), "登录异常", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!loginBean.getMessage().getMessageval().equals("login_succeed")) {
                            Toasty.error(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toasty.success(getActivity(), loginBean.getMessage().getMessagestr(), Toast.LENGTH_SHORT, true).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toasty.warning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
            display.setVisibility(View.VISIBLE);
            questionId = i;
        } else {
            questionId = 0;
            display.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        display.setVisibility(View.GONE);
    }
}
