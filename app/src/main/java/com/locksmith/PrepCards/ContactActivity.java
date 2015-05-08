package com.locksmith.PrepCards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.locksmith.utils.ReuseableClass;
import com.locksmith.utils.UnCaughtException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactActivity extends Activity {

	EditText editTextAddress;
	EditText editTextPhone;
	EditText editTextStoreName;
	private ProgressDialog dialog;
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_info_layout);

		getActionBar().setTitle(getResources().getString(R.string.add_contact_info_title));  

		Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));

		editTextAddress			= (EditText)findViewById(R.id.editTextAddress);
		editTextPhone			= (EditText)findViewById(R.id.editTextPhone);
		editTextStoreName		= (EditText)findViewById(R.id.editTextStoreName);

		editTextAddress.setTypeface(ReuseableClass.getFontStyle(this));
		editTextPhone.setTypeface(ReuseableClass.getFontStyle(this));
		editTextStoreName.setTypeface(ReuseableClass.getFontStyle(this));
		((Button)findViewById(R.id.buttonNext)).setTypeface(ReuseableClass.getFontStyle(this));
		((Button)findViewById(R.id.buttonClear)).setTypeface(ReuseableClass.getFontStyle(this));

		if(!ReuseableClass.getFromPreference("StoreName", this).equalsIgnoreCase(""))
		{
			editTextStoreName.setText(ReuseableClass.getFromPreference("StoreName", this));
			editTextAddress.setText(ReuseableClass.getFromPreference("Address", this));
			editTextPhone.setText(ReuseableClass.getFromPreference("Phone", this));
		}
	}

	public void savingContactInfo(View v)  
	{
		String address   = editTextAddress.getText().toString();
		String phone	 = editTextPhone.getText().toString();
		String storeName = editTextStoreName.getText().toString();

		if(ReuseableClass.haveNetworkConnection(ContactActivity.this))
		{
			if(address.trim().equalsIgnoreCase("") || phone.trim().equalsIgnoreCase("") || storeName.trim().equalsIgnoreCase(""))
			{
				Toast.makeText(this, R.string.all_field_mandatory, Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(address.trim().length()<6 || phone.trim().length()<6 || storeName.trim().length()<6)
				{
					Toast.makeText(this, R.string.min_char, Toast.LENGTH_SHORT).show();
				}
				else
				{
					dialog = new ProgressDialog(ContactActivity.this);
					dialog.setMessage(ContactActivity.this.getString(R.string.checking_server_dialog_message));
					dialog.show();

					new CheckingServerStatusTask().execute();
				}
			}
		}
		else
		{
			Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
		}
	}

	public void clearingContactInfo(View v) 
	{
		editTextStoreName.setText("");
		editTextAddress.setText("");
		editTextPhone.setText("");
	}

	private class CheckingServerStatusTask extends AsyncTask<String, String, String> {
		protected String doInBackground(String... values) {
			String responseBody = "";
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet("http://sms.locksmith.sr/EXTDEV/projects/AndroidBackEnds/PrepCards.php?action=GetServerStatus");
			try {
				HttpResponse response = httpclient.execute(httppost);

				int responseCode = response.getStatusLine().getStatusCode();
				if (responseCode == 200) {
					responseBody = EntityUtils.toString(response.getEntity());
				}
			} catch (Exception t) {
				Log.e("TAG", "Error: " + t);
			}
			return responseBody;
		}

		protected void onPostExecute(String result) {
			Log.d("TAG", "value: " + result);
			try {
				JSONObject JsonResult = new JSONObject(result);
				String success = JsonResult.getString("success");

				if (success.equalsIgnoreCase("true")) {
					JSONObject mainObj = JsonResult.getJSONObject("data");

					String message = mainObj.getString("message");
					String TeleGPercParam = mainObj.getString("TeleGPercParam");
					String DigicelPercParam = mainObj.getString("DigicelPercParam");

					ReuseableClass.saveInPreference("StoreName", editTextStoreName.getText().toString().trim(), ContactActivity.this);
					ReuseableClass.saveInPreference("Address", editTextAddress.getText().toString().trim(), ContactActivity.this);
					ReuseableClass.saveInPreference("Phone", editTextPhone.getText().toString().trim(), ContactActivity.this);

					Intent myIntent = new Intent(ContactActivity.this, OrderActivity.class);
					myIntent.putExtra("TeleGPercParam", TeleGPercParam);
					myIntent.putExtra("DigicelPercParam", DigicelPercParam);
					finish();
					startActivity(myIntent);
				} else if (success.equalsIgnoreCase("false")) {
					Toast.makeText(ContactActivity.this, R.string.server_offline, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(ContactActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}

	@Override 
	public void onBackPressed() 
	{ 
		if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) 
		{  
			super.onBackPressed();  
			return; 
		} 
		else { Toast.makeText(getBaseContext(), R.string.tap_to_back, Toast.LENGTH_SHORT).show(); }

		mBackPressed = System.currentTimeMillis();
	}
}

