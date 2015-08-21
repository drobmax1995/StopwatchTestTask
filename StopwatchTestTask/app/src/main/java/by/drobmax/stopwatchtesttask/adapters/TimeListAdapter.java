package by.drobmax.stopwatchtesttask.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Admin on 21.08.2015.
 */
public class TimeListAdapter extends BaseAdapter {
    private ArrayList<String> timeList;
    @Override
    public int getCount() {
        return timeList.size();
    }

    public TimeListAdapter(ArrayList<String> timeList) {
        this.timeList = timeList;
    }

    @Override
    public String getItem(int position) {
        return timeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
