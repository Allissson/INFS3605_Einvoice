package com.example.infs3605ess;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DescriptionActivity extends AppCompatActivity {
    private static final String TAG = "Description Activity";
    private String message;
        private List<Description> mDescription = new ArrayList<>();
        private DescriptionAdapter mAdapter;
        private RecyclerView mRecyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.description_recycler_view);
            message=getIntent().getStringExtra("key");
            System.out.println(message);
            // Instantiate Recyclerview
            mRecyclerView = findViewById(R.id.recycleView);
            // Set setHasFixedSize true if contents of the adapter does not change it's height or the width
            mRecyclerView.setHasFixedSize(true);

            // Specify linear layout manager for RecyclerView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            // Specify grid layout manager for RecyclerView
            //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
            mRecyclerView.setLayoutManager(layoutManager);
            String[] messageSplit = message.split(" ");
             int i = messageSplit.length;
             System.out.println(String.valueOf(i));


             for(int a=0;a<i;a=a+4){
                 Description d = new Description();
                 Log.d(TAG, messageSplit[a]);
                 d.setName(messageSplit[a]);
                 d.setQuantity(Integer.parseInt(messageSplit[a+1]));
                 Log.d(TAG, messageSplit[a+1]);
                 String price = messageSplit[a+2];
                 String total = messageSplit[a+3];
                 price = price.replace("$","");
                 price = price.replace(",","");
                 price = price.substring(0, price.length() - 3);


                 total = total.replace("$","");
                 total = total.replace(",","");
                 total = total.substring(0, total.length() - 3);

                 d.setTotal(Integer.parseInt(total));
                 d.setPrice(Integer.parseInt(price));
                 mDescription.add(d);
             }




            // Prepare data
            //prepareDescription();

            DescriptionAdapter.ClickListener listener = new DescriptionAdapter.ClickListener() {
                @Override
                public void onProductClick(View view, int DescriptionID) {
                    //Model model=(Model)adapter.getItem(position);
                    Description description = mDescription.get(DescriptionID);
                    // Display details for the selected RecyclerView item (product on the list)
                    Toast.makeText(getApplicationContext(), description.getName()+"\nPrice = $"+description.getPrice(), Toast.LENGTH_SHORT).show();
                }
            };
            // Instantiate adapter
            mAdapter = new DescriptionAdapter(mDescription, listener);
            // Set the adapter for the recycler view
            mRecyclerView.setAdapter(mAdapter);
        }

        private void prepareDescription(){
            Random rand = new Random();
            for(int i=10; i < 50; i++) {
                // random.nextInt(max - min + 1) + min (generates number from min to max (including both))
                int price = rand.nextInt(1000 - 100 + 1) + 100;
                Description description = new Description("Product-"+i, price, price+1, price+5);
                mDescription.add(description);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_description, menu);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mAdapter.getFilter().filter(newText);
                    return false;
                }
            });
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()) {
                case R.id.sortName:
                    // sort by name
                    mAdapter.sort(1);
                    return true;
                case R.id.sortPrice:
                    // sort by price
                    mAdapter.sort(2);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }
