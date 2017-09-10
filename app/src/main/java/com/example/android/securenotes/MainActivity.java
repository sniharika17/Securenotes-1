package com.example.android.securenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText note_id,title,note;
    ListView listView;
    List<note> mylist;
    DatabaseHandler db;
    AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        note_id = (EditText) findViewById(R.id.note_id);
        title = (EditText) findViewById(R.id.title);
        note = (EditText) findViewById(R.id.note);
        listView = (ListView) findViewById(R.id.list_item);
        db = new DatabaseHandler(this);


        load();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),add_new_note.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void load(){

        List<note> list = db.getAllNotes();
        mylist = list;
        adapter =new  AppAdapter();
        this.listView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), mylist.size()+"", Toast.LENGTH_LONG).show();

    }
    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mylist.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_item, null);
                new AppAdapter.ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.tv_id.setText(mylist.get(position).get_id() + "");
            holder.tv_name.setText(mylist.get(position).get_title());
            holder.tv_num.setText(mylist.get(position).get_note());


            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note_id.setText(mylist.get(position).get_id()+"");
                    title.setText(mylist.get(position).get_title());
                    note.setText(mylist.get(position).get_note());
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView tv_id;
            TextView tv_name;
            TextView tv_num;
            LinearLayout row;

            public ViewHolder(View view) {
                tv_id = (TextView) view.findViewById(R.id.note_id);
                tv_name = (TextView) view.findViewById(R.id.title);
                tv_num = (TextView) view.findViewById(R.id.note);
                row = (LinearLayout) view.findViewById(R.id.row);
                view.setTag(this);
            }
        }
    }
}
