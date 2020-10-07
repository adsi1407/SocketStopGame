package co.com.ceiba.socketapplication.game.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import co.com.ceiba.socketapplication.R;
import co.com.ceiba.socketapplication.game.GameActivity;

public class StopFormFragment extends Fragment {

    private TextView lblRound;
    private TextView lblLetter;
    private TextView lblTime;
    private EditText txtName;
    private EditText txtLastName;
    private EditText txtCity;
    private EditText txtAnimal;
    private Button btnNewRound;
    private Button btnFinish;

    private boolean fromClient = false;
    private int roundCounter = 1;

    public static StopFormFragment newInstance(String localIpAddress, String clientIpAddress, boolean fromClient) {
        StopFormFragment stopFormFragment = new StopFormFragment();

        Bundle args = new Bundle();
        args.putString("localIpAddress", localIpAddress);
        args.putString("clientIpAddress", clientIpAddress);
        stopFormFragment.setArguments(args);

        stopFormFragment.fromClient = fromClient;
        return stopFormFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stop_form_fragment, container, false);

        if (!fromClient) {
            ((GameActivity)getActivity()).sendMessage("Connect");
        }

        initViews(view);
        setupViews();
        return view;
    }

    private void initViews(View view) {
        lblRound = view.findViewById(R.id.lblRound);
        lblLetter = view.findViewById(R.id.lblLetter);
        lblTime = view.findViewById(R.id.lblTime);
        txtName = view.findViewById(R.id.txtName);
        txtLastName = view.findViewById(R.id.txtLastName);
        txtCity = view.findViewById(R.id.txtCity);
        txtAnimal = view.findViewById(R.id.txtAnimal);
        btnNewRound = view.findViewById(R.id.btnNewRound);
        btnFinish = view.findViewById(R.id.btnFinish);
    }

    private void setupViews() {

        lblRound.setText("Ronda # " + String.valueOf(roundCounter));
        lblLetter.setText("Letra " + setLetter());

        setTimer();
    }

    private String setLetter() {
        Random rnd = new Random();
        char randomChar = (char) ('a' + rnd.nextInt(26));
        return String.valueOf(randomChar).toUpperCase();
    }

    private void setTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lblTime.setText("Tiempo " + String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getActivity(), "El tiempo terminó", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();
    }
}