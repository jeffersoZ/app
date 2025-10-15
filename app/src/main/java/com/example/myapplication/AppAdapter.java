package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppAdapter extends ArrayAdapter<ApplicationInfo> {
    private final Context context;
    private final PackageManager pm;
    public AppAdapter(@NonNull Context context, @NonNull List<ApplicationInfo> apps, PackageManager pm) {
        super(context, 0, apps);
        this.context = context;
        this.pm = pm;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.app, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.appIcon);
        TextView name = convertView.findViewById(R.id.appName);

        ApplicationInfo app = getItem(position);

        name.setText(app.loadLabel(pm));
        icon.setImageDrawable(app.loadIcon(pm));

        convertView.setOnClickListener(v -> {
            Intent intent = pm.getLaunchIntentForPackage(app.packageName);
            if(intent != null)
                context.startActivity(intent);
        });

        return convertView;
    }
}