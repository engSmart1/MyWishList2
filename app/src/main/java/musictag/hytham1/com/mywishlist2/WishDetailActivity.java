package musictag.hytham1.com.mywishlist2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;

public class WishDetailActivity extends AppCompatActivity {
    TextView title ,content , date;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);
        title = (TextView) findViewById(R.id.tv_detailsTitles);
        content = (TextView) findViewById(R.id.tv_detailsTextView);
        date = (TextView) findViewById(R.id.tv_detailsDateText);

        button = (Button) findViewById(R.id.deleteButton);

        Bundle extras = getIntent().getExtras();

        if (extras != null){

            title.setText(extras.getString("title"));
            date.setText("Created : " + extras.getString("date"));
            content.setText(" \" "  + extras.getString("content") + " \" ");

            final int mId = extras.getInt("id");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.delete(mId);

                    Toast.makeText(WishDetailActivity.this, "Deleted ......", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(WishDetailActivity.this , DisplayWishesActivity.class));
                }
            });

        }
    }

    public void newEntry(View view) {
        startActivity( new Intent(WishDetailActivity.this , MainActivity.class));
    }
}
