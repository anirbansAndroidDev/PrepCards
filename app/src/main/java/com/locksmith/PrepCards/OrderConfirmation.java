package com.locksmith.PrepCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.locksmith.utils.ReuseableClass;
import com.locksmith.utils.UnCaughtException;

public class OrderConfirmation extends Activity {
	String message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.order_confirmation);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));
		
		((TextView)findViewById(R.id.textViewTitle)).setTypeface(ReuseableClass.getFontStyle(this));
		
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) 
		{
			message = extras.getString("message").trim();
			((TextView)findViewById(R.id.textViewTitle)).setText(message);
		}
	}
	@Override
	public void onBackPressed()
	{
		Intent myIntent = new Intent(OrderConfirmation.this, OrderActivity.class);
		finish();
		startActivity(myIntent); 
	}
}
