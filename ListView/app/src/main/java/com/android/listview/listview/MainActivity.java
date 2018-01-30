package com.android.listview.listview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView list;
    private String itens[] = {
            "Belo Horizonte", "Caldas Novas", "São Paulo", "Campos do jordão",
            "Porto Seguro", "Rio de Janeiro", "Uberlândia", "Caribe",
            "Praga", "Cancun", "Guarujá", "Santiago", "Aruba", "Buenos Aires"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this, itens[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
