package com.example.ominext.storedeviceonline.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Ominext on 8/9/2017.
 */

public class DatePickerDialogUtil extends DialogFragment {

    private Context context;
    private Calendar MinDate, MaxDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    public DatePickerDialogUtil() {
    }

    public DatePickerDialogUtil(DatePickerDialog.OnDateSetListener callback, Calendar MinDate, Calendar MaxDate, Context context) {
        mDateSetListener = callback;
        this.MinDate = MinDate;
        this.MaxDate = MaxDate;
        this.context = context;
    }
    public DatePickerDialogUtil(DatePickerDialog.OnDateSetListener callback, Context context) {
        mDateSetListener = callback;
        this.context = context;
    }
    DatePickerDialog dd;
    DatePicker dp;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        dd = new DatePickerDialog(getActivity(), this.mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dd.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                try {
                    if (MinDate!=null&&MaxDate!=null) {
                        ((DatePickerDialog) dialog).getDatePicker().setMaxDate(MaxDate.getTimeInMillis());
                        ((DatePickerDialog) dialog).getDatePicker().setMinDate(MinDate.getTimeInMillis());

                    }
                } catch (NullPointerException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return dd;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
