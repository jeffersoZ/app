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

    /*
     * ---------------------------------------------------------------------------------------------
     * RESPOSTAS ÀS PERGUNTAS:
     *
     * 1. Uso de QueryIntentActivities para descoberta dinâmica:
     *    O método `queryIntentActivities` funciona como um motor de busca interno do Android.
     *    Ao passarmos uma Intent configurada com `ACTION_MAIN` e `CATEGORY_LAUNCHER`, estamos pedindo ao
     *    PackageManager: "Liste todas as Activities registradas no sistema que servem como ponto de entrada
     *    principal de um aplicativo". O sistema varre os Manifestos de todos os apps instalados e retorna
     *    aqueles que correspondem a esse filtro, permitindo descobrir apps sem saber seus nomes previamente.
     *
     * 2. O Package Manager:
     *    É a classe de serviço que fornece informações globais sobre o ambiente de aplicativos do dispositivo.
     *    Principais métodos e retornos:
     *      - `queryIntentActivities(Intent, flags)`: Retorna uma `List<ResolveInfo>`. Cada `ResolveInfo` contém
     *        detalhes sobre uma Activity específica que pode responder à Intent (prioridade, ícone, label).
     *      - `loadLabel(PackageManager)`: Método usado (geralmente dentro do Adapter) para converter as informações
     *        do pacote em um nome legível (String) para o usuário.
     *      - `loadIcon(PackageManager)`: Método usado para obter o Drawable (imagem) do ícone do app.
     *      - `getLaunchIntentForPackage(packageName)`: Retorna a Intent exata necessária para abrir aquele app.
     *
     * 3. Métodos para criação do Launcher e Tratamento:
     *    Os métodos principais são a configuração da Intent (`new Intent`, `addCategory`) e a consulta (`queryIntentActivities`).
     *    Tratamento dos dados:
     *      - A consulta retorna `ResolveInfo` (que contém informações da Activity e do filtro).
     *      - No loop `for`, nós extraímos o objeto `ApplicationInfo` (`resolveinfo.activityInfo.applicationInfo`)
     *        porque, para este exemplo simples, estamos interessados nas informações gerais do aplicativo
     *        (ícone e nome) para popular nossa lista `apps`.
     *
     * 4. Influência do Sistema de Permissões:
     *    Devido a atualizações de privacidade (Android 11 / API 30+), o Android bloqueou a capacidade de um app
     *    ver quais outros apps estão instalados (Visibilidade de Pacotes).
     *    Para que o método `queryIntentActivities` retorne a lista completa de aplicativos do usuário (WhatsApp,
     *    Instagram, etc.) e não apenas apps do sistema, é OBRIGATÓRIO declarar a permissão especial:
     *    `<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES"/>` no `AndroidManifest.xml`.
     *    Sem essa permissão, o sistema filtra a lista e esconde a maioria dos apps instalados.
     * ---------------------------------------------------------------------------------------------
     */
}
