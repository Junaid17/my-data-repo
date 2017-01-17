package com.example.jmush.eventbudgetmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmush on 12/23/16.
 */

public class MemberActivity extends AppCompatActivity {

    Button btnadd;
    private SessionManager sessionManager;
    public Toolbar toolbar;
    private ListView listView;
    public DBAdapter db;
    private ArrayList<RetreiveName> arrayList;
    private ImageView ivEdit,ivDelete;
   private UsersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        db=new DBAdapter(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(this);
        btnadd = (Button) findViewById(R.id.btn_addmember);

        List<RetreiveName> nameList = new ArrayList<RetreiveName>();
        listView = (ListView) findViewById(R.id.lvMembers);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.isLoggedIn()) {

                    Intent intent = new Intent(MemberActivity.this, AddMemberActivity.class);
                    startActivity(intent);

                } else {


                    Toast.makeText(MemberActivity.this, "Please Login to add member", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ArrayList<RetreiveName> retreiveNames = db.getAllNames();



        for(RetreiveName nme : retreiveNames){

            Log.d("NTID .....",nme.getNtid());
            Log.d("NAME .....",nme.getMemberName());
            Log.d("PHONE .....",nme.getPhone());
            Log.d("AMOUNT......", String.valueOf(nme.getAmount()));

        }


        if(retreiveNames!=null && retreiveNames.size()>0)
        {

             adapter = new UsersAdapter(MemberActivity.this,R.layout.activity_listview,retreiveNames);
            listView.setAdapter(adapter);
        }

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
            public ImageView ivEdit;
            public ImageView ivDelete;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            View view = convertView;
            if(convertView==null) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_listview, null);
                TextView tViewName = (TextView) view.findViewById(R.id.tViewName);
                //RetreiveName retreiveName=nameList.get(position);
                ivEdit = (ImageView) view.findViewById(R.id.ivedit);
                ivDelete = (ImageView) view.findViewById(R.id.ivdelete);

                // tViewName.setText(retreiveName.getMemberName());

                holder.NameView = tViewName;
                holder.ivEdit = ivEdit;
                holder.ivDelete = ivDelete;
                holder.ivEdit.setOnClickListener(editListener);
                holder.ivDelete.setOnClickListener(deleteListner);

                view.setTag(holder);
            }
        else

                holder = (Holder) view.getTag();
                System.out.println("Position [" + position + "]");
                RetreiveName retreiveName = nameList.get(position);
                holder.NameView.setText(retreiveName.getMemberName());
                holder.ivEdit.setTag(retreiveName);
                holder.ivDelete.setTag(retreiveName);




            return view;

        }

        View.OnClickListener editListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetreiveName details= (RetreiveName) v.getTag();
                Intent intent=new Intent(context,UpdateActivity.class);
                intent.putExtra("NTID",details.getNtid());
                intent.putExtra("NAME",details.getMemberName());
                intent.putExtra("PHONE",details.getPhone());
                startActivity(intent);




                Toast.makeText(context,"Edit Clicked",Toast.LENGTH_LONG).show();
            }
        };

        View.OnClickListener deleteListner=new View.OnClickListener(){ @Override
        public void onClick(View v) {

            RetreiveName ntid= (RetreiveName) v.getTag();
            db.deleteMember(ntid);
            //nameList.remove(ntid);    notifyDataSetChanged();
            nameList=db.getAllNames();
            updateResults(nameList);



            Toast.makeText(context,"Delete Clicked"+ntid.getNtid(),Toast.LENGTH_LONG).show();
        }
        };
        public void updateResults(ArrayList<RetreiveName> results) {
            nameList = results;
            //Triggers the list update
           // notifyDataSetChanged();
            adapter = new UsersAdapter(MemberActivity.this,R.layout.activity_listview,nameList);
            listView.setAdapter(adapter);
            notifyDataSetChanged();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (sessionManager.isLoggedIn()) {

            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);

        } else {
            menu.getItem(1).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.login:

                Intent intent = new Intent(MemberActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:

                sessionManager.logoutUser();
                Toast.makeText(MemberActivity.this,
                        "you logged out from your A/c",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                return onOptionsItemSelected(item);


        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent  intent=new Intent(MemberActivity.this,MainActivity.class);
        startActivity(intent);

    }


}
