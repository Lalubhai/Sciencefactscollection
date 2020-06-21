package patel.krupesh.sciencefactscollection;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class TopicActivity extends AppCompatActivity {
    LinearLayout adContainer;
    MyAdapter_dataList adapter;
    ArrayList<Message> allMessage;
    int count_ads;
    int count_click;
    ImageView img_icon2;
    ListView list_sms_for_topic;
    boolean search = false;
    ArrayList<String> search_list;
    SearchView search_view;
    ArrayList<String> sms_list;
    int topic_position;
    String topic_title;
    TextView tv_topic_title;
    int type_num;

    private class LoadDataFromDatabase extends AsyncTask<Void, Void, Void> {
        private LoadDataFromDatabase() {
        }

        protected Void doInBackground(Void... params) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(TopicActivity.this);
            databaseAccess.open();
            TopicActivity.this.allMessage = databaseAccess.getMessagesForEachType(TopicActivity.this.topic_position);
            TopicActivity.this.search_list = new ArrayList();
            databaseAccess.close();
            TopicActivity.this.sms_list = new ArrayList();
            for (int i = 0; i < TopicActivity.this.allMessage.size(); i++) {
                TopicActivity.this.sms_list.add(((Message) TopicActivity.this.allMessage.get(i)).getSmsBody());
            }
            TopicActivity.this.adapter = new MyAdapter_dataList(TopicActivity.this, R.layout.list_data_item, TopicActivity.this.sms_list);
            return null;

        }

        protected void onPostExecute(Void result) {
            TopicActivity.this.list_sms_for_topic.setAdapter(TopicActivity.this.adapter);
            super.onPostExecute(result);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_each_topic);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getBundleExtra("data");
        this.topic_position = bundle.getInt("position_key");
        this.topic_title = bundle.getString("topic_key");
       // this.count_ads = bundle.getInt("count_ads");
        this.img_icon2 = (ImageView) findViewById(R.id.icon_app_main2);
        this.tv_topic_title = (TextView) findViewById(R.id.show_type_title2);
        this.list_sms_for_topic = (ListView) findViewById(R.id.listView2);
        this.search_view = (SearchView) findViewById(R.id.search_view2);
        this.tv_topic_title.setText(this.topic_title);
        this.tv_topic_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ClearSans-Medium.ttf"));

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }
      //  Toast.makeText(getApplicationContext(),TopicActivity.this.allMessage.size()+"",Toast.LENGTH_LONG).show();


//        Log.e("tag", String.valueOf(allMessage.size()));
        new LoadDataFromDatabase().execute(new Void[0]);

/*        final AdView adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.unit_id_topic_banner));
        adView.setAdSize(AdSize.SMART_BANNER);

        final LinearLayout adLinLay2 = (LinearLayout) findViewById(R.id.addlayout2);
        adLinLay2.addView(adView);

        final AdRequest.Builder adReq = new AdRequest.Builder();
        final AdRequest adRequest = adReq.build();
        adView.loadAd(adRequest);
*/

        this.list_sms_for_topic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TopicActivity topicActivity = TopicActivity.this;
                topicActivity.count_click++;
                if (TopicActivity.this.search) {
                    TopicActivity.this.type_num = TopicActivity.this.sms_list.indexOf(TopicActivity.this.search_list.get(position));
                } else {
                    TopicActivity.this.type_num = position;
                }
               Intent intent1 = new Intent(TopicActivity.this, MessageActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type_start", 1);
                bundle1.putString("topic_title_key", TopicActivity.this.topic_title);
                bundle1.putInt("topic_position_key", TopicActivity.this.topic_position);
                bundle1.putInt("message_id", ((Message) TopicActivity.this.allMessage.get(TopicActivity.this.type_num)).getMsmId());
                bundle1.putInt("message_position_key", TopicActivity.this.type_num);
                bundle1.putInt("count_click", TopicActivity.this.count_click);
                bundle1.putInt("number_message_key", TopicActivity.this.allMessage.size());
                intent1.putExtra("data", bundle1);
               TopicActivity.this.startActivity(intent1);
            }
        });
        this.search_view.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                if (!(TopicActivity.this.search_list == null || TopicActivity.this.sms_list == null)) {
                    TopicActivity.this.search_list.clear();
                    if (newText.length() == 0) {
                        TopicActivity.this.search_list.addAll(TopicActivity.this.sms_list);
                    } else {
                        int i = 0;
                        Iterator it = TopicActivity.this.sms_list.iterator();
                        while (it.hasNext()) {
                            String temp = (String) it.next();
                            i++;
                            if (temp == null) {
                                Log.e(Setting.TAG, "null at: " + i);
                            } else if (temp.toLowerCase().indexOf(newText) != -1) {
                                TopicActivity.this.search_list.add(temp);
                            }
                        }
                        TopicActivity.this.search = true;
                    }
                    TopicActivity.this.adapter = new MyAdapter_dataList(TopicActivity.this.getApplicationContext(), R.layout.list_data_item, TopicActivity.this.search_list);
                    TopicActivity.this.list_sms_for_topic.setAdapter(TopicActivity.this.adapter);
                }
                return false;
            }
        });
        this.img_icon2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TopicActivity.this.finish();
            }
        });
    }
}
