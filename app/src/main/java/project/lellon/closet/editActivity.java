package project.lellon.closet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class editActivity extends AppCompatActivity {
    private final String IP_ADDRESS ="192.168.43.67";
    Button send;
    String name;
    String date;
    String memo;
    String rfid;
    EditText edit_name, edit_memo;
    TextView edit_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = new Intent(this.getIntent());
        name = intent.getStringExtra("name");
        memo = intent.getStringExtra("memo");
        date = intent.getStringExtra("date");
        rfid = intent.getStringExtra("rfid");

        send = findViewById(R.id.UploadButton);
        edit_name = findViewById(R.id.EditName);
        edit_memo = findViewById(R.id.EditMemo);
        edit_date = findViewById(R.id.TextDate);

        edit_date.setText(date);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData updateData =new UpdateData();
                Log.d("Test",edit_name.getText().toString()+", " + edit_memo.getText().toString()+", " + edit_date.getText().toString());
                updateData.execute( "http://" + IP_ADDRESS + "/update_closet.php", String.valueOf(edit_name.getText()), String.valueOf(edit_memo.getText()),String.valueOf(edit_date.getText()));
                Intent intent = new Intent(editActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }
    class UpdateData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
        protected String doInBackground(String... params) {

            String name =  params[1];
            String memo =  params[2];
            String date = params[3];
            String serverURL = params[0];
            String postParameters = "name=" + name + "&memo=" + memo + "&date=" + date ;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("utf8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TAG", "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            }

            catch (Exception e) {

                Log.d("TAG", "InsertData: Error ", e);

                return "Error: " + e.getMessage();
            }

        }

    }
}
