package com.android.todolist.todolist;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText todoText;
    private Button addButton;
    private ListView todoList;
    private SQLiteDatabase dataBase;
    private static final String DATA_BASE_NAME = "todoApp";
    private ArrayList<Integer> ids;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoText = findViewById(R.id.editText);
        addButton = findViewById(R.id.add_button);
        todoList = findViewById(R.id.listView);

        try {

            dataBase = openOrCreateDatabase(DATA_BASE_NAME, MODE_PRIVATE, null);
            dataBase.execSQL("CREATE TABLE IF NOT EXISTS tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task VARCHAR)");

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{

                        String text = todoText.getText().toString();
                        saveTasks(text);

                    }catch(Exception e){
                        e.printStackTrace();
                    }finally {
                        todoText.setText("");
                    }
                }
            });

            todoList.setLongClickable(true);
            todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                    dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Delete");
                    dialog.setMessage("Do you really want to delete?");

                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                removeTask(ids.get(pos));
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    dialog.setNegativeButton("No", null);

                    dialog.create();
                    dialog.show();

                    return true;
                }
            });

            recoverTasks();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveTasks(String text) throws Exception{
        if(todoText.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Enter with a task please.", Toast.LENGTH_SHORT).show();
        }
        else{
            dataBase.execSQL("INSERT INTO tasks (task) VALUES('" + text + "')");
            recoverTasks();
        }
    }

    private void removeTask(Integer id) throws Exception{
        dataBase.execSQL("DELETE FROM tasks WHERE id = " + id);
        recoverTasks();
    }

    private void recoverTasks() throws Exception{
        Cursor cursor = dataBase.rawQuery("SELECT * FROM tasks ORDER BY id DESC", null);
        int indexTaskColumn = cursor.getColumnIndex("task");
        int indexIdColumn = cursor.getColumnIndex("id");

        ArrayList<String> itens = new ArrayList<>();

        ids = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                itens.add(cursor.getString(indexTaskColumn));
                ids.add(Integer.parseInt(cursor.getString(indexIdColumn)));
            } while (cursor.moveToNext());
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_2,
                android.R.id.text2,
                itens);

        todoList.setAdapter(adapter);

    }
}
