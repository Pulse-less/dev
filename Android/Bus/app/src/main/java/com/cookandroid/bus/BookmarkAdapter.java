package com.cookandroid.bus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cookandroid.bus.R;
import com.cookandroid.bus.StationBookmarkDTO;

import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StationBookmarkDTO> data;

    public BookmarkAdapter(Context context, ArrayList<StationBookmarkDTO> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.bookmarklist, null);
        }



        return convertView;
    }
}
