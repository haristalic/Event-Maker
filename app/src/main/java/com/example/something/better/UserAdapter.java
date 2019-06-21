package com.example.something.better;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder>{

    Context context;
    public static ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }
    @Override
    public UserAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_usersinterested, parent, false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final UserAdapter.CustomViewHolder holder, int position) {
        User currentUser = users.get(position);

        holder.emailText.setText(currentUser.email);
        holder.imgView = currentUser.img;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(ArrayList<User> arr) {
        users = arr;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {


        TextView emailText;
        String imgView;

        public CustomViewHolder(View view) {
            super(view);
            emailText = (TextView) view.findViewById(R.id.textView5);
        }
    }

}
