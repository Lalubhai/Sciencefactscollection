package patel.krupesh.sciencefactscollection;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ItemShowFragment extends Fragment {
    Context context;
    int coutn_message;
    private int favorite;
    ImageView img_copy;
    ImageView img_favorite;
    ImageView img_random;
    ImageView img_share;
    private String message_body;
    private int message_id;
    private int message_position;
    private String number;
    private int topic_position;
    private String topic_title;
    TextView tv_message_body;
    TextView tv_number;
    private int type;

    public static ItemShowFragment getInstance(Context context, String topic_title, int topic_position, int message_position, ArrayList<Message> allMessage) {
        ItemShowFragment mItemShowFragment = new ItemShowFragment();
        String message_body = ((Message) allMessage.get(message_position)).getSmsBody();
        String number = (message_position + 1) + "/" + allMessage.size();
        int favorite = ((Message) allMessage.get(message_position)).getSmsFavorite();
        int message_id = ((Message) allMessage.get(message_position)).getMsmId();
        int count_message = allMessage.size();
        Bundle args = new Bundle();
        args.putInt("type", 1);
        args.putString("topic_title", topic_title);
        args.putInt("topic_position", topic_position);
        args.putInt("message_id", message_id);
        args.putInt("message_position", message_position);
        args.putString("number", number);
        args.putString("message", message_body);
        args.putInt("favorite", favorite);
        args.putInt("count", count_message);
        mItemShowFragment.setArguments(args);
        return mItemShowFragment;
    }

    public static ItemShowFragment getInstance2(Context context, int slide_count, int message_position, ArrayList<Message> allMessage) {
        ItemShowFragment mItemShowFragment = new ItemShowFragment();
        String message_body = ((Message) allMessage.get(message_position)).getSmsBody();
        String number = (message_position + 1) + "/" + allMessage.size();
        int favorite = ((Message) allMessage.get(message_position)).getSmsFavorite();
        int message_id = ((Message) allMessage.get(message_position)).getMsmId();
        int count_message = allMessage.size();
        Bundle args = new Bundle();
        args.putInt("type", 2);
        args.putInt("message_id", message_id);
        args.putInt("message_position", message_position);
        args.putString("number", number);
        args.putString("message", message_body);
        args.putInt("favorite", favorite);
        args.putInt("count", count_message);
        mItemShowFragment.setArguments(args);
        return mItemShowFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.type = getArguments().getInt("type");
        if (this.type == 1) {
            this.topic_title = getArguments().getString("topic_title");
            this.topic_position = getArguments().getInt("topic_position");
            this.message_id = getArguments().getInt("message_id");
            this.message_position = getArguments().getInt("message_position");
            this.number = getArguments().getString("number");
            this.message_body = getArguments().getString("message");
            this.favorite = getArguments().getInt("favorite");
            this.coutn_message = getArguments().getInt("count");
        } else if (this.type == 2) {
            this.topic_title = "Favorite Tips";
            this.message_id = getArguments().getInt("message_id");
            this.message_position = getArguments().getInt("message_position");
            this.number = getArguments().getString("number");
            this.message_body = getArguments().getString("message");
            this.favorite = getArguments().getInt("favorite");
            this.coutn_message = getArguments().getInt("count");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = container.getContext();
        View view = inflater.inflate(R.layout.activity_content, container, false);
        this.tv_number = (TextView) view.findViewById(R.id.number);
        this.tv_message_body = (TextView) view.findViewById(R.id.message);
     this.img_favorite = (ImageView) view.findViewById(R.id.favorite_sms);
        this.img_share = (ImageView) view.findViewById(R.id.share_btn);
        this.img_random = (ImageView) view.findViewById(R.id.random_btn);
        this.img_copy = (ImageView) view.findViewById(R.id.copy_btn);
        this.tv_number.setText(this.number);
        this.tv_message_body.setText(Html.fromHtml(this.message_body));
        Typeface face = Typeface.createFromAsset(this.context.getAssets(), "fonts/ClearSans-Medium.ttf");
        Typeface face2 = Typeface.createFromAsset(this.context.getAssets(), "fonts/ClearSans-Regular.ttf");
        this.tv_message_body.setTypeface(face2);
        this.tv_number.setTypeface(face2);
        if (MessageActivity.temp_arr_favorite[this.message_position] == 1) {
            this.img_favorite.setImageResource(R.drawable.heart_red_1);
        } else {
            this.img_favorite.setImageResource(R.drawable.heart);
        }
        this.img_favorite.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ItemShowFragment.this.context);
                databaseAccess.open();
                if (MessageActivity.temp_arr_favorite[ItemShowFragment.this.message_position] == 1) {
                    MessageActivity.temp_arr_favorite[ItemShowFragment.this.message_position] = 0;
                    ItemShowFragment.this.img_favorite.setImageResource(R.drawable.heart);
                } else if (MessageActivity.temp_arr_favorite[ItemShowFragment.this.message_position] == 0) {
                    MessageActivity.temp_arr_favorite[ItemShowFragment.this.message_position] = 1;
                    ItemShowFragment.this.img_favorite.setImageResource(R.drawable.heart_red_1);
                }
                int result = databaseAccess.changeFavorite(ItemShowFragment.this.message_id, MessageActivity.temp_arr_favorite[ItemShowFragment.this.message_position]);
                databaseAccess.close();
            }
        });
        this.img_share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.SEND");
                intent2.setType("text/plain");
                intent2.putExtra("android.intent.extra.TEXT", ItemShowFragment.this.message_body);
                ItemShowFragment.this.startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });
        this.img_random.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Random rd = new Random();
                int value = rd.nextInt(ItemShowFragment.this.coutn_message);
                while (value < 0) {
                    value = rd.nextInt(ItemShowFragment.this.coutn_message);
                }
                MessageActivity.pager.setCurrentItem(value, false);
            }
        });
        this.img_copy.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((ClipboardManager) ItemShowFragment.this.context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text", ItemShowFragment.this.message_body));
                Toast.makeText(ItemShowFragment.this.context, "Copy to clipboard", 0).show();
            }
        });
        return view;
    }
}
