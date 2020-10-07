package co.com.ceiba.socketapplication.game.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.com.ceiba.socketapplication.R;

public class StopFormFragment extends Fragment {

    private boolean fromClient = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.stop_form_fragment, container, false);
    }
}