package co.com.ceiba.socketapplication.game.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import co.com.ceiba.socketapplication.R;
import co.com.ceiba.socketapplication.game.GameActivity;

public class WelcomeFragment extends Fragment {

    private TextView lblLocalIpAddress;
    private EditText txtClientAddress;
    private Button btnInvite;

    private String localIpAddress;

    public static WelcomeFragment newInstance(String localIpAddress) {
        WelcomeFragment welcomeFragment = new WelcomeFragment();

        Bundle args = new Bundle();
        args.putString("localIpAddress", localIpAddress);
        welcomeFragment.setArguments(args);

        return welcomeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_fragment, container, false);

        initViews(view);
        setupViews();
        return view;
    }

    private void initViews(View view) {
        lblLocalIpAddress = view.findViewById(R.id.lblLocalIpAddress);
        txtClientAddress = view.findViewById(R.id.txtClientAddress);
        btnInvite = view.findViewById(R.id.btnInvite);
    }

    private void setupViews() {

        localIpAddress = getArguments().getString("localIpAddress");
        lblLocalIpAddress.setText(localIpAddress);

        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtClientAddress.getText().toString().trim().equals("")) {
                    ((GameActivity)getActivity()).setClientIpAddress(txtClientAddress.getText().toString().trim());
                    ((GameActivity)getActivity()).callBeginGameFragment(false);
                }
                else {
                    Toast.makeText(getActivity(), "Debes ingresar la dirección IP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}