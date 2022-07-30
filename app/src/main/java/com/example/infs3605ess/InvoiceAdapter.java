package com.example.infs3605ess;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605ess.ui.invoices.InvoicesFragment;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.MyViewHolder>  implements  Filterable, Serializable {

    private List<Invoice> mInvoice;
    private List<Invoice> mInvoiceFiltered;
    private InvoiceAdapter.ClickListener mListener;

    public InvoiceAdapter(List<Invoice> invoice, InvoiceAdapter.ClickListener listener){

        this.mInvoice = invoice;
        this.mInvoiceFiltered = invoice;
        this.mListener = listener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mInvoiceFiltered = mInvoice;
                } else {
                    ArrayList<Invoice> filteredList = new ArrayList<>();
                    for(Invoice invoice : mInvoice) {
                        if(invoice.getIssuer().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(invoice);
                        }
                    }
                    mInvoiceFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mInvoiceFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mInvoiceFiltered = (ArrayList<Invoice>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    // Allows click events to be caught
    public interface  ClickListener {
        void onInvoiceClick(View view, int InvoiceID);
    }

    // Inflate the row layout from xml when needed (just the view, no data)
    @NonNull
    @Override
    public InvoiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new InvoiceAdapter.MyViewHolder(view, mListener);
    }

    // Bind the data to the TextView elements in each row
    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.MyViewHolder holder, int position) {
        final Invoice invoice = mInvoiceFiltered.get(position);
        int InvoiceID = position;
        holder.title.setText(invoice.getInvoiceNum());
        holder.status.setText(invoice.getStatus());
        holder.amount.setText("Total: $"+String.valueOf(invoice.getTotal()));
        holder.itemView.setTag(InvoiceID);
        holder.View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InvoiceView.class);
                intent.putExtra("View",  mInvoiceFiltered.get(InvoiceID));
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }

    // Total number of rows in the list
    @Override
    public int getItemCount() {
        return mInvoiceFiltered.size();
    }

    // Create view holder. The view holder has two text view elements
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, status, amount;
        private InvoiceAdapter.ClickListener listener;
        private Button View;

        public MyViewHolder(@NonNull View itemView, InvoiceAdapter.ClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(InvoiceAdapter.MyViewHolder.this);
            title = itemView.findViewById(R.id.tv_title);
            status = itemView.findViewById(R.id.tv_status);
            amount = itemView.findViewById(R.id.tv_money);
            View = itemView.findViewById(R.id.btn_view);

            /*
            itemView.findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), InvoiceView.class);
                    intent.putExtra(mInvoiceFiltered.get(mInvoiceFiltered.get))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

             */


        }

        @Override
        public void onClick(View v) {
            listener.onInvoiceClick(v, (Integer) v.getTag());
        }
    }

    // Sort method
    public void sort(final int sortMethod) {
        if(mInvoiceFiltered.size() > 0) {
            Collections.sort(mInvoiceFiltered, new Comparator<Invoice>() {
                @Override
                public int compare(Invoice o1, Invoice o2) {
                    if(sortMethod == 1) {
                        return o1.getIssuer().compareTo(o2.getIssuer());
                    } else if(sortMethod == 2)
                        return String.valueOf(o1.getTotal()).compareTo(String.valueOf(o2.getTotal()));
                    return o1.getIssuer().compareTo(o2.getIssuer());
                }
            });
        }
        notifyDataSetChanged();
    }


}
