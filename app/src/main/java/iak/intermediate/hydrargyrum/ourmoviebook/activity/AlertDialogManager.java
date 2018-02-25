package iak.intermediate.hydrargyrum.ourmoviebook.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import iak.intermediate.hydrargyrum.ourmoviebook.R;

/**
 * Created by hydrargyrum on 2/22/2018.
 * Handling alert dialog popup
 */



public class AlertDialogManager {
    public void showAlertDialog(Context _context, String _title, String _message, Boolean _status){
        AlertDialog alertDialog = new AlertDialog.Builder(_context).create();
        alertDialog.setTitle(_title);
        alertDialog.setMessage(_message);

        if(_status != null)
            alertDialog.setIcon((_status) ? R.mipmap.ic_action_bell : R.mipmap.ic_action_bell);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which){}
        });
        alertDialog.show();
    }
}
