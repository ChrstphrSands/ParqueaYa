package com.example.parqueaya.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.parqueaya.R;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Vehiculo;
import com.example.parqueaya.services.DataService;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";
    //    private static final String TAG_FACE = "FacebookLogin";
    private static final int RC_SIGN_IN = 9001;

    private TextView signUp;
    private EditText mEmailField;
    private EditText mPasswordField;
    private LoginButton facebookButton;
    private String email;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient mGoogleSignInClient;
    private ParkingApi parkingApi;
    private List<Cliente> mClientes;
    private String currentUser;
    private CallbackManager mCallbackManager;

    private Cliente cliente;
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private boolean exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.googleSignInButton).setOnClickListener(this);
        findViewById(R.id.facebookSignInButton).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);

        facebookButton = findViewById(R.id.facebookSignInButton);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "Usuario logueado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario no logueado", Toast.LENGTH_SHORT).show();
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("451162286488-1ta7h8epe8t1c5j0damnj9qeudadc0im.apps.googleusercontent.com")
            .requestEmail()
            .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mCallbackManager = CallbackManager.Factory.create();

        facebookButton.setReadPermissions("email", "public_profile", "user_friends");

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest
            .GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject jsonObject = response.getJSONObject();
                try {
                    email = jsonObject.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        facebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }


    private void signInWithEmail(String email, String password) {
        Log.d(TAG, "signInWithEmail:" + email);
        if (!validateForm()) {
            return;
        }

        //        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        //                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        //                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Autenticaciokn fallida.",
                            Toast.LENGTH_SHORT).show();
                    }

                    if (!task.isSuccessful()) {

                        //                        mStatusTextView.setText(R.string.auth_failed);
                    }
                    //                    hideProgressDialog();

                }
            });
    }

    private void signInWithGoogle() {
        Intent signInIntentWithGoogle = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntentWithGoogle, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        exists = false;
                        Log.d("Clientes", String.valueOf(mClientes));

                        for (Cliente cli : mClientes) {
                            if (cli.getUID().equals(currentUser)) {
                                exists = true;
                                break;
                            }
                        }

                        if (exists == false) {
                            cliente = new Cliente(acct.getFamilyName(), "", 0, 0, acct.getDisplayName(), 0, acct.getEmail(), vehiculos, currentUser);
                            saveCliente(cliente);
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication Failed.", Toast.LENGTH_LONG)
                            .show();
                    }
                    //                    hideProgressDialog();
                }
            });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        exists = false;

                        for (Cliente cli : mClientes) {
                            if (cli.getUID().equals(currentUser)) {
                                exists = true;
                                break;
                            }
                        }

                        Profile profile = Profile.getCurrentProfile();

                        if (exists == false) {
                            cliente = new Cliente(profile.getLastName(), "", 0, 0, profile.getFirstName(), 0, email,
                                vehiculos, currentUser);
                            saveCliente(cliente);
                        }

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    }

                    // hideProgressDialog();
                }
            });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void saveCliente(Cliente cliente) {
        DataService.getInstance().setCliente(cliente);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailSignInButton) {
            signInWithEmail(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.googleSignInButton) {
            signInWithGoogle();
        } else if (i == R.id.login_button) {
            facebookButton.performClick();
        } else if (i == R.id.sign_up) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}
