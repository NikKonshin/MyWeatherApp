package com.example.myweatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogBuilderFragment extends DialogFragment {
    private String city;

    public void setCity(String city) {
        this.city = city;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_dialog)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        ((MainActivity)getActivity()).onDialogResult(getString(R.string.yes));
                    }
                }).setMessage("В городе: " + city + " \n" + "На улице холодно, одевайтесь теплее");
        return builder.create();
    }
}
