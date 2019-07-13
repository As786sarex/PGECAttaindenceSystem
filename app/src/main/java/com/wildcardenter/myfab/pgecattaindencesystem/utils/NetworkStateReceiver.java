package com.wildcardenter.myfab.pgecattaindencesystem.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.wildcardenter.myfab.pgecattaindencesystem.R;


/*
    Class On Package com.wildcardenter.myfab.pgecattaindencesystem.recievers
    
    Created by Asif Mondal on 13-07-2019 at 17:51
*/


public class NetworkStateReceiver extends BroadcastReceiver {

    public static final String NETWORK_AVAILABLE_ACTION = "com.mortazacorp.pgecattendance.NETWORK_AVAILABLE";
    AlertDialog builder;

    public NetworkStateReceiver(AlertDialog dialog) {
        this.builder=dialog;
    }

    public NetworkStateReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);

        Log.e("reciever", "Sulod sa network reciever");
        AlertDialog.Builder dialog=new AlertDialog.Builder(context).setTitle("vgbv").setMessage("ij");
        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            if (!builder.isShowing()){
                builder.show();
            }
        } else {
            if (builder.isShowing()){
                builder.dismiss();
            }

        }


    }
}
