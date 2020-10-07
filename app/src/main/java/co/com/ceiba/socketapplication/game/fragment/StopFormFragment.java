package co.com.ceiba.socketapplication.game.fragment;

import android.app.AlertDialog;
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
import co.com.ceiba.socketapplication.game.Round;

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

    private int roundCounter = 1;

    public static StopFormFragment newInstance(String localIpAddress, String clientIpAddress) {
        StopFormFragment stopFormFragment = new StopFormFragment();

        Bundle args = new Bundle();
        args.putString("localIpAddress", localIpAddress);
        args.putString("clientIpAddress", clientIpAddress);
        stopFormFragment.setArguments(args);

        return stopFormFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stop_form_fragment, container, false);

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

        btnNewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
                roundCounter++;
                lblRound.setText("Ronda # " + String.valueOf(roundCounter));
                lblLetter.setText("Letra " + setLetter());
                btnNewRound.setEnabled(false);
                btnFinish.setEnabled(false);
                setTimer();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalValue = ((GameActivity)getActivity()).calculateTotalValue();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Resultado de la Ronda");
                alertDialog.setMessage(String.valueOf(totalValue));
                alertDialog.setPositiveButton("Aceptar", null);
                alertDialog.create().show();

                clearFields();
                roundCounter = 1;
                lblRound.setText("Ronda # " + String.valueOf(roundCounter));
            }
        });
    }

    private String setLetter() {
        Random rnd = new Random();
        char randomChar = (char) ('a' + rnd.nextInt(26));
        return String.valueOf(randomChar).toUpperCase();
    }

    private void setTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lblTime.setText("Tiempo " + String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                //Toast.makeText(getActivity(), "El tiempo terminó", Toast.LENGTH_SHORT).show();

                Round round = new Round(txtName.getText().toString().trim(),
                        txtLastName.getText().toString().trim(),
                        txtCity.getText().toString().trim(),
                        txtAnimal.getText().toString().trim());

                ((GameActivity)getActivity()).addRound(round);
                int roundValue = ((GameActivity)getActivity()).calculateRoundValue(round);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Resultado de la Ronda");
                alertDialog.setMessage(String.valueOf(roundValue));
                alertDialog.setPositiveButton("Aceptar", null);
                alertDialog.create().show();

                btnNewRound.setEnabled(true);
                btnFinish.setEnabled(true);
            }
        };
        countDownTimer.start();
    }

    private void clearFields() {
        txtName.setText("");
        txtLastName.setText("");
        txtCity.setText("");
        txtAnimal.setText("");
    }
}