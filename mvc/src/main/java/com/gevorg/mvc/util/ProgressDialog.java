package com.gevorg.mvc.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

public class ProgressDialog {

    private static AlertDialog progressDialog;

    public static void show(Context context) {
        try {
            progressDialog.dismiss();
        } catch (Exception ignored) {
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        progressDialog = builder.create();
        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleInverse);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(context.getResources().getColor(android.R.color.white)));
        }
        progressDialog.setView(progressBar);
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void showWithTimer(Context context, int millisecond) {
        show(context);
        Handler handler = new Handler();
        handler.postDelayed(ProgressDialog::dismiss,millisecond);
    }

    public static void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public static String colorDecToHex(int p_red, int p_green, int p_blue) {
        String red = Integer.toHexString(p_red);
        String green = Integer.toHexString(p_green);
        String blue = Integer.toHexString(p_blue);

        if (red.length() == 1) {
            red = "0" + red;
        }
        if (green.length() == 1) {
            green = "0" + green;
        }
        if (blue.length() == 1) {
            blue = "0" + blue;
        }

        String colorHex = "#" + red + green + blue;
        return colorHex;
    }
}
