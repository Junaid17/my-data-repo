package com.example.jmush.eventbudgetmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmush on 1/3/17.
 */

public class MyDialogFragment extends DialogFragment {


    private Button update,cancel;
    private EditText etAmount;
    private DBAdapter dbh;
    String ntid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_dialog, container, false);
        getDialog().setTitle("Enter Amount To Update!");
        setCancelable(false);
        dbh = new DBAdapter(getContext());


        // Get References of Views


        update= (Button) rootView.findViewById(R.id.btUpdate);
        cancel= (Button) rootView.findViewById(R.id.btCancel);
        etAmount= (EditText) rootView.findViewById(R.id.etAmount);
        ntid = getArguments().getString("NTID");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Amount = etAmount.getText().toString();
                int amt = Integer.parseInt(Amount);


                // check if any of the fields are vaccant
                if (Amount.equals("")) {
                    Toast.makeText(getContext(), "Field Vacant",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                     RetreiveName retreiveName=new RetreiveName();
                    // Save the Data in Database
                    retreiveName.setNtid(ntid);
                    retreiveName.setAmount(amt);
                        long yes = dbh.updateFunds(retreiveName);
                        if (yes != 0) {
                            dismiss();
                            Toast.makeText(getContext(),
                                    "Successfully Added ",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(),
                                    FundsActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(
                                    getContext(),
                                    "Problem with Adding please Try again ",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        });
        return rootView;
    }

}