package iak.intermediate.hydrargyrum.ourcookbook.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import iak.intermediate.hydrargyrum.ourcookbook.database.DatabaseHandler;

/**
 * Created by hydrargyrum on 2/22/2018.
 * Kelas parent untuk activity movie dan lainnya
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private AlertDialog.Builder alert;
    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this);
        db = new DatabaseHandler(this);
    }

    // Method get db
    protected DatabaseHandler getDB(){
        return db;
    }

    protected void showAlertMessage(String _title, String _message){
        alert.setTitle(_title);
        alert.setMessage(_message);
        alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    protected void showDialog(String _message){
        pDialog.setMessage(_message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected void hideDialog(){
        if(pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pDialog != null)
            pDialog.cancel();
    }

    protected boolean isInternetConnectionAvaiable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork == null ? false : activeNetwork.isConnectedOrConnecting();
    }
}
