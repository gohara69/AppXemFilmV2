package com.example.appxemfilm.viewmodels;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.appxemfilm.R;
import com.example.appxemfilm.model.RequestToken;
import com.example.appxemfilm.model.Session;
import com.example.appxemfilm.request.Servicey;
import com.example.appxemfilm.response.TokenStatusResponse;
import com.example.appxemfilm.utils.Credentials;
import com.example.appxemfilm.utils.RequestTokenApi;
import android.content.Intent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    EditText edtuser,edtpassword;
    Button btndangnhap,btnthoat;
    TextView text_view_error;
    RequestToken firstTimeToken;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Anhxa();
        ControlButton();
    }

    private void ControlButton(){
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có chắc chắn muốn thoát khỏi app");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequestToken();
            }
        });
    }
    private void Anhxa(){
        edtuser = (EditText) findViewById(R.id.edittextuser);
        edtpassword = (EditText) findViewById(R.id.edittextpassword);
        btndangnhap = (Button) findViewById(R.id.buttondangnhap);
        btnthoat = (Button) findViewById(R.id.buttonthoat);
        text_view_error = findViewById(R.id.text_view_error);
        context = this;
    }

    public void getRequestToken(){
        Servicey servicey = new Servicey();
        RequestTokenApi requestTokenApi = servicey.getRequestTokenApi();
        Call<RequestToken> requestTokenCall = requestTokenApi.getToken(
                Credentials.API_KEY
        );
        requestTokenCall.enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(Call<RequestToken> call, Response<RequestToken> response) {
                RequestToken firstToken = response.body();
                firstTimeToken = firstToken;
                getSecondToken();
            }

            @Override
            public void onFailure(Call<RequestToken> call, Throwable t) {

            }
        });
    }

    public void getSecondToken(){
        Servicey servicey = new Servicey();
        RequestTokenApi requestTokenApi = servicey.getRequestTokenApi();
        Call<TokenStatusResponse> requestCall = requestTokenApi.getTokenStatus(
                Credentials.API_KEY,
                firstTimeToken.getRequest_token(),
                edtuser.getText().toString(),
                edtpassword.getText().toString()
        );
        requestCall.enqueue(new Callback<TokenStatusResponse>() {
            @Override
            public void onResponse(Call<TokenStatusResponse> call, Response<TokenStatusResponse> response) {
                text_view_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<TokenStatusResponse> call, Throwable t) {
                text_view_error.setVisibility(View.INVISIBLE);
                getSessionId();
            }
        });
    }

    public void getSessionId(){
        Servicey servicey = new Servicey();
        RequestTokenApi requestTokenApi = servicey.getRequestTokenApi();
        Call<Session> call = requestTokenApi.getSession(
                Credentials.API_KEY,
                firstTimeToken.getRequest_token()
        );
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if(response.code() == 200){
                    Session session = response.body();
                    Intent intent = new Intent(context, HomeActivity.class);
                    intent.putExtra("session", session.getSession_id());
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {

            }
        });
    }
}