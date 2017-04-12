package groupproject.cmsc436.flow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by Junze on 4/11/2017.
 */

public class LogInFragment extends Fragment {
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    private Button signInButton;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Intent intent = new Intent(getActivity().getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        emailTextView = (AutoCompleteTextView) view.findViewById(R.id.email);
        passwordTextView = (EditText) view.findViewById(R.id.password);
        signInButton = (Button) view.findViewById(R.id.email_sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.none_fields_blank, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        DatabaseService.getDBService(getActivity().getApplicationContext()).signIn(email, password);
                        getActivity().finish();
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public static Fragment newInstance() {
        return new LogInFragment();
    }
}
