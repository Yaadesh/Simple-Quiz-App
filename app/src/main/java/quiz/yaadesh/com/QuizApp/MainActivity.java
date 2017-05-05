package quiz.yaadesh.com.QuizApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static Context mContext;

    public static final String CONST_ID="id";
    public static final String SERVER_URL="http://192.168.1.107/quiz.php";
    private  ImageButton StartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView quiztext =(TextView) findViewById(R.id.textView2);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/TrebuchetMS.ttf");
        quiztext.setTypeface(type);

        StartButton = (ImageButton) findViewById(R.id.button);

        StartButton.setOnClickListener(this);

         mContext = getBaseContext();

    }
    @Override
    public void onClick(View v){
        if(v == StartButton){
            Question_reveal("1");
        }
    }


public static void Question_reveal(final String id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                        QuestionPage questionPage = new QuestionPage();
                        Intent intent = new Intent(mContext,QuestionPage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Response",response);
                        mContext.startActivity(intent);

                        //Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(mContext,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(CONST_ID,id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }
}
