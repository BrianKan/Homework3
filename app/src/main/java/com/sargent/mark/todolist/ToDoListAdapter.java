package com.sargent.mark.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.DBHelper;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;


/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {
    private Cursor cursor;
    private ItemClickListener listener;
    private checkBoxListen listener2;
    private String TAG = "todolistadapter";




    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

        // TODO Added type to the itemClicklistener of the TodoListAdapter
    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, long id,String type);
    }

    // TODO Creating a checkbox interface to pass in the function to main activity
    public interface checkBoxListen{
        void onCheckBoxClick(long id,int isDone);
    }

    //TODO Added a checkboxlistener
    public ToDoListAdapter(Cursor cursor, ItemClickListener listener,checkBoxListen listener2) {
        this.cursor = cursor;
        this.listener = listener;
        this.listener2 =listener2;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descr;
        TextView due;
        String duedate;
        String description;
        long id;

        //TODO Added Checkbox and Textview for Type and isDone
        CheckBox isDo;
        TextView typ;

        String type;
        boolean isDone;


        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            isDo=(CheckBox)view.findViewById(R.id.itemCheckBox);
            typ=(TextView)view.findViewById(R.id.type);
            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {

            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            // TODO Added type and isDone to be read by the Cursor
            type=cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_TYPE));
            isDone=cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_IS_DONE))>0;
            descr.setText(description);
            due.setText(duedate);
            typ.setText(type);

            isDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                            listener2.onCheckBoxClick(id,1);
                        else
                            listener2.onCheckBoxClick(id,0);
                    }
                });
            isDo.setChecked(isDone);


            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, id,type);
        }
    }

}
