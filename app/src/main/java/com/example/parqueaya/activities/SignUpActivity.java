package com.example.parqueaya.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.parqueaya.R;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Vehiculo;
import com.example.parqueaya.services.DataService;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createAccount;
    private EditText nombre;
    private EditText email;
    private EditText password;
    private EditText confirm_password;

    private boolean exists;
    private List<Cliente> mClientes;
    private Cliente cliente;
    private List<Vehiculo> vehiculos = new ArrayList<>();

    private String currentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ParkingApi parkingApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        createAccount = findViewById(R.id.btnCreateAccount);
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.password_confirm);

        createAccount.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(SignUpActivity.this, "Usuario logueado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Usuario no logueado", Toast.LENGTH_SHORT).show();
                }
            }
        };

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Registrarse");
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        final String mEmail = email.getText().toString();
        String mPassword = password.getText().toString();

        if (i == R.id.btnCreateAccount) {

            mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);

                        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        exists = false;

                        for (Cliente cli : mClientes) {
                            if (cli.getUID().equals(currentUser)) {
                                exists = true;
                                break;
                            }
                        }

                        if (exists == false) {
                            cliente = new Cliente(nombre.getText().toString(), "", 0, 0, nombre.getText().toString(), 0, mEmail,
                                vehiculos, currentUser);
                            saveCliente(cliente);
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Cuenta no creada",
                            Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

        parkingApi = RetrofitInstance.createService(ParkingApi.class);

        Call<List<Cliente>> callCliente = parkingApi.getClientes();

        callCliente.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    mClientes = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("Error cliente", t.getLocalizedMessage());
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        }

        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    private void saveCliente(Cliente cliente) {
        DataService.getInstance().setCliente(cliente);
    }
}
