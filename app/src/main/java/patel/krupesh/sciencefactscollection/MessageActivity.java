package patel.krupesh.sciencefactscollection;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    static ViewPager pager;
    public static int[] temp_arr_favorite;

    ImageView back_to_list;
    TextView title_content;
/*
    private InterstitialAd interstitialAd;
    private InterstitialAd _interstitialAd;

    private static int loaded = 1;
    private static int loaded1 = 1;
    public static int count=0;

*/
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_for_each_message);
        getSupportActionBar().hide();
        pager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        this.title_content = (TextView) findViewById(R.id.title_content);
        this.back_to_list = (ImageView) findViewById(R.id.back_to_list);

     /*   this.adView = (AdView) findViewById(R.id.av_banner2);
        this.adView.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
              //  MessageActivity.this.adView.setVisibility(8);
            }

            public void onAdLoaded() {
                super.onAdLoaded();
            //    MessageActivity.this.adView.setVisibility(0);
            }
        });  */
       // this.adView.setVisibility(8);
      //  this.adRequest = new Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
       // this.adView.loadAd(this.adRequest);


 /*       loaded =0;

        loaded1=0;

        // Instantiate an InterstitialAd object
       interstitialAd = new InterstitialAd(this, "2087174841554513_2087188394886491");
     //     interstitialAd = new InterstitialAd(this, "VID_HD_16_9_46S_APP_INSTALL#2119061478317191_2119062321650440");



        // Instantiate an InterstitialAd object
        //  _interstitialAd = new InterstitialAd(this, "VID_HD_16_9_46S_APP_INSTALL#2119061478317191_2119063274983678");

        _interstitialAd = new InterstitialAd(this, "2087174841554513_2087175718221092");


*/

        Bundle bundle = getIntent().getBundleExtra("data");
        int type_start = bundle.getInt("type_start");
        this.title_content.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ClearSans-Medium.ttf"));
        int message_position;
        int number_message;
        DatabaseAccess databaseAccess;
        ArrayList<Message> allMessage;
        int i;
        if (type_start == 1) {
            String topic_title = bundle.getString("topic_title_key");
            int topic_position = bundle.getInt("topic_position_key");
            message_position = bundle.getInt("message_position_key");
            number_message = bundle.getInt("number_message_key");
            this.title_content.setText(topic_title);
            databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            allMessage = databaseAccess.getMessagesForEachType(topic_position);
            databaseAccess.close();
            temp_arr_favorite = new int[allMessage.size()];
            for (i = 0; i < allMessage.size(); i++) {
                temp_arr_favorite[i] = ((Message) allMessage.get(i)).getSmsFavorite();
            }
            pager.setAdapter(new PagerAdapters(getApplicationContext(), manager, topic_title, topic_position, message_position, number_message, allMessage));
            pager.setCurrentItem(message_position);
        } else if (type_start == 2) {
            this.title_content.setText("Favorite Hacks");
            databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            allMessage = databaseAccess.getMessageFavorite();
            databaseAccess.close();
            temp_arr_favorite = new int[allMessage.size()];
            for (i = 0; i < allMessage.size(); i++) {
                temp_arr_favorite[i] = ((Message) allMessage.get(i)).getSmsFavorite();
            }
            number_message = bundle.getInt("number_message_key");
            message_position = bundle.getInt("message_position_key");
            pager.setAdapter(new PagerAdapters(getApplicationContext(), manager, message_position, number_message, allMessage));
            pager.setCurrentItem(message_position);
        }
        this.back_to_list.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MessageActivity.this.finish();

           /*     if (loaded == 1) {
                    interstitialAd.show();
                } else {
                    MessageActivity.this.finish();
                }
*/

            }
        });
  /*      int count_click = bundle.getInt("count_click");
        this.interstitialAd = new InterstitialAd(this);
        this.interstitialAd.setAdUnitId(getResources().getString(R.string.ad_unit_interstitial_message_to_topic));
        this.interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            public void onAdClosed() {
                MessageActivity.this.finish();
            }
        });
        requestNewInterstitial();

*/
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));
        }


        // Implements change listener with manual page count increase.
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {



                // Increase page count for the AdInterstitial
                //   adInterstitial.increaseShownPageCount();

           //     Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();

       /*        count++;

                if(count%7==0){

                    //      showNativeAd();
                    if (loaded1 == 1) {
                        _interstitialAd.show();
                    }





                }  */

                //      Toast.makeText(getApplicationContext(),"Page Number"+position ,Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

/*
        // Set listeners for the Interstitial Ad
        _interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback


            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                _interstitialAd.loadAd();


            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(getApplicationContext(), "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.
                loaded1=1;
                //  interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });


        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(getApplicationContext(), "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.
                loaded=1;
                //  interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown

    //   AdSettings.addTestDevice("5564a774-445e-405b-b92f-d5cc0ee815db");

        interstitialAd.loadAd();
        _interstitialAd.loadAd();





        this.back_to_list.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                MessageActivity.this.finish();

                if (loaded == 1) {
                    interstitialAd.show();
                } else {
                    MessageActivity.this.finish();
                }


                //   interstitialAd.show();

            }
        });


*/










    }




    public void onBackPressed() {

        MessageActivity.this.finish();
   /*     if (loaded==1) {
            this.interstitialAd.show();
        } else {
            finish();
        }  */
    }
}
