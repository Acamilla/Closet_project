package project.lellon.closet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class InfActivity extends AppCompatActivity {
    private final String IP_ADDRESS ="192.168.43.67";
    private ArrayList<closet_item> mArrayList;
    TextView nameText;
    TextView RfidText;
    TextView DateText;
    TextView MemoText;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);
        Intent intent = new Intent(this.getIntent());
        String name = intent.getStringExtra("name");
        String rfid = intent.getStringExtra("rfid");
        String date = intent.getStringExtra("date");
        String memo = intent.getStringExtra("memo");


        nameText =  findViewById(R.id.IdText);
        DateText =  findViewById(R.id.DateText);
        RfidText =  findViewById(R.id.RFIDText);
        MemoText =  findViewById(R.id.MemoText);
        btn =  findViewById(R.id.goEditButton);

        nameText.setText(name);
        DateText.setText(date);
        RfidText.setText(rfid);
        MemoText.setText(memo);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfActivity.this, editActivity.class);
                intent.putExtra("name",nameText.getText().toString());
                intent.putExtra("memo",MemoText.getText().toString());
                intent.putExtra("date",DateText.getText().toString());
                intent.putExtra("rfid",RfidText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
