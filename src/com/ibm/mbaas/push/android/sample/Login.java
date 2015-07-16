package com.ibm.mbaas.push.android.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;


public class Login extends Activity {

    Button login;
    EditText username;
    EditText password;

    String url = "http://<YOUR-APP-NAME>.mybluemix.net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.loginButton);
        username = (EditText)findViewById(R.id.usernameText);
        password = (EditText)findViewById(R.id.passwordText);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Login.this);
        Boolean isLoggedIn = sp.getBoolean("loggedIn", false);

        System.out.println("onMainCreate: " + isLoggedIn);
        if (isLoggedIn) {
            Intent launchactivity = new Intent(Login.this, PushActivity.class);
            startActivity(launchactivity);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    if (isOnline()) {
                        new AuthCreds() {
                            @Override
                            protected void onPreExecute() {

                                try {
                                    jsonToSend.put("username", username.getText().toString());
                                    jsonToSend.put("password", password.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                urlToPost = url + "/logincheck";

                                mDialog = new ProgressDialog(Login.this);
                                mDialog.setMessage("Logging In...");
                                mDialog.show();
                            }

                            @Override
                            protected void onPostExecute(String str) {
                                System.out.println("ResponseStr: " + responseStr);

                                System.out.println(jsonToSend.toString());

                                SharedPreferences.Editor speditor = sp.edit();
                                speditor.putString("url", url);


                                try {
                                    if (responseStr.getBoolean("status")) {

                                        speditor.putBoolean("loggedIn", true).commit();
                                        try {
                                            speditor.putString("username", jsonToSend.getString("username")).commit();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Intent launchactivity = new Intent(Login.this, PushActivity.class);
                                        startActivity(launchactivity);
                                    } else if (responseStr.getInt("error_code") == 1) {
                                        Toast.makeText(Login.this, "Username-Password mismatch", Toast.LENGTH_SHORT).show();
                                        password.setText("");
                                    } else {
                                        Toast.makeText(Login.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                        password.setText("");
                                        username.setText("");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mDialog.dismiss();

                            }
                        }.execute();
                    } else {
                        Toast.makeText(Login.this, "No internet Connection!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, "Enter credentials first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}