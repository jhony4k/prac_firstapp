package shamim.prac_firstapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btn_signup, btn_login, btn_viewall;
    EditText edt_username, edt_password,edt_name,edt_level,edt_sid,edt_earnedcredit;
    String sname,slevel,ssid,searnedcredit;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String email,password;
    private DatabaseReference refDatabase;
    private StudentInfo student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_earnedcredit=(EditText) findViewById(R.id.edt_earnedcredit);
        edt_sid=(EditText) findViewById(R.id.edt_sid);
        edt_level=(EditText) findViewById(R.id.edt_level);
        edt_name=(EditText) findViewById(R.id.edt_name);
        btn_viewall=(Button) findViewById(R.id.btn_viewall);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_signup=(Button)findViewById(R.id.btn_login);
        edt_username=(EditText)findViewById(R.id.edt_username);
        edt_password=(EditText)findViewById(R.id.edt_password);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        refDatabase=FirebaseDatabase.getInstance().getReference("student");

    }

    private void signIn() {

            getAllInputData();
            createStudent();
            createAccountANdSaveInfo();
        /*email=edt_username.getText().toString().trim();
        password=edt_password.getText().toString().trim();
        progressDialog.setMessage("Please Wait !!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Log In Successful",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

*/
    }
    private void getAllInputData(){
        sname=edt_name.getText().toString().trim();
        slevel=edt_level.getText().toString().trim();
        ssid=edt_sid.getText().toString().trim();
        searnedcredit=edt_earnedcredit.getText().toString().trim();
    }
    void createStudent(){
        student=new StudentInfo(sname,ssid,slevel,searnedcredit);
    }

    public void createAccountANdSaveInfo(){
        email=edt_username.getText().toString().trim();
        password=edt_password.getText().toString().trim();
        progressDialog.setMessage("Please Wait !!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Log In Successful",Toast.LENGTH_SHORT).show();
                            refDatabase.child(user.getUid()).setValue(student);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

}
