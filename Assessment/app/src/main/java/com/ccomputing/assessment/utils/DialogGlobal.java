package com.ccomputing.assessment.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ccomputing.assessment.R;


public class DialogGlobal {

    private static final DialogGlobal ourInstance = new DialogGlobal();

    public static DialogGlobal getInstance() {
        return ourInstance;
    }

    private Dialog dialogLoading,dialogCompEstimate;

    private DialogGlobal() {
    }

    /**
     * show loading dialog
     */
    public void showLoadingDialog(Context context){

        try {
            if(dialogLoading!=null)
                closeLoadingDialog();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialogLoading = new Dialog(context);
            }else{
                dialogLoading = new Dialog(context,
                        android.R.style.Theme_Holo_Light_NoActionBar);
            }
            WindowManager.LayoutParams lp = dialogLoading.getWindow().getAttributes();
            lp.dimAmount=0.8f;
            dialogLoading.getWindow().setAttributes(lp);
            dialogLoading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialogLoading.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            dialogLoading.setCancelable(true);
            dialogLoading.setContentView(R.layout.global_dialogs);
            RelativeLayout rlLoading = dialogLoading.findViewById(R.id.rlLoading);
            rlLoading.setVisibility(View.VISIBLE);
            dialogLoading.show();
        }catch (Exception ignored){

        }

    }

    /**
     * close loading dialog
     */
    public void  closeLoadingDialog(){
        try {
            if(dialogLoading!=null && dialogLoading.isShowing()){
                dialogLoading.dismiss();
            }
        }catch (Exception ignored){
        }
    }

    public AlertDialog.Builder showAlertDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        if(!title.isEmpty())
            builder.setTitle(title);

        builder.setMessage(message);
        builder.setCancelable(false);
        return builder;
    }


}
