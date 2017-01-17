package com.example.jmush.eventbudgetmanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by jmush on 12/29/16.
 */

public class FundsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Toolbar toolbar;
    public TextView textView, tvTotalAmount;
    private ListView listView;

    public DBAdapter db;
    private ArrayList<RetreiveName> arrayList;
    private UsersAdapter adapter;
    int check=0;
    // private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);
        listView= (ListView) findViewById(R.id.fundslist);
        db = new DBAdapter(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        tvTotalAmount= (TextView) findViewById(R.id.tvTotalAmount);
        int i=db.getTotalOfAmount();
        tvTotalAmount.setText(""+i);
        List<RetreiveName> nameList = new ArrayList<RetreiveName>();
        ArrayList<RetreiveName> retreiveNames = db.getAllNames();
        if(retreiveNames!=null && retreiveNames.size()>0)
        {

            adapter = new UsersAdapter(FundsActivity.this,R.layout.activity_fundslist,retreiveNames);
            listView.setAdapter(adapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_funds, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        textView= (TextView) findViewById(R.id.tvTotalAmount);
       /* List<RetreiveFund> fundlist=db.getAllFunds();

        Iterator iterator = fundlist.iterator();
        while (iterator.hasNext()){
            RetreiveFund rf = (RetreiveFund) iterator.next();
            Log.d("amt",rf.getAmount());
        }
*/
        List<RetreiveName> list=db.getAllNames();

        String[] nameList=new String[list.size()];

        for(int i=0;i<list.size();i++){
            nameList[i]=list.get(i).getNtid(); //create array of name
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                 android.R.layout.simple_spinner_item,nameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       /* int x=spinner.getSelectedItemPosition();
        spinner.setSelection(x, false);*/
        spinner.setOnItemSelectedListener(this);
 return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        check=check+1;
        if(check>1) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();


            Bundle ntidBundle = new Bundle();
            ntidBundle.putString("NTID", item);


            // Showing selected spinner item
               /* Toast.makeText(FundsActivity.this, "Selected  : " + item,
                        Toast.LENGTH_LONG).show();*/
            FragmentManager fragmentManager = getSupportFragmentManager();
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.setArguments(ntidBundle);
            myDialogFragment.show(fragmentManager, "My Fragment");
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class UsersAdapter extends ArrayAdapter<RetreiveName> {
        ArrayList<RetreiveName> nameList = new ArrayList<>();
        private Context context;

        public UsersAdapter(Context context, int textViewResourceId, ArrayList<RetreiveName> objects) {
            super(context, textViewResourceId, objects);
            nameList = objects;
            this.context=context;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        private  class Holder {
            public TextView NameView;
            public TextView AmountView;
            public ImageView ivEdit;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            View view = convertView;
            if(convertView==null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_fundslist, null);
                holder.NameView = (TextView) view.findViewById(R.id.tViewName);
                holder.AmountView= (TextView) view.findViewById(R.id.tViewAmount);
                holder.ivEdit = (ImageView) view.findViewById(R.id.iveditfunds);
                holder.ivEdit.setOnClickListener(editListener);
                view.setTag(holder);
            }
            else

                holder = (FundsActivity.UsersAdapter.Holder) view.getTag();
            System.out.println("Position [" + position + "]");
            RetreiveName retreiveName = nameList.get(position);
            holder.NameView.setText(retreiveName.getMemberName());
            holder.AmountView.setText(""+retreiveName.getAmount());
            holder.ivEdit.setTag(retreiveName);
            return view;

        }

        View.OnClickListener editListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetreiveName details= (RetreiveName) v.getTag();
                String ntid=details.getNtid();
                Bundle ntidBundle = new Bundle();
                ntidBundle.putString("NTID",ntid);

                FragmentManager fragmentManager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.setArguments(ntidBundle);
                myDialogFragment.show(fragmentManager, "My Fragment");
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();

    }

}