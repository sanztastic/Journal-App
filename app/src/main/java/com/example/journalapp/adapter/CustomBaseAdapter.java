package com.example.journalapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AlertDialogLayout;

import com.example.journalapp.AddJournal;
import com.example.journalapp.JournalDash;
import com.example.journalapp.R;
import com.example.journalapp.dbUtils.DatabaseUtil;
import com.example.journalapp.dbUtils.DbBitmapUtility;
import com.example.journalapp.model.Journal;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {
    private Context ctx;
    private List<Journal> journalList;
    private DatabaseUtil db;

    public CustomBaseAdapter(Context ctx, List<Journal> journalList){
        this.ctx = ctx;
        this.journalList = journalList;
    }

    @Override
    public int getCount() {
        return journalList.size();
    }

    @Override
    public Object getItem(int i) {
        return journalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView title = (TextView) view.findViewById(R.id.title_text);
        ImageView image = (ImageView) view.findViewById(R.id.image_icon);
        ImageView update = (ImageView) view.findViewById(R.id.update);
        ImageView delete = (ImageView) view.findViewById(R.id.delete);
        Journal journal = journalList.get(i);
        title.setText(journal.getTitle());
        Bitmap bitmap = DbBitmapUtility.getImage(journal.getImage());
        image.setImageBitmap(bitmap);
        update.setImageResource(R.drawable.update);
        delete.setImageResource(R.drawable.delete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddJournal.class);
                intent.putExtra("user_name",journalList.get(i).getUser_name());
                intent.putExtra("update","yes");
                intent.putExtra("id",journalList.get(i).getId());
                view.getContext().startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
                dialogBuilder.setTitle("Are you sere you want to delete?");
                dialogBuilder.setMessage("Are you sere you want to delete "+i);
                final int positionToRemove = i;
                dialogBuilder.setNegativeButton("Cancel", null);
                dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = journalList.get(positionToRemove).getId();
                        db = new DatabaseUtil(view.getContext());
                        db.deleteJournalById(id);
                        journalList.remove(positionToRemove);
                        db.close();
                        notifyDataSetChanged();
                    }
                });
                dialogBuilder.show();
            }
        });



        return view;
    }
}
