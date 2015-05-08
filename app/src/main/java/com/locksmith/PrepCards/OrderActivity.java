package com.locksmith.PrepCards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.net.URLEncoder;

public class OrderActivity extends Activity {
    float TeleGPercParam;
    float DigicelPercParam;

    EditText QTY00;
    EditText QTY01;
    EditText QTY10;
    EditText QTY11;
    EditText QTY20;
    EditText QTY21;
    EditText QTY30;
    EditText QTY31;
    EditText QTY40;
    EditText QTY41;
    EditText QTY50;
    EditText QTY51;

    TextView TeleGPercParamSub;
    TextView DigicelPercParamSub;
    TextView TextViewGrandTotal;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this));

        getActionBar().setTitle(getResources().getString(R.string.order_info_title));

        Bundle extras = getIntent().getExtras();
        startService(new Intent(OrderActivity.this, CurrentLocationService.class));

        if (extras != null) {
            TeleGPercParam = Float.parseFloat(extras.getString("TeleGPercParam").trim());
            DigicelPercParam = Float.parseFloat(extras.getString("DigicelPercParam").trim());
        }

        ((TextView) findViewById(R.id.TextViewConfirmOrder)).setTypeface(ReuseableClass.getFontStyle(this));
        ((Button) findViewById(R.id.buttonPlaceOrder)).setTypeface(ReuseableClass.getFontStyle(this));
        ((Button) findViewById(R.id.buttonClear)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.Product_Title)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.VendorOne)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.VendorTwo)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleOne)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleTwo)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleThree)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleFour)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleFive)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleSix)).setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductTitleSix)).setTypeface(ReuseableClass.getFontStyle(this));

        QTY00 = (EditText) findViewById(R.id.QTY00);
        QTY01 = (EditText) findViewById(R.id.QTY01);
        QTY10 = (EditText) findViewById(R.id.QTY10);
        QTY11 = (EditText) findViewById(R.id.QTY11);
        QTY20 = (EditText) findViewById(R.id.QTY20);
        QTY21 = (EditText) findViewById(R.id.QTY21);
        QTY30 = (EditText) findViewById(R.id.QTY30);
        QTY31 = (EditText) findViewById(R.id.QTY31);
        QTY40 = (EditText) findViewById(R.id.QTY40);
        QTY41 = (EditText) findViewById(R.id.QTY41);
        QTY50 = (EditText) findViewById(R.id.QTY50);
        QTY51 = (EditText) findViewById(R.id.QTY51);

        TeleGPercParamSub = (TextView) findViewById(R.id.TeleGPercParamSub);
        DigicelPercParamSub = (TextView) findViewById(R.id.DigicelPercParamSub);
        TextViewGrandTotal = (TextView) findViewById(R.id.TextViewGrandTotal);

        if (TeleGPercParam == 100) {
            ((TextView) findViewById(R.id.VendorOne)).setVisibility(View.INVISIBLE);
            QTY00.setVisibility(View.INVISIBLE);
            QTY10.setVisibility(View.INVISIBLE);
            QTY20.setVisibility(View.INVISIBLE);
            QTY30.setVisibility(View.INVISIBLE);
            QTY40.setVisibility(View.INVISIBLE);
            QTY50.setVisibility(View.INVISIBLE);
            TeleGPercParamSub.setVisibility(View.INVISIBLE);
        }
        if (DigicelPercParam == 100) {
            ((TextView) findViewById(R.id.VendorTwo)).setVisibility(View.INVISIBLE);
            QTY01.setVisibility(View.INVISIBLE);
            QTY11.setVisibility(View.INVISIBLE);
            QTY21.setVisibility(View.INVISIBLE);
            QTY31.setVisibility(View.INVISIBLE);
            QTY41.setVisibility(View.INVISIBLE);
            QTY51.setVisibility(View.INVISIBLE);
            DigicelPercParamSub.setVisibility(View.INVISIBLE);
        }

        QTY00.setTypeface(ReuseableClass.getFontStyle(this));
        QTY01.setTypeface(ReuseableClass.getFontStyle(this));
        QTY10.setTypeface(ReuseableClass.getFontStyle(this));
        QTY11.setTypeface(ReuseableClass.getFontStyle(this));
        QTY20.setTypeface(ReuseableClass.getFontStyle(this));
        QTY21.setTypeface(ReuseableClass.getFontStyle(this));
        QTY30.setTypeface(ReuseableClass.getFontStyle(this));
        QTY31.setTypeface(ReuseableClass.getFontStyle(this));
        QTY40.setTypeface(ReuseableClass.getFontStyle(this));
        QTY41.setTypeface(ReuseableClass.getFontStyle(this));
        QTY50.setTypeface(ReuseableClass.getFontStyle(this));
        QTY51.setTypeface(ReuseableClass.getFontStyle(this));

        ((TextView) findViewById(R.id.ProductSubTotalTitle)).setTypeface(ReuseableClass.getFontStyle(this));
        TeleGPercParamSub.setTypeface(ReuseableClass.getFontStyle(this));
        DigicelPercParamSub.setTypeface(ReuseableClass.getFontStyle(this));
        ((TextView) findViewById(R.id.ProductGrandTotalTitle)).setTypeface(ReuseableClass.getFontStyle(this));
        TextViewGrandTotal.setTypeface(ReuseableClass.getFontStyle(this));

        QTY00.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY01.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY10.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY11.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY20.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY21.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY30.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY31.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY40.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY41.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY50.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        QTY51.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calculate();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(4);
        QTY00.setFilters(FilterArray);
        QTY01.setFilters(FilterArray);
        QTY10.setFilters(FilterArray);
        QTY11.setFilters(FilterArray);
        QTY20.setFilters(FilterArray);
        QTY21.setFilters(FilterArray);
        QTY30.setFilters(FilterArray);
        QTY31.setFilters(FilterArray);
        QTY40.setFilters(FilterArray);
        QTY41.setFilters(FilterArray);
        QTY50.setFilters(FilterArray);
        QTY51.setFilters(FilterArray);

    }


    public void clearingContactInfo(View v) {
        TeleGPercParamSub.setText("");
        DigicelPercParamSub.setText("");
        TextViewGrandTotal.setText("");

        QTY00.setText("");
        QTY01.setText("");
        QTY10.setText("");
        QTY11.setText("");
        QTY20.setText("");
        QTY21.setText("");
        QTY30.setText("");
        QTY31.setText("");
        QTY40.setText("");
        QTY41.setText("");
        QTY50.setText("");
        QTY51.setText("");
    }

    public void calculate() {
        float QTY00_value = Float.parseFloat(QTY00.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY00.getText().toString().trim());
        float QTY01_value = Float.parseFloat(QTY01.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY01.getText().toString().trim());
        float QTY10_value = Float.parseFloat(QTY10.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY10.getText().toString().trim());
        float QTY11_value = Float.parseFloat(QTY11.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY11.getText().toString().trim());
        float QTY20_value = Float.parseFloat(QTY20.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY20.getText().toString().trim());
        float QTY21_value = Float.parseFloat(QTY21.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY21.getText().toString().trim());
        float QTY30_value = Float.parseFloat(QTY30.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY30.getText().toString().trim());
        float QTY31_value = Float.parseFloat(QTY31.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY31.getText().toString().trim());
        float QTY40_value = Float.parseFloat(QTY40.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY40.getText().toString().trim());
        float QTY41_value = Float.parseFloat(QTY41.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY41.getText().toString().trim());
        float QTY50_value = Float.parseFloat(QTY50.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY50.getText().toString().trim());
        float QTY51_value = Float.parseFloat(QTY51.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY51.getText().toString().trim());

        float TeleGPercParamSubTotal = (QTY00_value * 5) + (QTY10_value * 10) + (QTY20_value * 20) + (QTY30_value * 30) +
                (QTY40_value * 50) + (QTY50_value * 100) - (TeleGPercParam / 100) * ((QTY00_value * 5) + (QTY10_value * 10) +
                (QTY20_value * 20) + (QTY30_value * 30) + (QTY40_value * 50) + (QTY50_value * 100));

        float DigicelPercParamSubTotal = (QTY01_value * 5) + (QTY11_value * 10) + (QTY21_value * 20) + (QTY31_value * 30) +
                (QTY41_value * 50) + (QTY51_value * 100) - (DigicelPercParam / 100) * ((QTY01_value * 5) + (QTY11_value * 10) +
                (QTY21_value * 20) + (QTY31_value * 30) + (QTY41_value * 50) + (QTY51_value * 100));

        float grandTotal = TeleGPercParamSubTotal + DigicelPercParamSubTotal;

        TeleGPercParamSub.setText(String.format("%.2f", TeleGPercParamSubTotal) + " SRD");
        DigicelPercParamSub.setText(String.format("%.2f", DigicelPercParamSubTotal) + " SRD");
        TextViewGrandTotal.setText(String.format("%.2f", grandTotal) + " SRD");
    }

    public void savingContactInfo(View v) {
        Intent myIntent = new Intent(OrderActivity.this, OrderConfirmation.class);
        finish();
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(OrderActivity.this, CurrentLocationService.class));

        Intent myIntent = new Intent(OrderActivity.this, ContactActivity.class);
        finish();
        startActivity(myIntent);
    }

    public void placingOrder(View v) {

        if (Float.parseFloat(TextViewGrandTotal.getText().toString().substring(0, TextViewGrandTotal.getText().toString().length() - 4)) > 5) {
            float QTY00_value = Float.parseFloat(QTY00.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY00.getText().toString().trim());
            float QTY01_value = Float.parseFloat(QTY01.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY01.getText().toString().trim());
            float QTY10_value = Float.parseFloat(QTY10.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY10.getText().toString().trim());
            float QTY11_value = Float.parseFloat(QTY11.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY11.getText().toString().trim());
            float QTY20_value = Float.parseFloat(QTY20.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY20.getText().toString().trim());
            float QTY21_value = Float.parseFloat(QTY21.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY21.getText().toString().trim());
            float QTY30_value = Float.parseFloat(QTY30.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY30.getText().toString().trim());
            float QTY31_value = Float.parseFloat(QTY31.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY31.getText().toString().trim());
            float QTY40_value = Float.parseFloat(QTY40.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY40.getText().toString().trim());
            float QTY41_value = Float.parseFloat(QTY41.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY41.getText().toString().trim());
            float QTY50_value = Float.parseFloat(QTY50.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY50.getText().toString().trim());
            float QTY51_value = Float.parseFloat(QTY51.getText().toString().trim().equalsIgnoreCase("") ? "0" : QTY51.getText().toString().trim());


            JSONObject orderData = new JSONObject();
            try {
                    orderData.put("Teleg_5SRD", QTY00_value);
                    orderData.put("Teleg_10SRD", QTY10_value);
                    orderData.put("Teleg_20SRD", QTY20_value);
                    orderData.put("Teleg_30SRD", QTY30_value);
                    orderData.put("Teleg_50SRD", QTY40_value);
                    orderData.put("Teleg_100SRD", QTY50_value);
                    orderData.put("Digicel_5SRD", QTY01_value);
                    orderData.put("Digicel_10SRD", QTY11_value);
                    orderData.put("Digicel_20SRD", QTY21_value);
                    orderData.put("Digicel_30SRD", QTY31_value);
                    orderData.put("Digicel_50SRD", QTY41_value);
                    orderData.put("Digicel_100SRD", QTY51_value);

                orderData.put("Sub_Total_Teleg", TeleGPercParamSub.getText());
                orderData.put("Sub_Total_Digicel", DigicelPercParamSub.getText().toString());
                orderData.put("Grand_Total", TextViewGrandTotal.getText().toString());

                if (ReuseableClass.getFromPreference("glat", OrderActivity.this).equalsIgnoreCase("")) {
                    orderData.put("Cust_Loc_Lat", ReuseableClass.getFromPreference("nlat", OrderActivity.this));
                    orderData.put("Cust_Loc_Lng", ReuseableClass.getFromPreference("nlng", OrderActivity.this));
                } else {
                    orderData.put("Cust_Loc_Lat", ReuseableClass.getFromPreference("glat", OrderActivity.this));
                    orderData.put("Cust_Loc_Lng", ReuseableClass.getFromPreference("glng", OrderActivity.this));
                }

                orderData.put("Cust_Store_Name", ReuseableClass.getFromPreference("StoreName", OrderActivity.this));
                orderData.put("Cust_Address", ReuseableClass.getFromPreference("Address", OrderActivity.this));
                orderData.put("Cust_Phone", ReuseableClass.getFromPreference("Phone", OrderActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("TAG", "JSON: " + orderData);

            if (ReuseableClass.haveNetworkConnection(OrderActivity.this)) {
                dialog = new ProgressDialog(OrderActivity.this);
                dialog.setMessage(OrderActivity.this.getString(R.string.posting_order_dialog_message));
                dialog.show();
                new PostingOrderTask().execute(orderData.toString());
            } else {
                Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.increase_order_error), Toast.LENGTH_LONG).show();
        }
    }

    private class PostingOrderTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... values) {
            String responseBody = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                Log.d("TAG", "URL: " + "http://sms.locksmith.sr/EXTDEV/projects/AndroidBackEnds/PrepCards.php?action=noaction&orderdata=" + URLEncoder.encode(values[0], "UTF-8"));
                HttpGet httppost = new HttpGet("http://sms.locksmith.sr/EXTDEV/projects/AndroidBackEnds/PrepCards.php?action=noaction&orderdata=" + URLEncoder.encode(values[0], "UTF-8"));

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

                    Intent myIntent = new Intent(OrderActivity.this, OrderConfirmation.class);
                    myIntent.putExtra("message", message);
                    finish();
                    startActivity(myIntent);
                } else if (success.equalsIgnoreCase("false")) {
                    Toast.makeText(OrderActivity.this, R.string.server_offline, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(OrderActivity.this, R.string.other_error, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            stopService(new Intent(OrderActivity.this, CurrentLocationService.class));
        }
    }

}
