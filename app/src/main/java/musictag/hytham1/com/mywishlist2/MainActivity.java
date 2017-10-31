package musictag.hytham1.com.mywishlist2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {
    EditText content , title;
    Button saveButton;
    DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (EditText) findViewById(R.id.et_titleEditText);
        content = (EditText) findViewById(R.id.et_wishEditText);
        saveButton = (Button) findViewById(R.id.saveButton);
        dba = new DatabaseHandler(MainActivity.this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDb();
            }
        });


    }
     private void saveToDb(){

        MyWish myWish = new MyWish();
        myWish.setTitle(title.getText().toString().trim());
        myWish.setContent(content.getText().toString().trim());

         if(myWish.getContent().equals("") || myWish.getTitle().equals("")){
             Toast.makeText(this, "Please enter info ..", Toast.LENGTH_SHORT).show();
             return;
         }

        dba.addWishes(myWish);
        dba.close();
        //clear

         title.setText("");
         content.setText("");

        startActivity( new Intent( MainActivity.this , DisplayWishesActivity.class));

    }
}
