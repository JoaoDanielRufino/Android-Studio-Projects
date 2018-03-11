/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.fragments.HomeFragment;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private SlidingTabLayout slidingTabLayout;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = findViewById(R.id.toolbar_principal);
    slidingTabLayout = findViewById(R.id.sliding_tab_main);
    viewPager = findViewById(R.id.view_pager_main);

    toolbar.setLogo(R.drawable.instagramlogo);
    setSupportActionBar(toolbar);

      TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
      viewPager.setAdapter(tabsAdapter);

      slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
      slidingTabLayout.setDistributeEvenly(true);
      slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.cinza));
      slidingTabLayout.setViewPager(viewPager);
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_sair:
                ParseUser.logOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            case R.id.action_configuracoes:
                return true;
            case R.id.action_compartilhar:
                compartilharFoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void compartilharFoto(){
      //Acessando as imagens do celular
      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            //Recuperando o local da imagem
            Uri uriImagemSelecionada = data.getData();
            try {
                //Recuperando a imagem
                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImagemSelecionada);

                //Comprimindo a imagem em PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);

                //Criando um arquivo com formato do parse
                byte byteArray[] = stream.toByteArray();
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhss");
                String nomeImagem = dateFormat.format(new Date());
                ParseFile parseFile = new ParseFile(nomeImagem + ".png", byteArray);

                //Criando classe no parse
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("imagem", parseFile);

                //Salvando os dados
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            toastMessage("Imagem postada com sucesso!!");
                            TabsAdapter adapter = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment homeFragment = (HomeFragment) adapter.getFragment(0);
                            homeFragment.atualizaPostagens();
                        }
                        else{
                            toastMessage("Erro ao postar a imagem: " + e.getMessage());
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
