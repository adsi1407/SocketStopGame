package co.com.ceiba.socketapplication.game.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.com.ceiba.socketapplication.R;
import co.com.ceiba.socketapplication.game.GameActivity;

public class BeingGameFragment extends Fragment {

    private TextView lblTitle;
    private Button btnPlay;
    private boolean fromClient = false;

    public static BeingGameFragment newInstance(String localIpAddress, String clientIpAddress, boolean fromClient) {
        BeingGameFragment beingGameFragment = new BeingGameFragment();

        Bundle args = new Bundle();
        args.putString("localIpAddress", localIpAddress);
        args.putString("clientIpAddress", clientIpAddress);
        beingGameFragment.setArguments(args);

        beingGameFragment.fromClient = fromClient;
        return beingGameFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.being_game_fragment, container, false);

        if (!fromClient) {
            ((GameActivity)getActivity()).sendMessage("Connect");
        }

        initViews(view);
        setupViews();
        return view;
    }

    private void initViews(View view) {
        lblTitle = view.findViewById(R.id.lblTitle);
        btnPlay = view.findViewById(R.id.btnPlay);
    }

    private void setupViews() {
        lblTitle.setText("Vas a jugar con\n" + getArguments().getString("clientIpAddress"));

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity)getActivity()).sendMessage("Begin");
                ((GameActivity)getActivity()).callStopFormFragment(false);
            }
        });
    }
}