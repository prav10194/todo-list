package com.example.data4;

import com.appengers.noteworthy.R;
import com.example.data4.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddTodoActivity extends Activity implements OnClickListener {

	// GUI components
	private EditText todoText;		// Text field
	private ImageView addNewButton;	// Add new button
	private ImageView backButton;		// Back button
	
	// DAO
	private TodoDAO dao;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_todo);
		
	
		// Create DAO object
		dao = new TodoDAO(this);
		
		todoText 		= (EditText)findViewById(R.id.newTodoText);
		addNewButton 	= (ImageView)findViewById(R.id.addNewTodoButton);
		backButton		= (ImageView)findViewById(R.id.menuGoBackButton);
		
		addNewButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// If add button was clicked
		if (addNewButton.isPressed()) {
			// Get entered text
			String todoTextValue = todoText.getText().toString();
			todoText.setText("");
			
			// Add text to the database
			dao.createTodo(todoTextValue);
			
			// Display success information
			Toast.makeText(getApplicationContext(), "Note added on Home Screen!", Toast.LENGTH_LONG).show();
			
		} else if (backButton.isPressed()) {
			// When back button is pressed
			// Create an intent
			Intent intent = new Intent(this, MainActivity.class);
			// Start activity
			startActivity(intent);
			// Finish this activity
			this.finish();
			
			// Close the database
			dao.close();
		}
		
	}
	
}
