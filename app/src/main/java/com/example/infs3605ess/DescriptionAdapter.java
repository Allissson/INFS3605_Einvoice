package com.example.infs3605ess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Adapter class that connects data set to the recycler view
public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.MyViewHolder> implements Filterable {
    private List<Description>  mDescription;
    private List<Description> mDescriptionFiltered;
    private ClickListener mListener;

//    Initialise the dataset of the Adapter
public DescriptionAdapter(List<Description> description, ClickListener listener){
        this.mDescription = description;
        this.mDescriptionFiltered = description;
        this.mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mDescriptionFiltered = mDescription;
                } else {
                    ArrayList<Description> filteredList = new ArrayList<>();
                    for(Description description : mDescription) {
                        if(description.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(description);
                        }
                    }
                    mDescriptionFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDescriptionFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDescriptionFiltered = (ArrayList<Description>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    // Allows click events to be caught
    public interface  ClickListener {
        void onProductClick(View view, int productID);
    }

    // Inflate the row layout from xml when needed (just the view, no data)
    @NonNull
    @Override
    public DescriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view, mListener);
    }

    // Bind the data to the TextView elements in each row
    @Override
    public void onBindViewHolder(@NonNull DescriptionAdapter.MyViewHolder holder, int position) {
        final Description description = mDescriptionFiltered.get(position);
        int DescriptionID = position;
        holder.name.setText(description.getName());
        holder.price.setText("Price: $"+String.valueOf(description.getPrice()));
        holder.quantity.setText("Quantity: "+String.valueOf(description.getQuantity()));
        holder.total.setText("Total: $"+String.valueOf(description.getTotal()));
        holder.itemView.setTag(DescriptionID);
    }

    // Total number of rows in the list
    @Override
    public int getItemCount() {
        return mDescriptionFiltered.size();
    }

    // Create view holder. The view holder has two text view elements
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, price, quantity, total;
        private ClickListener listener;

        public MyViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(MyViewHolder.this);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
            total = itemView.findViewById(R.id.tvTotal);
        }

        @Override
        public void onClick(View v) {
            listener.onProductClick(v, (Integer) v.getTag());
        }
    }

    // Sort method
    public void sort(final int sortMethod) {
        if(mDescriptionFiltered.size() > 0) {
            Collections.sort(mDescriptionFiltered, new Comparator<Description>() {
                @Override
                public int compare(Description o1, Description o2) {
                    if(sortMethod == 1) {
                        return o1.getName().compareTo(o2.getName());
                    } else if(sortMethod == 2)
                        return String.valueOf(o1.getTotal()).compareTo(String.valueOf(o2.getTotal()));
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        notifyDataSetChanged();
    }

}
