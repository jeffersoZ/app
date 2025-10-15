package com.example.myapplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PackageManager pm;
    ListView listView;
    List<ApplicationInfo> apps;
    ArrayList<ApplicationInfo> userApps;
    AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        pm = getPackageManager(); // Recupera gerenciador de pacotes
        apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        userApps = new ArrayList<>();

        for(ApplicationInfo app : apps) {
            if((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                userApps.add(app);
            };
        }

        adapter = new AppAdapter(this, apps, pm);
        listView.setAdapter(adapter);
    }
}