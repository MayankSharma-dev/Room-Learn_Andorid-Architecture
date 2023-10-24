package com.example.andoridarchitecture;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//public class RAdapter extends RecyclerView.Adapter<RAdapter.NoteHolder> {

public class RAdapter extends ListAdapter<Note,RAdapter.NoteHolder> {

//    private List<Note> notes = new ArrayList<>();

    // a reference is created to access the method of the Interface.
    private OnItemClick listener;

    public RAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.t0.setText(String.valueOf(currentNote.getPriority()));
        holder.t1.setText(currentNote.getTitle());
        holder.t2.setText(currentNote.getDescription());



    }

    public Note getNotePosition(int position){
        return getItem(position);
    }

//    // it is handled automatically by list adapter now.
//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }



//    // instead of setNotes() we will use another method provided by the list adapter.
//    public void setNotes(List<Note> notes){
//        this.notes = notes;
//        notifyDataSetChanged(); // it means that the whole list is invlaid and draw a new list(which is inefficient) and
//        // does not provide default animations.
//    }



//    public interface onItemClick
//    {
//        void onItemClicked(Note note);
//    }

    // to Access the interface method.
    // here OnItemClick reference is received as an argument similar to (this) when we implement on View.OnClickListener
    public void setOnItemClick(OnItemClick listener){

        Log.d(">>>>>>>>>","setOntemClick method");
        this.listener = listener;
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView t0,t1,t2;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            t0 =itemView.findViewById(R.id.t0);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);

            // itemView is the whole cardview.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener !=null && position != RecyclerView.NO_POSITION){
                        Log.d(">>>>>>>>>","The Listerner Adpater");
                        listener.onItemClicked(getItem(position));
                    }

                }
            });

        }
    }
}
