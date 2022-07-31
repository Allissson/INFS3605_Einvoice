package com.example.infs3605ess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UrgentPayAdapter extends RecyclerView.Adapter<UrgentPayAdapter.UrgentPayViewHolder> {

    private List<Invoice> mInvoice;
    private UrgentPayAdapter.ClickListener mListener;
    public UrgentPayAdapter(List<Invoice> urgentpay, UrgentPayAdapter.ClickListener listener){
        this.mInvoice=urgentpay;
        this.mListener=listener;
    }

    public interface ClickListener {
        void onUrgentPayClick(View view, int InvoiceID);
    }

    @NonNull
    @Override
    public UrgentPayAdapter.UrgentPayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_urgentpay, parent, false);
        return new UrgentPayAdapter.UrgentPayViewHolder(view, mListener);
    }





    @Override
    public void onBindViewHolder(@NonNull UrgentPayAdapter.UrgentPayViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Invoice invoice = mInvoice.get(position);
        int InvoiceID = position;
        holder.itemView.setTag(InvoiceID);
        holder.urgentIssuer.setText(mInvoice.get(position).getIssuer());
        String amount = String.format("%.2f",mInvoice.get(position).getTotal());
        holder.urgentAmount.setText("$ "+ amount);
        holder.urgentNumber.setText(mInvoice.get(position).getInvoiceNum());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate1 = sdf1.format(mInvoice.get(position).getDueDate());
        holder.urgentDueDate.setText(strDate1);
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Pay button had been clicked!");
                Intent intent =  new Intent(view.getContext(),PaySuccessActivity.class);
                intent.putExtra("Invoice Number",mInvoice.get(position).getInvoiceNum());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mInvoice.size();
    }

    public class UrgentPayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView urgentNumber, urgentAmount, urgentIssuer,urgentDueDate;
        public final Button pay;
        public UrgentPayAdapter.ClickListener listener;


        public UrgentPayViewHolder(@NonNull View itemView,UrgentPayAdapter.ClickListener listener) {
            super(itemView);
            this.listener=listener;
            itemView.setOnClickListener(UrgentPayAdapter.UrgentPayViewHolder.this);
            urgentNumber=(TextView)itemView.findViewById(R.id.urgentNumber);
            urgentAmount=(TextView) itemView.findViewById(R.id.urgentAmount);
            urgentIssuer=(TextView)itemView.findViewById(R.id.urgentIssuer);
            urgentDueDate=(TextView)itemView.findViewById(R.id.urgentdueDate);
            pay=(Button)itemView.findViewById(R.id.urgentPay);

        }
        @Override
        public void onClick(View v ) {
            listener.onUrgentPayClick(v, (Integer) v.getTag());
        }
    }
    }



//    public void sort(final int sortMethod) {
//        if (mInvoice.size() > 0) {
//            Collections.sort(mInvoice, new Comparator<Invoice>() {
//                @Override
//                public int compare(Invoice o1, Invoice o2) {
//                    return o1.getDueDate().compareTo(o1.getDueDate());
//                }
//            });
//        }
//        notifyDataSetChanged();
//    }


