package com.example.infs3605ess;

import android.os.Bundle;
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
        private List<Description> mDescription = new ArrayList<>();
        private DescriptionAdapter mAdapter;
        private RecyclerView mRecyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.description_recycler_view);

            // Instantiate Recyclerview
            mRecyclerView = findViewById(R.id.recycleView);
            // Set setHasFixedSize true if contents of the adapter does not change it's height or the width
            mRecyclerView.setHasFixedSize(true);

            // Specify linear layout manager for RecyclerView
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            // Specify grid layout manager for RecyclerView
            //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
            mRecyclerView.setLayoutManager(layoutManager);

            // Prepare data
            prepareDescription();

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
            inflater.inflate(R.menu.menu_main, menu);
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

