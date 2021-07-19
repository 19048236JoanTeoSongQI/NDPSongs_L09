package sg.edu.rp.c346.id19048236.ndpsongs_l09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    Button btnShowAll;
    ListView lvSong;
    ArrayList<Songs> al;
    ArrayAdapter<Songs> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnShowAll = findViewById(R.id.btnShowStar);
        lvSong = findViewById(R.id.lv);
        al = new ArrayList<Songs>();

        DBHelper dbh = new DBHelper(this);
        al = dbh.getAllSongs();
        dbh.close();

        aa = new ArrayAdapter<Songs>(this,
                android.R.layout.simple_list_item_1, al);
        lvSong.setAdapter(aa);

        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Songs data = al.get(position);
                Intent i = new Intent(ListActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ListActivity.this);
                al.clear();

                al.addAll(dbh.getAllSongByStar(5));
                aa.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(ListActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();
    }


}