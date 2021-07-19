package sg.edu.rp.c346.id19048236.ndpsongs_l09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    EditText etID, edTitle, edSinger, edYear;
    Button btnUpdate, btnDel, btnCancel;
    RadioGroup rgStars;
    RadioButton r1, r2, r3, r4, r5;
    Songs data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etID=findViewById(R.id.etID);
        edTitle=findViewById(R.id.etTitle);
        edSinger=findViewById(R.id.etSingers);
        edYear=findViewById(R.id.etYear);
        rgStars=findViewById(R.id.rgStar);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDel=findViewById(R.id.btnDel);
        btnCancel=findViewById(R.id.btnCancel);
        r1 = findViewById(R.id.rgStar1);
        r2 = findViewById(R.id.rgStar2);
        r3 = findViewById(R.id.rgStar3);
        r4 = findViewById(R.id.rgStar4);
        r5 = findViewById(R.id.rgStar5);

        Intent i = getIntent();
        data = (Songs) i.getSerializableExtra("data");

        etID.setText(String.valueOf(data.get_id()));
        edTitle.setText(data.getTitle());
        edSinger.setText(data.getSingers());
        edYear.setText(String.valueOf(data.getYear()));

        if (data.getStars() == 1)
        {
            r1.setChecked(true);
        }
        else if (data.getStars() == 2)
        {
            r2.setChecked(true);
        }
        else if (data.getStars() == 3)
        {
            r3.setChecked(true);
        }
        else if (data.getStars() == 4)
        {
            r4.setChecked(true);
        }
        else if (data.getStars() == 5)
        {
            r5.setChecked(true);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.set_id(Integer.parseInt(etID.getText().toString()));
                data.setTitle(edTitle.getText().toString());
                data.setSingers(edSinger.getText().toString());
                data.setYear(Integer.parseInt(edYear.getText().toString()));

                int stars = 0;
                if (r1.isChecked())
                {
                    stars = 1;
                }
                else if (r2.isChecked())
                {
                    stars = 2;
                }
                else if (r3.isChecked())
                {
                    stars = 3;
                }
                else if (r4.isChecked())
                {
                    stars = 4;
                }
                else if (r5.isChecked())
                {
                    stars = 5;
                }

                data.setStars(stars);
                dbh.updateSong(data);
                dbh.close();
                Intent i = new Intent(EditActivity.this, ListActivity.class);
                startActivity(i);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.get_id());
                Intent i = new Intent(EditActivity.this, ListActivity.class);
                startActivity(i);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this,
                        ListActivity.class);
                startActivity(i);
            }
        });


    }
}