
package groupproject.cmsc436.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import groupproject.cmsc436.flow.Service.DatabaseService;

/**
 * Created by Junze on 4/11/2017.
 */

public class RegistrationFragment extends Fragment {

    private EditText usernameEditText;
    private EditText lastnameEditText;
    private EditText firstnameEditText;
    private EditText passEditText;
    private EditText repeatPassEditText;
    private Button cancelButton;
    private Button registerButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        usernameEditText = (EditText)view.findViewById(R.id.register_username);
        firstnameEditText = (EditText)view.findViewById(R.id.register_firstname);
        lastnameEditText = (EditText)view.findViewById(R.id.register_lastname);
        passEditText = (EditText)view.findViewById(R.id.register_password);
        repeatPassEditText = (EditText)view.findViewById(R.id.register_repeat_password);

        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        registerButton = (Button) view.findViewById(R.id.register_registration_button);
        registerButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String first = firstnameEditText.getText().toString();
                String last = lastnameEditText.getText().toString();
                String password = passEditText.getText().toString();
                String password2 = repeatPassEditText.getText().toString();
                if (username.isEmpty() ||  first.isEmpty() || last.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.none_fields_blank, Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password2)) {
                    Toast.makeText(getActivity(), "The two passwords you entered are different", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseService.getDBService().signUp(username, password, first, last, getActivity().getApplicationContext());
                    getActivity().finish();
                }
            }
        });
        return view;
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }
}