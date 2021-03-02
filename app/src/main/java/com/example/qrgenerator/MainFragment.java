package com.example.qrgenerator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrgenerator.adapter.RecyclerViewAdapter;
import com.example.qrgenerator.model.DataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<DataModel> dataModelArray = new ArrayList<>();

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){

        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        populateData();
        initRecyclerView(view);
        RecyclerViewAdapter dataAdapter = new RecyclerViewAdapter(getContext(), dataModelArray, this::onItemClick);
        recyclerView.setAdapter(dataAdapter);
        //Set up FAB
        fab.setOnClickListener(v -> showAddDataDialog(getContext()));
        return view;
    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        fab = view.findViewById(R.id.fab);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showAddDataDialog(Context context) {
        //Design Layout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText nameEditText = new EditText(context);
        nameEditText.setHint("Name");
        layout.addView(nameEditText);
        final EditText addressEditText = new EditText(context);
        addressEditText.setHint("Address");
        layout.addView(addressEditText);
        layout.setGravity(View.TEXT_ALIGNMENT_CENTER);
        layout.setPadding(20, 0, 20, 20);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Add New Data")
                .setMessage("Kindly input your name and address")
                .setView(layout)
                .setPositiveButton("Save", (dialog1, which) -> {
                    String name = String.valueOf(nameEditText.getText());
                    String address = String.valueOf(addressEditText.getText());
                    if (name.equals("") || address.equals("")){
                        errorMessage();
                    }
                    else{
                        DataModel dataModel = new DataModel(name, address);
                        dataModelArray.add(dataModel);
                        Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }

    private void errorMessage() {
        Toast.makeText(getContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
    }

    private void populateData(){
        dataModelArray.add(new DataModel("Solomon Blue", "Lagos, Island"));
        dataModelArray.add(new DataModel("James Real", "Lade, Bayway"));
        dataModelArray.add(new DataModel("Susan Peach", "Ibadan, Uplans"));
        dataModelArray.add(new DataModel("clark Ballon", "Lagos, Tiret"));
        dataModelArray.add(new DataModel("Superman", "los angeles, Island"));
        dataModelArray.add(new DataModel("Daniel", "Lagos, Abuja"));
        dataModelArray.add(new DataModel("Dalph Teht", "Fiji, Island"));
        dataModelArray.add(new DataModel("Caret Bloor", "Red, Caymen"));
        dataModelArray.add(new DataModel("Seth", "Brazil, Island"));
        dataModelArray.add(new DataModel("Timi James", "Lagos, Island"));
    }

    @Override
    public void onItemClick(DataModel dataModel) {
        Fragment fragment = QrDislayFragment.newInstance(dataModel.getName(), dataModel.getAddress());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment"));
        transaction.add(R.id.host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}