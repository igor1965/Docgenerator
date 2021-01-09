package com.igor.docgenerator;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Employees> employeesList;
    private Activity context;
    private RoomDB database;

    public MainAdapter(List<Employees> employeesList, Activity context) {
        this.employeesList = employeesList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Employees employees = employeesList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(employees.getName());
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employees emp = employeesList.get(holder.getAdapterPosition());
                final int sID = emp.getID();
                String sText = emp.getName();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button btnUpdate = dialog.findViewById(R.id.bt_update);

                editText.setText(sText);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String uText = editText.getText().toString().trim();
                        database.mainDao().update(sID,uText);
                        employeesList.clear();
                        employeesList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employees emp = employeesList.get(holder.getAdapterPosition());
                database.mainDao().delete(emp);

                int position = holder.getAdapterPosition();
                employeesList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,employeesList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.card_text_view);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
