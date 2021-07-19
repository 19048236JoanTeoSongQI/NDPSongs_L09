package sg.edu.rp.c346.id19048236.ndpsongs_l09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edTitle, edSinger, edYear;
    Button btnInsert, btnShow;
    RadioGroup rgStars;
    RadioButton r1,r2,r3,r4,r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTitle=findViewById(R.id.etTitle);
        edSinger=findViewById(R.id.etSingers);
        edYear=findViewById(R.id.etYear);
        rgStars=findViewById(R.id.rgStar);
        btnInsert=findViewById(R.id.btnInsert);
        btnShow=findViewById(R.id.btnShow);
        r1=findViewById(R.id.rgStar1);
        r2=findViewById(R.id.rgStar2);
        r3=findViewById(R.id.rgStar3);
        r4=findViewById(R.id.rgStar4);
        r5=findViewById(R.id.rgStar5);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListActivity.class);
                startActivity(i);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertSong(edTitle.getText().toString(), edSinger.getText().toString(), Integer.parseInt(edYear.getText().toString()), Integer.parseInt(String.valueOf(stars)));

                if (inserted_id != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}