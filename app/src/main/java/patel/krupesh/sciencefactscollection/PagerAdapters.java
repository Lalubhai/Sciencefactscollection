package patel.krupesh.sciencefactscollection;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapters extends FragmentStatePagerAdapter {
    ArrayList<Message> allMessage;
    Context context;
    int message_position;
    int slide_count;
    int topic_position = -1;
    String topic_title;

    public PagerAdapters(Context context, FragmentManager fm, String topic_title, int topic_position, int message_position, int number_message, ArrayList<Message> allMessage) {
        super(fm);
        this.context = context;
        this.topic_position = topic_position;
        this.message_position = message_position;
        this.topic_title = topic_title;
        this.slide_count = number_message;
        this.allMessage = allMessage;
    }

    public PagerAdapters(Context context, FragmentManager fm, int message_position, int number_message, ArrayList<Message> allMessage) {
        super(fm);
        this.context = context;
        this.message_position = message_position;
        this.slide_count = number_message;
        this.allMessage = allMessage;
    }

    public Fragment getItem(int position) {
        if (this.topic_position != -1) {
            return ItemShowFragment.getInstance(this.context, this.topic_title, this.topic_position, position, this.allMessage);
        }
        return ItemShowFragment.getInstance2(this.context, this.slide_count, position, this.allMessage);
    }

    public int getCount() {
        return this.slide_count;
    }


}
