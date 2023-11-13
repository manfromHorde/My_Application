package com.example.my_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_application.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity {

    EditText inputName,inputEmail,inputPassword,inputConfirmPassword;
    Button registerButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private ActivitySignUpBinding binding;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();



        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
   inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
   registerButton = findViewById(R.id.registerButton);
   progressDialog = new ProgressDialog(this);
   mAuth = FirebaseAuth.getInstance();
mUser =mAuth.getCurrentUser();
    }

    private  void setListeners(){
        binding.textSignIn.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),SignInActivity.class)));

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });
    }private  void PerforAuth(){
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();


        if(email.matches(emailPattern)){
            inputEmail.setError("Enter Connext Email");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Enter Proper Password ");
        } else if (password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Password Not match Both field");
        }else {
progressDialog.setMessage("Wait While Registration...");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if (task.isSuccessful()){
    progressDialog.dismiss();
    sendUserToNextActivity();
    Toast.makeText(SignUpActivity.this,"Register Successful", Toast.LENGTH_SHORT).show();
}else{
    progressDialog.dismiss();
    Toast.makeText(SignUpActivity.this,""+task.getException(), Toast.LENGTH_SHORT).show();
}
            }
        });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUpActivity.this,Page1Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}