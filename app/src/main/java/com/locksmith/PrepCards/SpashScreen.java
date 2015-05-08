package com.locksmith.PrepCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.locksmith.utils.ReuseableClass;
import com.locksmith.utils.UnCaughtException;


public class SpashScreen extends Activity {

	LinearLayout logo_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spash_screen);
		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));

		TextView textViewTitle = (TextView)findViewById(R.id.textViewTitle);
		TextView textViewCompanyInfo = (TextView)findViewById(R.id.textViewCompanyInfo);
		textViewTitle.setTypeface(ReuseableClass.getFontStyle(SpashScreen.this));		
		textViewCompanyInfo.setTypeface(ReuseableClass.getFontStyle(SpashScreen.this));		

		Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		logo_layout = (LinearLayout)findViewById(R.id.linearLayoutLogoLayout);

		logo_layout.setAnimation(slideUp);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() 
			{
				Intent myIntent = new Intent(SpashScreen.this, ContactActivity.class);
				finish();
				startActivity(myIntent);
				overridePendingTransition(R.anim.fadein,R.anim.fadeout);
			}
		}, 5500);
	}
}
