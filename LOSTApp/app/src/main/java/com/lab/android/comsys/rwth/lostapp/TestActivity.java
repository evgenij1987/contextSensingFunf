package com.lab.android.comsys.rwth.lostapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.android.comsys.rwth.lostapp.context.ContextFeatures;
import com.lab.android.comsys.rwth.lostapp.context.FunfContextClient;
import com.lab.android.comsys.rwth.lostapp.funf.QueuePipeline;
import com.lab.android.comsys.rwth.lostapp.requests.LearnPostRequest;
import com.lab.android.comsys.rwth.lostapp.requests.OnResponseListener;

import java.util.Date;

/**
 * Created by evgenijavstein on 12/07/15.
 */
public class TestActivity extends ActionBarActivity {

    FunfContextClient funfContextClient;
    private static final String TAG=TestActivity.class.getCanonicalName();
    private TextView contextDisplay;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button= (Button) findViewById(R.id.learnButton);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearnPostRequest learnPostRequest=new LearnPostRequest(TestActivity.this);
                learnPostRequest.setOnResponseListener(new OnResponseListener<Boolean>(){
                    @Override
                    public void onResponse(Boolean response) {
                        if(response)
                            Toast.makeText(TestActivity.this, "Learned something new", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
                learnPostRequest.send(funfContextClient.getCurrentContext(),1,null, true);
            }
        });


        contextDisplay=(TextView)findViewById(R.id.contextDisplay);


        funfContextClient=new FunfContextClient(this);

        //1.SET LISTENER (you might also call <code>FunfContextClient.getCurrentContext()<code>)
        funfContextClient.setOnContextChangedListener(new QueuePipeline.OnContextChangedListener() {
            @Override
            public void onChanged(ContextFeatures contextFeatures) {

                Date date = new Date();
                String context=contextFeatures.getAsJSON().toString();
                String contextChange="Context changed: " + date.toString() + " " +context;
                Log.d(TAG,contextChange );

                contextDisplay.setText(contextChange);

                button.setEnabled(true);

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
