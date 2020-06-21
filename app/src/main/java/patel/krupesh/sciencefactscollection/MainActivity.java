package patel.krupesh.sciencefactscollection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;


public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor edit;
    ImageView img_infor;
    ImageView img_open_favorite;
   // InterstitialAd interstitialAd;
    int isShowRate;
    ListView listView;
    LinearLayout mLinearLayout;
    NodeList nList;
    SharedPreferences pre;
    boolean search = false;
    ArrayList<String> search_list;
    SearchView search_view;
    ArrayList<String> sms_list;
    TextView title_main;
    String topic;
    int type_num;
    MyAdapter_dataList adapter;
    int backgroundId;
    int count_ads;
  //  InterstitialAd interstitialAd;

    private static int loaded = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getDataFromXML();
        this.search_list = new ArrayList();
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ClearSans-Medium.ttf");
        this.title_main = (TextView) findViewById(R.id.show_type_title);
        this.title_main.setTypeface(face);
        this.pre = getSharedPreferences("my_data", 0);
        this.edit = this.pre.edit();
        this.backgroundId = this.pre.getInt("bgId", 2);
        this.listView = (ListView) findViewById(R.id.listView);
        this.search_view = (SearchView) findViewById(R.id.search_view);
       this.img_open_favorite = (ImageView) findViewById(R.id.favorite_list);
     /*   this.img_infor = (ImageView) findViewById(R.id.info_main);       */
        this.mLinearLayout = (LinearLayout) findViewById(R.id.background_main_layout);

      //  createDailog();

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }

        this.adapter = new MyAdapter_dataList(this, R.layout.list_data_item, this.sms_list);
        this.listView.setAdapter(this.adapter);

        //loaded =0;
       //  Instantiate an InterstitialAd object
       //    interstitialAd = new InterstitialAd(this, "VID_HD_16_9_46S_APP_INSTALL#2119061478317191_2119062321650440");



/*
        final AdView adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.unit_id_main_banner));
        adView.setAdSize(AdSize.SMART_BANNER);




      final LinearLayout adLinLay = (LinearLayout) findViewById(R.id.addlayout);
        adLinLay.addView(adView);

        final AdRequest.Builder adReq = new AdRequest.Builder();
        final AdRequest adRequest = adReq.build();
        adView.loadAd(adRequest);

        this.interstitialAd = new InterstitialAd(this);
        this.interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_interstitial_main));
        this.interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            public void onAdClosed() {
             //   MainActivity.this.finish();
                MainActivity.this.doOpenContentActivity(MainActivity.this.type_num, MainActivity.this.topic);

            }
        });
        requestNewInterstitial();

*/
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.count_ads++;
                if (MainActivity.this.search) {
                    MainActivity.this.type_num = MainActivity.this.sms_list.indexOf(MainActivity.this.search_list.get(position));
                } else {
                    MainActivity.this.type_num = position;
                }
                MainActivity.this.topic = (String) MainActivity.this.sms_list.get(MainActivity.this.type_num);
           /*     if (MainActivity.this.interstitialAd.isLoaded()) {
                    MainActivity.this.interstitialAd.show();
                } else {
                    MainActivity.this.doOpenContentActivity(MainActivity.this.type_num, MainActivity.this.topic);
               }  */

                MainActivity.this.doOpenContentActivity(MainActivity.this.type_num, MainActivity.this.topic);
            }
        });


        this.img_open_favorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, FavoriteListActivity.class));
            }
        });
    /*    this.img_infor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
              //  MainActivity.this.ad.show();
            }
        });   */
       this.search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                MainActivity.this.search_list.clear();
                if (newText.length() == 0) {
                    MainActivity.this.search_list.addAll(MainActivity.this.sms_list);
                } else {
                    Iterator it = MainActivity.this.sms_list.iterator();
                    while (it.hasNext()) {
                        String temp = (String) it.next();
                        if (temp.toLowerCase().indexOf(newText) != -1) {
                            MainActivity.this.search_list.add(temp);
                        }
                    }
                    MainActivity.this.search = true;
                }
                MainActivity.this.adapter = new MyAdapter_dataList(MainActivity.this.getApplicationContext(), R.layout.list_data_item, MainActivity.this.search_list);
                MainActivity.this.listView.setAdapter(MainActivity.this.adapter);
                return false;
            }
        });


        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(1) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
    }

    public void getDataFromXML() {
        try {
            this.sms_list = new ArrayList();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getAssets().open("smslist.xml"));
            doc.getDocumentElement().normalize();
            this.nList = doc.getElementsByTagName("lesson");
            for (int i = 0; i < this.nList.getLength(); i++) {
                Node node = this.nList.item(i);
                String item = null;
                if (node.getNodeType() == (short) 1) {
                    item = getValue("name", (Element) node);
                }
                this.sms_list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getValue(String tag, Element element) {
        return element.getElementsByTagName(tag).item(0).getChildNodes().item(0).getNodeValue();
    }

    public void doOpenContentActivity(int position, String topic) {
        Intent myIntent = new Intent(this, TopicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position_key", position);
        bundle.putString("topic_key", topic);
      //  bundle.putInt("count_ads", count_ads);
        myIntent.putExtra("data", bundle);
        startActivity(myIntent);
    }


  /*  private void requestNewInterstitial() {
        this.interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }
*/


 /*   public void createDailog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_show_infor, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setIcon(R.drawable.icon512);
        dialogBuilder.setTitle("Life Hacks");
        TextView tv_infor = (TextView) dialogView.findViewById(R.id.dialog_message);
        TextView tv_exit = (TextView) dialogView.findViewById(R.id.tv_exit_app);
        TextView tv_rate = (TextView) dialogView.findViewById(R.id.tv_rate_app);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel_app);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/ClearSans-Regular.ttf");
        tv_infor.setTypeface(face);
        tv_exit.setTypeface(face);
        tv_rate.setTypeface(face);
        tv_cancel.setTypeface(face);
        this.adView2 = (AdView) dialogView.findViewById(R.id.av_banner2);
        this.adView2.setVisibility(8);
        tv_exit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
        tv_rate.setOnClickListener(new OnClickListener() {
            String package_name = MainActivity.this.getPackageName();

            public void onClick(View view) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.package_name)));
                } catch (ActivityNotFoundException e) {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + this.package_name)));
                }
                MainActivity.this.ad.cancel();
            }
        });
        tv_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.ad.cancel();
            }
        });
        this.ad = dialogBuilder.create();
    }

    public void createDailog2() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_show_infor, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setIcon(R.drawable.icon512);
        dialogBuilder.setTitle("Life Hacks");
        TextView tv_exit = (TextView) dialogView.findViewById(R.id.tv_exit_app);
        tv_exit.setText("English Idioms");
        ((TextView) dialogView.findViewById(R.id.tv_rate_app)).setVisibility(8);
        TextView tv_cancel = (TextView) dialogView.findViewById(R.id.tv_cancel_app);
        tv_cancel.setText("Rate app");
        this.adView3 = (AdView) dialogView.findViewById(R.id.av_banner2);
        this.adView3.setAdListener(new AdListener() {
            public void onAdClosed() {
                MainActivity.this.adView3.setVisibility(8);
                super.onAdClosed();
            }

            public void onAdLoaded() {
                MainActivity.this.adView3.setVisibility(0);
                super.onAdLoaded();
            }
        });
        this.adRequest3 = new Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        this.adView3.loadAd(this.adRequest);
        this.adView3.setVisibility(8);
        tv_exit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=example.com.hacklife.englishidioms")));
                } catch (ActivityNotFoundException e) {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=example.com.hacklife.englishidioms")));
                }
                MainActivity.this.ad2.cancel();
            }
        });
        tv_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + MainActivity.this.getPackageName())));
                } catch (ActivityNotFoundException e) {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
                MainActivity.this.ad2.cancel();
            }
        });
        this.ad2 = dialogBuilder.create();
    }
*/

}
