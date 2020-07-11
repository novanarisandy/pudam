package com.example.aplikasikelolaasetpudam.Service;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.aplikasikelolaasetpudam.Service.Constants;

public class GeocoderResultReceiver extends ResultReceiver {
    private String address;
    private DialogInterface dialog;

    public GeocoderResultReceiver(Handler handler) {
        super(handler);
    }

    /**
     *  Menerima balikan data dari GeocoderIntentService and menampilkan Toast di MainActivity.
     */

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        // Menampilkan alamat, atau error yang didapat dari proses reverse geocoding
        address = resultData.getString(Constants.RESULT_DATA_KEY);

        // Memunculkan toast message jika ada alamat ditemukan
        if (resultCode == Constants.SUCCESS_RESULT) {
//            Toast.makeText(PerbaruiMutasiActivity.this, "Alamat ditemukan \n" +
//                    address, Toast.LENGTH_LONG).show();
        }

        dialog.dismiss();
    }
}

