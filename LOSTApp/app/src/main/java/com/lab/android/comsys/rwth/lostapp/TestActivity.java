package com.lab.android.comsys.rwth.lostapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lab.android.comsys.rwth.lostapp.context.ContextFeatures;
import com.lab.android.comsys.rwth.lostapp.context.FunfContextClient;
import com.lab.android.comsys.rwth.lostapp.context.MotionFeatures;
import com.lab.android.comsys.rwth.lostapp.funf.QueuePipeline;

import java.util.Date;

/**
 * Created by evgenijavstein on 12/07/15.
 */
public class TestActivity extends ActionBarActivity {

    FunfContextClient funfContextClient;
    private static final String TAG=TestActivity.class.getCanonicalName();
    private TextView contextDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextDisplay=(TextView)findViewById(R.id.contextDisplay);


        funfContextClient=new FunfContextClient(this);

        //1.SET LISTENER (you might also call <code>FunfContextClient.getCurrentContext()<code>)
        funfContextClient.setOnContextChangedListener(new QueuePipeline.OnContextChangedListener() {
            @Override
            public void onChanged(ContextFeatures contextFeatures) {

                Date date = new Date();
                Gson gson=new Gson();
                String contextChange="Context changed: " + date.toString() + " " + gson.toJson(contextFeatures);
                Log.d(TAG,contextChange );

                contextDisplay.setText(contextChange);

            }
        });

        //2.CONNECT IN ON_CREATE
        funfContextClient.connect();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //3. DISCONNECT IN ON_DESTROY
        funfContextClient.disconnect();

    }
}
