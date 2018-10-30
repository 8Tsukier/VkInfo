package a8tsukier.com.vkinfo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static a8tsukier.com.vkinfo.utils.NetworkUtils.generateURL;
import static a8tsukier.com.vkinfo.utils.NetworkUtils.getResponceFromURL;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private Button searchButton;
    private TextView result;

    class VKQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            String responce = null;
            try {
                responce = getResponceFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responce;
        }

        @Override
        protected void onPostExecute(String responce){
            String firstName = null;
            String lastName = null;

            try {
                JSONObject jsonResponce = new JSONObject(responce);
                JSONArray jsonArray = jsonResponce.getJSONArray("responce");
                JSONObject userInfo = jsonArray.getJSONObject(0);

                firstName = userInfo.getString("first_name");
                lastName = userInfo.getString("last_name");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String resultString = "Имя: " + firstName + "\n" + "Фамилия: " + lastName + "\n";

            result.setText(resultString);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_field);
        searchButton = findViewById(R.id.b_search_vk);
        result = findViewById(R.id.tv_result);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL generatedURL = generateURL(searchField.getText().toString());

                new VKQueryTask().execute(generatedURL);

            }
        };

        searchButton.setOnClickListener(onClickListener);
    }
}
