package com.android.petswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends Activity {

	private String[] mStrings = {
            "Push up", "Push left", "Cross fade", "Hyperspace"};

	TextView searchBy;
	Spinner spinner;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchtab);
        
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner.setAdapter(adapter);

        searchBy = (TextView) findViewById(R.id.searchkey);
    }
	
	public void searchPetSitter(View v)
	{
		Intent i = new Intent(this, SearchOwnerResultActivity.class);
		i.putExtra(SearchOwnerResultActivity.SEARCH_TYPE, spinner.getSelectedItem().toString());
		i.putExtra(SearchOwnerResultActivity.SEARCH_KEY, searchBy.getText().toString());
		startActivity(i);
	}
}
