package quiz.yaadesh.com.QuizApp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yaadesh on 08-12-2016.
 */
public class QuestionPage extends AppCompatActivity implements View.OnClickListener {

    private JSONObject j;
    private String id;
    private String question;
    private String Option1;
    private String Option2;
    private String Option3;
    private String Option4;


    private TextView ques_text;
    private TextView Op1_text;
    private TextView Op2_text;
    private TextView Op3_text;
    private TextView Op4_text;


    public final String QCHECK_URL="http://192.168.1.107/qanscheck.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_page);

        Intent i = getIntent();
        String response = i.getStringExtra("Response");
        try {
            j = new JSONObject(response);

            question = j.getString("question");
            id=j.getString("id");
            Option1 = j.getString("option1");
            Option2 = j.getString("option2");
            Option3 = j.getString("option3");
            Option4 = j.getString("option4");

            System.out.println("OOO"+Option4);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


        ques_text= (TextView) findViewById(R.id.quesId);
        Op1_text = (TextView) findViewById(R.id.op1);
        Op2_text = (TextView) findViewById(R.id.op2);
        Op3_text = (TextView) findViewById(R.id.op3);
        Op4_text = (TextView) findViewById(R.id.op4);

        System.out.println(Option4);



        ques_text.setText(question);
        Op1_text.setText(Option1);
        Op2_text.setText(Option2);
        Op3_text.setText(Option3);
        Op4_text.setText(Option4);


        Op1_text.setOnClickListener(this);
        Op2_text.setOnClickListener(this);
        Op3_text.setOnClickListener(this);
        Op4_text.setOnClickListener(this);



    }

    @Override
    public void onClick(View v){
       if(v==Op1_text){send_data(Option1,v);}
        if(v==Op2_text){send_data(Option2,v);}
        if(v==Op3_text){send_data(Option3,v);}
        if(v==Op4_text){send_data(Option4,v);}

    }

    private void send_data(final String answer, final View v){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, QCHECK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                        try {
                            JSONObject j = new JSONObject(response);
                            Boolean check = j.getBoolean("check");
                            String id = j.getString("id");

                            if(check){
                                //SET COLOR TO GREEN MAYBE TIMEOUT AND...
                                v.setBackgroundColor(Color.GREEN);
                                Toast.makeText(getBaseContext()," Correct!",Toast.LENGTH_SHORT).show();
                                int int_id= Integer.parseInt(id);
                                int_id++;
                                if(int_id!=5) {
                                    MainActivity.Question_reveal(String.valueOf(int_id));
                                }
                                else{

                                    Intent i = new Intent(getBaseContext(),Thanks.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getBaseContext().startActivity(i);
                                }

                            }
                            else{
                                v.setBackgroundColor(Color.RED);
                                Toast.makeText(getBaseContext(),"Wrong! Try Again ",Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(getBaseContext(),response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(QuestionPage.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("question",question);
                params.put("answer",answer );

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
