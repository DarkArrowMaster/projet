package com.example.maste.projet;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnexion {

    private BluetoothDevice device = null;
    private BluetoothSocket socket = null;
    private InputStream receiveStream = null;
    private OutputStream sendStream = null;

    private ReceiverThread receiverThread;
    //     BluetoothAdapter Badapter;
//    Set<BluetoothDevice> BlueArray;
    Handler handler;

    /*
     * constructeur modifi� pour prendre le nom du device en param�tre
     */
    public BluetoothConnexion(Handler hstatus, Handler h, String name) {
        Set<BluetoothDevice> setpairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        BluetoothDevice[] pairedDevices = (BluetoothDevice[]) setpairedDevices.toArray(new BluetoothDevice[setpairedDevices.size()]);
        Log.e("great ",String.valueOf(pairedDevices.length));
        Log.e("great2 ",String.valueOf(setpairedDevices.size()));



        for(int i=0;i<pairedDevices.length;i++) {
            Log.e("Devides", "what the fuck");

            if(pairedDevices[i].getName().contains(name)) {
                Log.e("", "ffffffffffffffffff");
                device = pairedDevices[i];
                try {
                    //socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    receiveStream = socket.getInputStream();
                    sendStream = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        handler = hstatus;

        receiverThread = new ReceiverThread(h);
    }


//    private void gestPairedDevices(){
//
//    BlueArray=Badapter.getBondedDevices();
//        if (BlueArray.size()>0)
//            for (BluetoothDevice device:BlueArray){
//
//
//            }
//
//    }
	/*public void sendData(String data) {
		sendData(data, false);
	}

	public void sendData(String data, boolean deleteScheduledData) {
		try {
			sendStream.write(data.getBytes());
	        sendStream.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}*/

    public void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if(socket !=null)
                        socket.connect();

                    Message msg = handler.obtainMessage();
                    msg.arg1 = 1;
                    handler.sendMessage(msg);

                    receiverThread.start();
                    Log.i("ReadyToReceive", "RunningThred " );


                } catch (IOException e) {
                    Log.v("N", "Connection Failed : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void sendMessage(String Msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message",Msg);

        boolean b = LocalBroadcastManager.getInstance(MyApp.getContext()).sendBroadcast(intent);
    }

    public static int byteToUnsignedInt(byte b) {
        return 0x00 << 24 | b & 0xff;
    }
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    private class ReceiverThread extends Thread {
        Handler handler;

        ReceiverThread(Handler h) {
            handler = h;
        }

        @Override
        public void run() {
            Log.i("", "BEGIN mConnectedThread");
            byte[] buffer = new byte[1];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream

                    Log.i("we are receiving data" , "coooollll");
                    if(receiveStream != null){
                        bytes = receiveStream.read(buffer);
                        //   Log.i("we read " , String.valueOf((int)buffer[0]));
                        // String text = new String(buffer, 0, buffer.length, "ASCII");
                        int tranfert=  byteToUnsignedInt(buffer[0]);
                        sendMessage(String.valueOf(tranfert));

                        Log.d("", "message bytes " + bytes);
                        Log.d("", "message string bytes " + String.valueOf(bytes));
                        Log.d("", "message buffer " + new String(buffer));
                        //String geta=new String(buffer);
                        Message msg=handler.obtainMessage();
                        Bundle DataBundle =new Bundle();
                        DataBundle.putString("Distance",String.valueOf((int)buffer[0]));
                        msg.setData(DataBundle);
                        handler.sendMessage(msg);
                    }

                } catch (IOException e) {
                    Log.e("", "disconnected", e);
                    // Start the service over to restart listening mode;
                    break;
                }
            }
        }
    }

}
class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
