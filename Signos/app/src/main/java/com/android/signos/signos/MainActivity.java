package com.android.signos.signos;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView listaSignos;
    private String signos[] = {
            "Áries", "Touro", "Gêmeos", "Câncer", "Leão", "Virgem",
            "Libra", "Escorpião", "Sagitário", "Capricórnio",
            "Aquário", "Peixes"
    };
    private String perfis[] = {
            "A energia de liderança e pioneirismo rodeia pessoas. São conhecidos por serem pessoas briguentas e que não levam desaforo para casa.",
            "São conhecidos por amigos e companheiros fiéis além de muito generosos. São pessoas altamente carinhosas e possuem poucos, porém amigos selecionados a dedo.",
            "Eles são multitarefas, conseguem fazer diversas coisas ao mesmo tempo e as fazem muito bem. Se preocupam em manter seu espírito sempre jovem e gostam de estar atualizados.",
            "São conhecidos por serem direcionados especialmente pelas suas emoções, são pessoas altamente carinhosas, protetoras daqueles que amam e também simpáticos quando querem.",
            "São marcados pela impulsividade, você dificilmente os verá refletindo sobre as decisões que irão tomar. Outra das suas maiores características é a independência.",
            "Seguem uma linha de pensamento mais tradicional e possuem apego pela seriedade. São ótimos amigos, pois são extremamente leais.",
            "Libra é um dos signos mais agradáveis: cativante, sociável e dotado de uma vivacidade pouco comum, ele sabe tecer os relacionamentos com harmonia.",
            "São conhecidos por serem pessoas frias e calculistas, porém isso é apenas uma proteção para que não entreguem seus sentimentos de uma vez por todas.",
            "São pessoas apaixonadas pela liberdade e conhecidos pela busca eterna pela aventura. Sempre cheios de energia, positivos e em busca da renovação, é um signo que adora mudanças e está sempre buscando por novos ares.",
            "Podem muitas vezes serem considerados materialistas, devido à alta importância que dão para as coisas. Eles irão se esforçar para subir na vida, alcançando sucesso e poder.",
            "Conhecidos por serem pessoas humanitárias e muito simpáticas, aquarianos são leais, originais e possuem uma personalidade única.",
            "Eles costumam valorizar sempre um todo, não costumam pensar por cada indivíduo. Acham que o bem maior é mais importante do que o individual, se preocupando muito com o coletivo."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSignos = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                signos
        );

        listaSignos.setAdapter(adapter);
        listaSignos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, perfis[i], Toast.LENGTH_LONG).show();
            }
        });
    }
}
