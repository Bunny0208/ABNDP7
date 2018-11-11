package com.example.android.abndp7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> news1) {
        super(context, 0, news1);
    }

    static class ViewHolder {
        private TextView mTitleTextView;
        private TextView mAuthorTextView;
        private TextView mDataTextView;
        private TextView mSectionTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.mTitleTextView = convertView.findViewById(R.id.title);
            holder.mAuthorTextView = convertView.findViewById(R.id.author);
            holder.mDataTextView = convertView.findViewById(R.id.date);
            holder.mSectionTextView = convertView.findViewById(R.id.section);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News currentNews = getItem(position);


        holder.mTitleTextView.setText(currentNews.getTitle());
        holder.mAuthorTextView.setText(currentNews.getAuthor());
        holder.mDataTextView.setText(currentNews.getDate());
        holder.mSectionTextView.setText(currentNews.getSectionName());

        return convertView;
    }
}
