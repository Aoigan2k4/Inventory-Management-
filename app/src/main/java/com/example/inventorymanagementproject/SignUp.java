package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.Users.Admin;
import com.example.inventorymanagementproject.Users.Client;
import com.example.inventorymanagementproject.Users.Staff;
import com.example.inventorymanagementproject.Users.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText usernameTxt, passTxt, emailTxt;
    Button signUpBtn;
    RadioGroup roles;
    RadioButton admin, staff, client;
    TextView login;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    User newUser;
    FirebaseManager mng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameTxt = findViewById(R.id.Username);
        passTxt = findViewById(R.id.Password);
        emailTxt = findViewById(R.id.Email);
        signUpBtn = findViewById(R.id.SignUpBtn);
        login = findViewById(R.id.loginLink);
        roles = findViewById(R.id.roleRadioGroup);
        admin = findViewById(R.id.adminRadioButton);
        staff = findViewById(R.id.staffRadioButton);
        client = findViewById(R.id.clientRadioButton);

        signUpBtn.setOnClickListener(v -> SignUpBtn());
        login.setOnClickListener(v -> LogInLink());
    }

    private void SignUpBtn() {
        String username = usernameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();

        // Get the selected role from the radio group
        int selectedRoleId = roles.getCheckedRadioButtonId();
        String selectedRole;
        if (selectedRoleId == -1) {
            selectedRole = "Client"; // default role if none is selected
        } else {
            RadioButton selectedRadioButton = findViewById(selectedRoleId);
            selectedRole = selectedRadioButton.getText().toString();
        }

        mng = FirebaseManager.getInstance();
        db = mng.getDb();
        mAuth = mng.getAuth();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isComplete()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            newUser = Roles(selectedRoleId, uid, username, email);

                            db.collection("Users")
                                    .document(selectedRole)
                                    .collection(selectedRole)
                                    .document(newUser.getId())
                                    .set(newUser)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(SignUp.this, "User created!", Toast.LENGTH_SHORT).show();
                                        // Navigate to InventoryActivity and pass the selected role
                                        Intent intent = new Intent(SignUp.this, InventoryActivity.class);
                                        intent.putExtra("role", selectedRole);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignUp.this, e.toString(), Toast.LENGTH_LONG).show();
                                        login.setText(e.toString());
                                    });
                        } else {
                            Toast.makeText(SignUp.this, "Failed to create user Auth", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private User Roles(int code, String uid, String username, String email) {
        if (code == R.id.adminRadioButton) {
            newUser = new Admin(uid, username, email);
            return newUser;
        } else if (code == R.id.staffRadioButton) {
            newUser = new Staff(uid, username, email);
            return newUser;
        } else if (code == R.id.clientRadioButton) {
            newUser = new Client(uid, username, email);
            return newUser;
        } else {
            // Fallback default if no radio button is selected
            newUser = new Client(uid, username, email);
            return newUser;
        }
    }

    private void LogInLink(){
        // Navigate back to the LogIn screen
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
        finish();
    }
}
