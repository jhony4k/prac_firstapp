package shamim.prac_firstapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    Button btn_signup, btn_login, btn_viewall,btn_uploadpic;
    EditText edt_username, edt_password,edt_name,edt_level,edt_sid,edt_earnedcredit;
    String sname,slevel,ssid,searnedcredit,imageUrl;
    ImageView img_picture;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String email,password;
    private DatabaseReference refDatabase;
    private StudentInfo student;


    private StorageReference mStorageRef;
    private String imageDownloadUrl;
    private Uri uri=null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if(requestCode==IMAGE_READ_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            if(resultData != null){
                uri=resultData.getData();
                img_picture.setImageURI(null);
                img_picture.setImageURI(uri);
            }
        }
    }

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

        img_picture=(ImageView)findViewById(R.id.img_picture);
        btn_uploadpic=(Button) findViewById(R.id.btn_uploadpic);

        //For Photo Upload

        mStorageRef = FirebaseStorage.getInstance().getReference("images/");

        btn_uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }

        });

        //Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        refDatabase=FirebaseDatabase.getInstance().getReference("student");

    }

    private void openGallery() {
        Intent pickImage=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        pickImage.addCategory(Intent.CATEGORY_OPENABLE);
        pickImage.setType("image/*");
        startActivityForResult(pickImage,IMAGE_READ_REQUEST_CODE);

    }

    private static final int IMAGE_READ_REQUEST_CODE=10;


    private void signIn() {

            getAllInputData();
            //createStudent();
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
        student=new StudentInfo(sname,ssid,slevel,searnedcredit,imageDownloadUrl);
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
                            final FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(getApplicationContext(),"Log In Successful",Toast.LENGTH_SHORT).show();
                            StorageReference imgRef=mStorageRef.child(user.getEmail()+"/");

                            imgRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                                    imageDownloadUrl=downloadUrl.toString();
                                    createStudent();
                                    refDatabase=FirebaseDatabase.getInstance().getReference("student");
                                    refDatabase.child(student.getSsid()).setValue(student);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed Storage",Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

}
