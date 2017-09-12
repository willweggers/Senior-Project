package com.example.seniorproject;
//too access TI enter TI into username/ M enter M/ A enter A/ leave pass blank
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivityLogin extends AppCompatActivity {
    private Button submitButton;
    private EditText userName;
    private EditText password;
    //stores actual un and pass inputed by user
    private String actualUserName;
    private String actualPassWord;
    //below store temp un and pass for each account
    //might change depending on what client wants
    //current one account accesses certain UI instead of individual
    //expected un/pass for Track Inspectors
    private String expUserNameTI = "ti";
    private String expPassWordTI = "Password";
    //expected un/pass for Managers
    private String expUserNameM = "m";
    private String expPassWordM = "Password";
    //expected un/pass for Admins
    private String expUserNameA = "a";
    private String expPassWordA = "Password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        userName= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.passWord);
        submitButton= (Button) findViewById(R.id.submit);

        setSubmitButton();

    }
    //sets submit button to send user to either TI,Manager, or Admin UI
    //if actual UN and pass dont equal an exp UN and pass then give user pop up that says there wrong
    private void setSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getUserNamePassWord();
                //if UN and pass equal TI UN and pass then submit button goes to TI UI
                if(actualUserName.equals(expUserNameTI) && actualPassWord.equals(expPassWordTI)) {
                    Intent intent = new Intent(MainActivityLogin.this,TrackInsepctor.class);
                    startActivity(intent);
                }
                //if Un and pass equal Manager UN and pass then submit button goes to Manager UI
                else if((actualUserName.equals(expUserNameM))&& (actualPassWord.equals(expPassWordM))){
                    Intent intent = new Intent(MainActivityLogin.this,Manager.class);
                    startActivity(intent);
                }
                //if Un and pass equal Manager UN and pass then submit button goes to admin UI
                else if(actualUserName.equals(expUserNameA) && actualPassWord.equals(expPassWordA)){
                    Intent intent = new Intent(MainActivityLogin.this,Admin.class);
                    startActivity(intent);
                }
                //dont equal show popup
                else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityLogin.this);
                    builder.setTitle("Error")
                            .setMessage("Either username or password is incorrect.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
    }
    //gets whatever is in edit text fields and stores it to variables specified prev
    private void getUserNamePassWord(){
        actualUserName = userName.getText().toString();
        actualPassWord = password.getText().toString();
    }
}
