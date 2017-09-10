package com.example.android.securenotes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class add_new_note extends AppCompatActivity {

    EditText note_id,title,note;
    Button add_note;
    ListView listView;
    List<note> mylist;
    AppAdapter adapter;
    DatabaseHandler db;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

      // add_note= (Button) findViewById(R.id.add_note);
       note_id = (EditText) findViewById(R.id.note_id);
        title = (EditText) findViewById(R.id.title);
        note = (EditText) findViewById(R.id.note);
        listView = (ListView) findViewById(R.id.list_item);
        db = new DatabaseHandler(this);
       FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
       fab1.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {

               db.addNote(new note(title.getText().toString(), note.getText().toString()));
               Toast.makeText(getApplicationContext(), "Note saved successfully!", Toast.LENGTH_LONG).show();
               load();

               finish();


               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       .setAction("Action",null)
                       .show();

           }
       });

     /*   add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.addNote(new note(title.getText().toString(), note.getText().toString()));
                //Toast.makeText(getApplicationContext(), "note saved successfully!", Toast.LENGTH_LONG).show();
                clear();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });*/


    }

    public void load() {

        List<note> list = db.getAllNotes();
        mylist = list;
        adapter = new AppAdapter();
        this.listView.setAdapter(adapter);

        //  Toast.makeText(getApplicationContext(), mylist.size()+"", Toast.LENGTH_LONG).show();

    }
    public void clear(){
        note_id.setText("");
        title.setText("");
        note.setText("");
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_item, null);
                new AppAdapter.ViewHolder(convertView);
            }
            MainActivity.AppAdapter.ViewHolder holder = (MainActivity.AppAdapter.ViewHolder) convertView.getTag();

            holder.tv_id.setText(mylist.get(position).get_id() + "");
            holder.tv_name.setText(mylist.get(position).get_title());
            holder.tv_num.setText(mylist.get(position).get_note());


            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note_id.setText(mylist.get(position).get_id() + "");
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
