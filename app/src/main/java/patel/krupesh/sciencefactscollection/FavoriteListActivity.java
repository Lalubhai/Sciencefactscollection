package patel.krupesh.sciencefactscollection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;

public class FavoriteListActivity extends AppCompatActivity {

    MyAdapter_dataList adapter;
    ArrayList<Message> allMessage;
    ImageView img_icon2;
    int list_size = 0;
    ListView list_sms_favorite;
    int message_position;
    ProgressDialog progress = null;
    boolean search = false;
    ArrayList<String> search_list;
    SearchView search_view;
    ArrayList<String> sms_list;
    int topic_position;
    String topic_title;
    TextView tv_notify_empty;
    TextView tv_topic_title;
    int type_num;

    private class LoadDataFromDatabase extends AsyncTask<Void, Void, Void> {
        private LoadDataFromDatabase() {
        }

        protected Void doInBackground(Void... params) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(FavoriteListActivity.this);
            databaseAccess.open();
            FavoriteListActivity.this.allMessage = databaseAccess.getMessageFavorite();
            databaseAccess.close();
            return null;
        }

        protected void onPostExecute(Void result) {
            if (FavoriteListActivity.this.allMessage.size() > 0) {
                FavoriteListActivity.this.tv_notify_empty.setVisibility(View.GONE);
                FavoriteListActivity.this.list_sms_favorite.setVisibility(View.VISIBLE);
            } else {
                FavoriteListActivity.this.tv_notify_empty.setVisibility(View.VISIBLE);
                FavoriteListActivity.this.list_sms_favorite.setVisibility(View.GONE);
            }
            if (FavoriteListActivity.this.list_size != FavoriteListActivity.this.allMessage.size()) {
                FavoriteListActivity.this.sms_list = new ArrayList();
                for (int i = 0; i < FavoriteListActivity.this.allMessage.size(); i++) {
                    FavoriteListActivity.this.sms_list.add(((Message) FavoriteListActivity.this.allMessage.get(i)).getSmsBody());
                }
                FavoriteListActivity.this.search_list = new ArrayList();
                FavoriteListActivity.this.adapter = new MyAdapter_dataList(FavoriteListActivity.this, R.layout.list_data_item, FavoriteListActivity.this.sms_list);
                FavoriteListActivity.this.list_sms_favorite.setAdapter(FavoriteListActivity.this.adapter);
                FavoriteListActivity.this.adapter.notifyDataSetChanged();
                FavoriteListActivity.this.list_size = FavoriteListActivity.this.allMessage.size();
            }
            FavoriteListActivity.this.list_sms_favorite.setAdapter(FavoriteListActivity.this.adapter);
            FavoriteListActivity.this.progress.dismiss();
            super.onPostExecute(result);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        this.progress = ProgressDialog.show(this, null, "Loading text messages...");
        getSupportActionBar().hide();
        this.img_icon2 = (ImageView) findViewById(R.id.icon_back_to_main);
        this.tv_topic_title = (TextView) findViewById(R.id.show_type_title3);
        this.list_sms_favorite = (ListView) findViewById(R.id.listView3);
        this.tv_notify_empty = (TextView) findViewById(R.id.notify_empty);
        this.search_view = (SearchView) findViewById(R.id.search_view3);
        this.tv_topic_title.setText("Favorite Hacks");
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ClearSans-Medium.ttf");
        this.tv_topic_title.setTypeface(face);
        this.tv_notify_empty.setTypeface(face);
        this.list_sms_favorite.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (FavoriteListActivity.this.search) {
                    FavoriteListActivity.this.type_num = FavoriteListActivity.this.sms_list.indexOf(FavoriteListActivity.this.search_list.get(position));
                } else {
                    FavoriteListActivity.this.type_num = position;
                }
                Intent intent1 = new Intent(FavoriteListActivity.this, MessageActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type_start", 2);
                bundle1.putInt("message_id", ((Message) FavoriteListActivity.this.allMessage.get(FavoriteListActivity.this.type_num)).getMsmId());
                bundle1.putInt("message_position_key", FavoriteListActivity.this.type_num);
                bundle1.putInt("number_message_key", FavoriteListActivity.this.allMessage.size());
                intent1.putExtra("data", bundle1);
                FavoriteListActivity.this.startActivity(intent1);
            }
        });
        this.img_icon2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FavoriteListActivity.this.finish();
            }
        });
        this.search_view.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                if (!(FavoriteListActivity.this.search_list == null || FavoriteListActivity.this.sms_list == null)) {
                    newText = newText.toLowerCase();
                    FavoriteListActivity.this.search_list.clear();
                    if (newText.length() == 0) {
                        FavoriteListActivity.this.search_list.addAll(FavoriteListActivity.this.sms_list);
                    } else {
                        Iterator it = FavoriteListActivity.this.sms_list.iterator();
                        while (it.hasNext()) {
                            String temp = (String) it.next();
                            if (temp.toLowerCase().indexOf(newText) != -1) {
                                FavoriteListActivity.this.search_list.add(temp);
                            }
                        }
                        FavoriteListActivity.this.search = true;
                    }
                    FavoriteListActivity.this.adapter = new MyAdapter_dataList(FavoriteListActivity.this.getApplicationContext(), R.layout.list_data_item, FavoriteListActivity.this.search_list);
                    FavoriteListActivity.this.list_sms_favorite.setAdapter(FavoriteListActivity.this.adapter);
                }
                return false;
            }
        });
    }

    protected void onResume() {
        new LoadDataFromDatabase().execute(new Void[0]);
        super.onResume();
    }
}
