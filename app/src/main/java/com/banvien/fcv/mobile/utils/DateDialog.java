package com.banvien.fcv.mobile.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.banvien.fcv.mobile.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener {

    EditText txtDate;

    public static DateDialog newInstance(View v) {
        DateDialog dateDialog = new DateDialog();
        dateDialog.txtDate = (EditText) v;
        Bundle bundle = new Bundle();

        dateDialog.setArguments(bundle);

       return dateDialog;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "-" + (monthOfYear+1)+"-"+year;
        InputMethodManager imn = (InputMethodManager) txtDate.getContext().getSystemService(txtDate.getContext().INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(txtDate.getWindowToken(), 0);
        txtDate.setText(date);
        txtDate.clearFocus();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        InputMethodManager imn = (InputMethodManager) txtDate.getContext().getSystemService(txtDate.getContext().INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(txtDate.getWindowToken(), 0);
        txtDate.clearFocus();
    }
}
