package musictag.hytham1.com.mywishlist2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHandler;
import model.MyWish;

public class DisplayWishesActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<MyWish> dbWishes = new ArrayList<>();
    private DatabaseHandler dba;
    private WishAdapter wishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wishes);
        listView = (ListView) findViewById(R.id.listView);
        refreshData();
    }

    public void refreshData(){
        dbWishes.clear();
        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<MyWish> wishesFromDb = dba.getWishes();

        for (int i = 0; i < wishesFromDb.size(); i++) {
            String title = wishesFromDb.get(i).getTitle();
            String content = wishesFromDb.get(i).getContent();
            String dateRecord = wishesFromDb.get(i).getRecordDate();

            int mId = wishesFromDb.get(i).getItemId();

            MyWish myWish = new MyWish();
            myWish.setTitle(title);
            myWish.setContent(content);
            myWish.setRecordDate(dateRecord);
            myWish.setItemId(mId);

            dbWishes.add(myWish);
        }
        dba.close();
        // setup wish adapter

        wishAdapter = new WishAdapter(DisplayWishesActivity.this, R.layout.row_item, dbWishes);
        listView.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();

    }
    public class WishAdapter extends ArrayAdapter<MyWish>{

       Activity activity;
        int layoutResource;
        MyWish wish;
        ArrayList<MyWish> mData = new ArrayList<>();

        public WishAdapter(Activity act, int resource, ArrayList<MyWish> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            return mData.size();
        }


        @Override
        public MyWish getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getPosition(MyWish item) {
            return super.getPosition(item);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null || (row.getTag()) == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();
                holder.mTitle = (TextView) row.findViewById(R.id.name);
                holder.mDate = (TextView) row.findViewById(R.id.dataText);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            holder.myWish = getItem(position);
            holder.mTitle.setText(holder.myWish.getTitle());
            holder.mDate.setText(holder.myWish.getRecordDate());

            final ViewHolder finalHolder = holder;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = finalHolder.myWish.getTitle().toString();
                    String date = finalHolder.myWish.getRecordDate().toString();
                    String content = finalHolder.myWish.getContent().toString();
                    int id = finalHolder.myWish.getItemId();

                    Intent intent = new Intent(DisplayWishesActivity.this , WishDetailActivity.class);
                    intent.putExtra("title" , title);
                    intent.putExtra("content" , content);
                    intent.putExtra("date" , date);
                    intent.putExtra("id" , id);
                    startActivity(intent);
                }
            });


            return row;
        }
        class ViewHolder{
            TextView mTitle;
            TextView mContent;
            TextView mDate;
            int mId;
            MyWish myWish;

        }
    }

}
