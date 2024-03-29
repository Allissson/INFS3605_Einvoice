package com.example.infs3605ess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605ess.ui.invoices.InvoicesFragment;

import java.io.Externalizable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    //Filter method
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

    //Allows click events to be caught
    public interface  ClickListener {
        void onInvoiceClick(View view, int InvoiceID);
    }

    //Inflate the row layout from xml when needed (just the view, no data)
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
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        holder.DateT.setText(sdf1.format(invoice.getInvoiceDate()));
        if(invoice.getStatus().equals("Paid")){
            holder.IMG.setImageResource(R.mipmap.paid);
        }else if (invoice.getStatus().equals("unpaid")){
            holder.IMG.setImageResource(R.mipmap.unpaid);
        }else{
            holder.IMG.setImageResource(R.mipmap.overdue);
        }
        holder.title.setText(invoice.getInvoiceNum());
        holder.status.setText(invoice.getIssuer());
        holder.amount.setText(" $"+String.valueOf(invoice.getTotal()));
        holder.itemView.setTag(InvoiceID);
        holder.View.setOnClickListener(new View.OnClickListener() {

            //View Button OnClick method
            @Override
            public void onClick(View view) {

                //Pass parcelable object through intent
                Intent intent = new Intent(view.getContext(), InvoiceView.class);
                intent.putExtra("View",  mInvoiceFiltered.get(InvoiceID));

                //Pass date items
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                String invoiceDate = sdf1.format(mInvoiceFiltered.get(InvoiceID).getInvoiceDate());
                String dueDate = sdf1.format(mInvoiceFiltered.get(InvoiceID).getDueDate());
                intent.putExtra("invoiceDate",invoiceDate);
                intent.putExtra("dueDate",dueDate);

                //Pass description List
                List<String> Name = new ArrayList<>();
                List<Integer> Quantity = new ArrayList<>();
                List<Double> Price = new ArrayList<>();
                List<Double> Total = new ArrayList<>();
                int i = mInvoiceFiltered.get(InvoiceID).getDescriptionList().size();
                for(int a = 0; a<i; a++){
                    Description d = new Description();
                    String name = mInvoiceFiltered.get(InvoiceID).getDescriptionList().get(a).getName();
                    int quantity = mInvoiceFiltered.get(InvoiceID).getDescriptionList().get(a).getQuantity();
                    double price = mInvoiceFiltered.get(InvoiceID).getDescriptionList().get(a).getPrice();
                    double total = mInvoiceFiltered.get(InvoiceID).getDescriptionList().get(a).getTotal();
                    Name.add(name);
                    Quantity.add(quantity);
                    Price.add(price);
                    Total.add(total);
                }
                intent.putExtra("NAME", (Serializable) Name);
                intent.putExtra("QUANTITY", (Serializable) Quantity);
                intent.putExtra("PRICE", (Serializable) Price);
                intent.putExtra("TOTAL", (Serializable) Total);
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
        private TextView title, status, amount, DateT;
        private InvoiceAdapter.ClickListener listener;
        private Button View;
        private ImageView IMG;

        public MyViewHolder(@NonNull View itemView, InvoiceAdapter.ClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(InvoiceAdapter.MyViewHolder.this);
            title = itemView.findViewById(R.id.tv_title);
            status = itemView.findViewById(R.id.tv_status);
            amount = itemView.findViewById(R.id.tv_money);
            View = itemView.findViewById(R.id.btn_view);
            IMG = itemView.findViewById(R.id.img);
            DateT = itemView.findViewById(R.id.tv_time);
        }

    //OnClick method
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
