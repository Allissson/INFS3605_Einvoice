package com.example.infs3605ess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UrgentPayAdapter extends RecyclerView.Adapter<UrgentPayAdapter.UrgentPayViewHolder> {

    private ArrayList<Invoice> mInvoice;
    private Context mContext;
    private RecyclerViewClickListener rListener;
    public UrgentPayAdapter(Context context, ArrayList<Invoice> urgentpay,RecyclerViewClickListener listener){
        mContext=context;
        mInvoice=urgentpay;
        rListener=listener;
    }
    @NonNull
    @Override
    public UrgentPayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_urgentpay,parent,false);
        UrgentPayViewHolder urgentPayViewHolder = new UrgentPayViewHolder(v,rListener);
        return urgentPayViewHolder;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, Invoice invoice);
    }

    @Override
    public void onBindViewHolder(@NonNull UrgentPayAdapter.UrgentPayViewHolder holder, int position) {
        holder.urgentIssuer.setText(mInvoice.get(position).getIssuer());
        String amount = String.format("%.2f",mInvoice.get(position).getTotal());
        holder.urgentAmount.setText(amount);
        holder.urgentNumber.setText(mInvoice.get(position).getInvoiceNum());
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Pay button had been clicked!");
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UrgentPayViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView urgentNumber, urgentAmount, urgentIssuer;
        public final Button pay;
        public RecyclerViewClickListener bListener;


        public UrgentPayViewHolder(@NonNull View itemView,RecyclerViewClickListener listener) {
            super(itemView);
            mView=itemView;
            this.bListener=listener;
            urgentNumber=(TextView)itemView.findViewById(R.id.urgentNumber);
            urgentAmount=(TextView) itemView.findViewById(R.id.urgentAmount);
            urgentIssuer=(TextView)itemView.findViewById(R.id.urgentIssuer);
            pay=(Button)itemView.findViewById(R.id.urgentPay);

        }
    }
}
