package project.lellon.closet;




import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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


public class SubActivity extends AppCompatActivity {

    private String mJsonString;
    private ArrayList<closet_item> mArrayList;
    private CardAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private final String IP_ADDRESS ="192.168.43.67";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        mArrayList = new ArrayList<>();
        mArrayList.clear();

        mAdapter = new CardAdapter(getApplicationContext(), mArrayList);
        mRecyclerView  = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        selectdata task = new selectdata();
        task.execute( "http://" + IP_ADDRESS + "/select_closet.php", "");
    }



    class selectdata extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;
        String mJsonString;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SubActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d("TAG", "response - " + result);

            if (result == null){

                Log.d("errorString",errorString);
            }
            else {

                Log.d("result",result);
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "name=" + params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TAG", "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d("TAG", "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }


        private void showResult(){

            String TAG_JSON="data";

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    String name = item.getString("name");
                    String date = item.getString("date");
                    String RFID_type = item.getString("rfid");
                    String memo = item.getString("memo");
                    boolean exist = (item.getInt("exist") == 1);
                    Log.d("exist", String.valueOf(exist));
                    closet_item closetItem = new closet_item();

                    closetItem.setName(name);
                    closetItem.setDate(date);
                    closetItem.setMemo(memo);
                    closetItem.setRFID_type(RFID_type);
                    closetItem.setExist(exist);


                    mArrayList.add(closetItem);
                    mAdapter.notifyDataSetChanged();
                }



            } catch (JSONException e) {

                Log.d("TAG", "showResult : ", e);
            }

        }

    }
}
