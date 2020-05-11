package com.example.realm1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realm1.model.Restaurante;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRest extends Fragment {

    private Realm mRealm;
    static int upORadd;
    static String id;
    private EditText nameEditText, describcionEditText;
    private Button addToBBDDButton,upDateToBBDDButton;

    public AddRest() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_rest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        nameEditText = view.findViewById(R.id.accer_name);
        describcionEditText = view.findViewById(R.id.accer_des);

        addToBBDDButton = view.findViewById(R.id.addToBBDD);
        upDateToBBDDButton = view.findViewById(R.id.upDateToBBDD);

        if (upORadd == 1){
            addToBBDDButton.setVisibility(View.VISIBLE);
        }else {
            upDateToBBDDButton.setVisibility(View.VISIBLE);
        }

        addToBBDDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBBDD();
                InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(requireContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Navigation.findNavController(view).navigate(R.id.home);
            }
        });

        upDateToBBDDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDateBBDD();
                InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(requireContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                Navigation.findNavController(view).navigate(R.id.home);
            }
        });
    }

    private void upDateBBDD() {

        Restaurante restaurante = mRealm.where(Restaurante.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if (nameEditText.getText() != null) {
            restaurante.setNombre(String.valueOf(nameEditText.getText()));
        }
        if (describcionEditText.getText() != null) {
            restaurante.setDescripcion(String.valueOf(describcionEditText.getText()));
        }
        mRealm.commitTransaction();

    }

    private void addBBDD() {

        mRealm.beginTransaction();
        Restaurante restaurante = mRealm.createObject(Restaurante.class, UUID.randomUUID().toString());
        restaurante.setNombre(String.valueOf(nameEditText.getText()));
        restaurante.setDescripcion(String.valueOf(describcionEditText.getText()));
        mRealm.commitTransaction();

    }

}
