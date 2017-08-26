package com.app.gaolonglong.fragmenttabhost.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.app.gaolonglong.fragmenttabhost.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by yanqi on 2017/8/18.
 */

public class DebugApiActivity extends Activity {

    @BindView(R.id.test_msg)
    TextView test;

    private Call call;

    @OnClick(R.id.test)
    public void test()
    {



        /*Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.102:8013")
                         .addConverterFactory(GsonConverterFactory.create()).build();*/

        /*call.enqueue(new Callback() {
            @Override
            public void onResponse(final Response response, Retrofit retrofit) {
                if(response.isSuccess())
                {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           test.setText(response.body().toString());
                       }
                   });

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_page);
        ButterKnife.bind(this);
    }
}
