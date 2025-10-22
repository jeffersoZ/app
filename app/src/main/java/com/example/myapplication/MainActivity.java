package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PackageManager pm;
    ListView listView;
    ArrayList<ApplicationInfo> apps = new ArrayList<>();
    AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        pm = getPackageManager();

        Intent iQuery = new Intent(Intent.ACTION_MAIN,null);

        iQuery.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> listResolveInfo = pm.queryIntentActivities(iQuery,PackageManager.GET_META_DATA);

        for(ResolveInfo resolveinfo : listResolveInfo){
            apps.add(resolveinfo.activityInfo.applicationInfo);
        }
        adapter = new AppAdapter(this, apps, pm);
        listView.setAdapter(adapter);

    }
}