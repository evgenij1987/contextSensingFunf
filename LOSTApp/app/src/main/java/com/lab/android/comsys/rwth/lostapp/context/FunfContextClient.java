package com.lab.android.comsys.rwth.lostapp.context;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.lab.android.comsys.rwth.lostapp.funf.QueuePipeline;
import com.lab.android.comsys.rwth.lostapp.funf.QueuePipeline.OnContextChangedListener;

import edu.mit.media.funf.FunfManager;

/**
 * Created by evgenijavstein on 12/07/15.
 */
public class FunfContextClient {
    private static final String PIPELINE_NAME = "queuePipeline";
    private FunfManager funfManager;
    private QueuePipeline pipeline;
    private Activity activity;

    private OnContextChangedListener onContextChangedListener;
    private ServiceConnection funfManagerConn=funfManagerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            funfManager = ((FunfManager.LocalBinder) service).getManager();

            final Gson gson = funfManager.getGson();

            pipeline = (QueuePipeline) funfManager.getRegisteredPipeline(PIPELINE_NAME);

            pipeline.setOnContextChangedListener(new OnContextChangedListener() {
                @Override
                public void onChanged(final ContextFeatures contextFeatures) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FunfContextClient.this.onContextChangedListener.onChanged(contextFeatures);
                        }
                    });
                }
            });
            funfManager.enablePipeline(PIPELINE_NAME);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            funfManager = null;
        }

    };

    public FunfContextClient(Activity activity){
        this.activity=activity;
    }

    /**
     * Connect to service, please use inly in <code>Activity.onCreate()</code>
     */
    public void connect(){

        // Bind to the service, to create the connection with FunfManager
        activity.bindService(new Intent(activity, FunfManager.class), funfManagerConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * Make sure to call it in <code>Activity.onDestroy()
     */
    public void disconnect(){
        activity.unbindService(funfManagerConn);
    }

    /**
     * Might return null initially, use <code>setOnContextChangedListener</code>
     * to be notified.
     * @return
     */
    public ContextFeatures getCurrentContext(){
        return pipeline.getContextFeatures();

    }

    /**
     * Context update every 2 min. Call before first call of <code>connect()</code>.
     * Note it funf decides, when to start the probes, also you configure duration and
     * interval, the actual start may be delayed.(The offset in the config file can be
     * used to set a start up time static, but this is not what is needed to track
     * user context continuously).This is what happends currently for weather
     * and simplelocation probe. But after for 4-6 minutes you receive the whole context information.
     * @param onContextChangedListener
     */
    public void  setOnContextChangedListener(OnContextChangedListener onContextChangedListener){
        this.onContextChangedListener=onContextChangedListener;
    }

}
