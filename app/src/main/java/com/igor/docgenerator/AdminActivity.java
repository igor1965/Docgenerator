package com.igor.docgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

        EditText editText;
        Button btAdd,btReset;
        RecyclerView recyclerView;


        List<Employees> employeesList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager;
        RoomDB dataBase;
        MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        editText = findViewById(R.id.name_edit_text);
        btAdd = findViewById(R.id.name_add_btn);
        btReset = findViewById(R.id.name_reset_btn);
        recyclerView = findViewById(R.id.name_recycler_view);

        dataBase = RoomDB.getInstance(this);
        employeesList = dataBase.mainDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MainAdapter(employeesList,this);

        recyclerView.setAdapter(adapter);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = editText.getText().toString().trim();
                if (!sText.equals(""))
                {
                    Employees employees = new Employees();
                    employees.setName(sText);
                    dataBase.mainDao().insert(employees);
                    editText.setText("");
                    employeesList.clear();
                    employeesList.addAll(dataBase.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBase.mainDao().deleteAll(employeesList);
                employeesList.clear();
                employeesList.addAll(dataBase.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}