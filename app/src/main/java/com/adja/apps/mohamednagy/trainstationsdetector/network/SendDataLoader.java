package com.adja.apps.mohamednagy.trainstationsdetector.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Mohamed Nagy on 2/6/2018.
 */

public class SendDataLoader extends AsyncTaskLoader<Void> {

    private final int mTimeInSecond;
    public static final int LOADER_ID = 0x1;

    SendDataLoader(Context context, long timeInMills) {
        super(context);
        mTimeInSecond = (int) (timeInMills/1000);
    }

    @Override
    public Void loadInBackground() {
        final String HOST = "localhost";
        final int PORT = 5005;

        Socket socket = new Socket();
        DataOutputStream dataOutputStream = null;
        try {
            socket.connect(new InetSocketAddress(HOST, PORT));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.write(mTimeInSecond);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            if(socket.isClosed())
                socket.close();
            if(dataOutputStream != null)
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
