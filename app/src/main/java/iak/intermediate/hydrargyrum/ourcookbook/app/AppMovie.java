package iak.intermediate.hydrargyrum.ourcookbook.app;

/**
 * Created by hydrargyrum on 2/22/2018.
 * Intermediary  request handler to main acitivity
 */

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class AppMovie extends Application {

    private static final String TAG = "AppMovie";
    private static AppMovie mInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        // Init class application
        mInstance = this;
    }

    // Method manggil class app yg sudah jalan
    public static synchronized AppMovie getInstance(){
        return mInstance;
    }

    protected boolean isInternetConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }

    /* Volley Config
     * START REGION
     * Method ini untuk mengambil request antrian volley
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024); // cache data 5 hari
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> _req, String _tag){
        Log.d("Request_queue_volley", _req + " | " + _tag);
        _req.setTag(TextUtils.isEmpty(_tag) ? TAG : _tag);
        getRequestQueue().add(_req);
    }

    // MEthod ini untuk tambahin request volley dengan tag default class ini (AppMovie)
    public <T> void addToRequestQueue(Request<T> _req){
        Log.d("Request_queue_volley", _req + " | " + TAG);
        _req.setTag(TAG);
        getRequestQueue().add(_req);
    }

    // Method cancle request volley
    public void cancelPendingRequest(Object _tag){
        if(mRequestQueue != null)
            mRequestQueue.cancelAll(_tag);
    }
    // END REGION
}
