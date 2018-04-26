package com.katherine.pruebarappi.util;

import android.app.Activity;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class Dialogs {

    public static void definirProgressDialog(Activity activity){
        if(Util.pDialog != null){
            Util.pDialog.dismiss();
        }

        Util.pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        Util.pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        Util.pDialog.setTitleText("Cargando...");
        Util.pDialog.setCancelable(false);
    }
}
