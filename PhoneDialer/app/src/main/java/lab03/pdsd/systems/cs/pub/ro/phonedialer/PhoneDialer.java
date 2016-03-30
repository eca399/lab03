package lab03.pdsd.systems.cs.pub.ro.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import general.Constants;

public class PhoneDialer extends AppCompatActivity {

    EditText phoneNumberEditText = null;
    ButtonOnClickListener buttonOnClickListener = new ButtonOnClickListener();
    CallButtonOnClickListener callButtonOnClickListener = new CallButtonOnClickListener();
    HangUpOnClickListener hangUpOnClickListener = new HangUpOnClickListener();
    BackspaceOnClickListener backspaceOnClickListener = new BackspaceOnClickListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        phoneNumberEditText = (EditText) findViewById(R.id.phone_dialer_edit_text);
        for (int i = 0; i < Constants.buttonIds.length; i++) {
            Button button = (Button)findViewById(Constants.buttonIds[i]);
            button.setOnClickListener(buttonOnClickListener);
        }

        ImageButton backspaceButton = (ImageButton) findViewById(R.id.backspace_button);
        backspaceButton.setOnClickListener(backspaceOnClickListener);

        ImageButton callButton = (ImageButton) findViewById(R.id.call_button);
        callButton.setOnClickListener(callButtonOnClickListener);

        ImageButton hangUpButton = (ImageButton) findViewById(R.id.hangup_button);
        hangUpButton.setOnClickListener(hangUpOnClickListener);

        Button starButton = (Button) findViewById(R.id.star_button);
        starButton.setOnClickListener(buttonOnClickListener);

        Button poundBotton = (Button) findViewById(R.id.pound_button);
        poundBotton.setOnClickListener(buttonOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone_dialer, menu);
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

    private class ButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)v).getText().toString());
        }
    }

    private class CallButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phoneNumber));

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    private class HangUpOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            phoneNumberEditText.setText("");
            finish();
        }
    }

    private class BackspaceOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (phoneNumberEditText.getText().toString().length() > 0) {
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString().substring(0, phoneNumberEditText.getText().toString().length() - 1));
            }
        }
    }
}
