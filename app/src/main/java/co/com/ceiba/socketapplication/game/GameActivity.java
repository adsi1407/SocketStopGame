package co.com.ceiba.socketapplication.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import co.com.ceiba.socketapplication.NetworkManager;
import co.com.ceiba.socketapplication.R;
import co.com.ceiba.socketapplication.game.fragment.BeingGameFragment;
import co.com.ceiba.socketapplication.game.fragment.StopFormFragment;
import co.com.ceiba.socketapplication.game.fragment.WelcomeFragment;

public class GameActivity extends AppCompatActivity {

    private NetworkManager networkManager;
    private String localIpAddress = "";
    private String clientIpAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Thread messageThread = new Thread(new MySocketServer(this));
        messageThread.start();

        getLocalIpAddress();
        if (savedInstanceState == null) {
            callWelcomeFragment();
        }
    }

    private void getLocalIpAddress() {
        networkManager = new NetworkManager(this);
        try {
            localIpAddress = networkManager.getLocalIpAddress();
        }
        catch(UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void callWelcomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, WelcomeFragment.newInstance(localIpAddress))
                .commitNow();
    }

    public void callBeginGameFragment(boolean fromClient) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, BeingGameFragment.newInstance(localIpAddress, clientIpAddress, fromClient))
                .commitNow();
    }

    public void callStopFormFragment(boolean fromClient) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, StopFormFragment.newInstance(localIpAddress, clientIpAddress, fromClient))
                .commitNow();
    }

    public void setClientIpAddress(String ipAddress) {
        clientIpAddress = ipAddress;
    }

    public void sendMessage(String message) {
        SendMessageRunnable sendMessageRunnable = new SendMessageRunnable(clientIpAddress, message);
        Thread thread = new Thread(sendMessageRunnable);
        thread.start();;
    }

    class SendMessageRunnable implements Runnable {

        private String address;
        private String message;

        public  SendMessageRunnable(String address, String message) {
            this.address = address;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(address, 8080);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(message);
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MySocketServer implements Runnable {

        private final Context context;
        ServerSocket serverSocket;
        Socket socket;
        DataInputStream dataInputStream;
        String message;
        Handler handler = new Handler();

        public MySocketServer(Context context) {
            this.context = context;
        }

        @Override
        public void run() {

            try {
                serverSocket = new ServerSocket(8080);

                while (true) {
                    socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    message = dataInputStream.readUTF();

                    if (message.equals("Connect")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                clientIpAddress = socket.getInetAddress().getHostAddress();
                                callBeginGameFragment(true);
                            }
                        });
                    }
                    else if(message.equals("Begin")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callStopFormFragment(true);
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}