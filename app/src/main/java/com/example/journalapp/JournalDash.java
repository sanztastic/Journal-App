package com.example.journalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.journalapp.adapter.CustomBaseAdapter;
import com.example.journalapp.dbUtils.DatabaseUtil;
import com.example.journalapp.model.Journal;

import java.util.List;

public class JournalDash extends AppCompatActivity {
    private DatabaseUtil dbUtil;
    private List<Journal> journalList;

    private ListView listView;
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        dbUtil = new DatabaseUtil(this);
        String userName = getIntent().getStringExtra("user_name");
        journalList = dbUtil.getJournalFromUser(userName);
        listView = (ListView) findViewById(R.id.journal_list);
        addButton = (Button) findViewById(R.id.add);

        CustomBaseAdapter adapter = new CustomBaseAdapter(this, journalList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddJournal.class);
                intent.putExtra("user_name",userName);
                intent.putExtra("update", "no");
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}