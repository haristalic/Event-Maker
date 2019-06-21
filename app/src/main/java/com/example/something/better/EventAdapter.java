package com.example.something.better;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolder>  {

        Context context;
        public static ArrayList<Event> events;

        public EventAdapter(Context context, ArrayList<Event> events) {
            this.context = context;
            this.events = events;
        }


        @Override
        public EventAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_feed, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final EventAdapter.CustomViewHolder holder, final int position) {
            final Event currentEvent = events.get(events.size() - position - 1);


            holder.eventName.setText(currentEvent.eventName);
            holder.email.setText(currentEvent.email);
            holder.numInterested.setText(currentEvent.numInterested);
            holder.date = currentEvent.date;
            holder.description = currentEvent.description;
            holder.imageURL = currentEvent.imageURL;
            holder.key = currentEvent.key;
            holder.pplInterested = currentEvent.peopleInterested;

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("email",holder.email.getText().toString());
                    intent.putExtra("name",holder.eventName.getText().toString());
                    intent.putExtra("number",holder.numInterested.getText().toString());
                    intent.putExtra("description", holder.description);
                    intent.putExtra("date", holder.date);
                    intent.putExtra("imageURL",holder.imageURL);
                    intent.putExtra("key",holder.key);
                    intent.putExtra("peopleInterested",holder.pplInterested);

                    intent.putExtra("address",currentEvent.address);

                    context.startActivity(intent);
                }
            });

            class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
                protected Bitmap doInBackground(String... strings) {
                    try {return Glide.
                            with(context).
                            load(strings[0]).
                            asBitmap().
                            into(100, 100).
                            get();}
                    catch (Exception e) {return null;}



                }


                protected void onProgressUpdate(Void... progress) {}

                protected void onPostExecute(Bitmap result) {
                    holder.pic.setImageBitmap(result);
                }
            }
            FirebaseStorage.getInstance().getReferenceFromUrl("gs://adix-events-2e55b.appspot.com").child(currentEvent.imageURL+ ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    new DownloadFilesTask().execute(uri.toString()); Log.d("ye", uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("sad", exception.toString());
                }
            });

        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {


            TextView eventName;
            TextView email;
            TextView numInterested;
            ImageView pic;
            String date;
            String description;
            String imageURL;
            CardView cardView;
            String key;
            ArrayList<String> pplInterested;

            public CustomViewHolder(View view) {
                super(view);

                eventName = (TextView) view.findViewById(R.id.textView13);
                email = (TextView) view.findViewById(R.id.textView12);
                numInterested = (TextView) view.findViewById(R.id.textView14);
                pic = (ImageView) view.findViewById(R.id.imageView5);

                 cardView = (CardView) view.findViewById(R.id.cardView);


            }
        }

}
