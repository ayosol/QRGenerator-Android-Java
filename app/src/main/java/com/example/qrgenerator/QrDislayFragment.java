package com.example.qrgenerator;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrDislayFragment extends Fragment {
    ImageView img_barcode;
    TextView txt_display_name, txt_display_address;

    private static final String ARG_NAME = "name";
    private static final String ARG_ADDRESS = "address";
    private String mName;
    private String mAddress;

    public QrDislayFragment(){

    }

    public static QrDislayFragment newInstance(String name, String address){
        QrDislayFragment fragment = new QrDislayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mName = getArguments().getString(ARG_NAME);
            mAddress = getArguments().getString(ARG_ADDRESS);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_display, container, false);
        txt_display_name = view.findViewById(R.id.txt_display_name);
        txt_display_address = view.findViewById(R.id.txt_display_address);
        img_barcode = view.findViewById(R.id.img_barcode_display);
        txt_display_name.setText(mName);
        txt_display_address.setText(mAddress);
        //BARCODE GENERATOR
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(mName.concat(mAddress), BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img_barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //
            }
        });*/
    }
}