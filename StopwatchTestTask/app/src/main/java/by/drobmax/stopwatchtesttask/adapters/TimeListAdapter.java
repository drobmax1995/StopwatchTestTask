package by.drobmax.stopwatchtesttask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import by.drobmax.stopwatchtesttask.MainActivity;
import by.drobmax.stopwatchtesttask.database.TimeListDataProvider;
import by.drobmax.stopwatchtesttask.models.TimeListModel;
import by.drobmax.teeeeeeeest.R;

/**
 * Created by Admin on 21.08.2015.
 */
public class TimeListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<TimeListModel> timeList;
    private Context context;
    @Override
    public int getCount() {
        return timeList.size();
    }

    public TimeListAdapter(ArrayList<TimeListModel> timeList, Context context) {
        this.timeList = timeList;
        this.context = context;
        inflater  = LayoutInflater.from(context);
    }

    @Override
    public TimeListModel getItem(int position) {
        return timeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list, null);
            viewHolder = new ViewHolder();
            viewHolder.initViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final TimeListModel item = getItem(position);
        viewHolder.textViewContent.setText(item.getTimeList());
        viewHolder.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeListDataProvider dataProvider = new TimeListDataProvider(context);
                dataProvider.deleteTimes(item);
                ((MainActivity)context).onDataChange();
            }
        });
        return view;
    }

    class ViewHolder {
        private ImageButton closeButton;
        private TextView textViewContent;
        public ViewHolder() {
        }
        public void initViewHolder(View view) {
            closeButton = (ImageButton)view.findViewById(R.id.imageButton);
            textViewContent = (TextView)view.findViewById(R.id.textViewItem);
        }
    }
}
