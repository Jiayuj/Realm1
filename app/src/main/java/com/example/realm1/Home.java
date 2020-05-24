package com.example.realm1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.realm1.model.Migration;

import com.example.realm1.model.Restaurante;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private ElementosAdapter elementosAdapter;
    private Realm mRealm;
    private String id;
    private List<Restaurante> r;
    private EditText nameEditText;

    /**
     * Instantiates a new Home.
     */
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRealm = Realm.getDefaultInstance();

        showStatus("Restaurante");
        showStatus(mRealm);

        view.findViewById(R.id.addNewRestaurante).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRest.upORadd=1;
                Navigation.findNavController(view).navigate(R.id.addRest);
            }
        });

        view.findViewById(R.id.removeRestaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eleminarRest();
            }
        });

        view.findViewById(R.id.cercar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEditText = view.findViewById(R.id.search_name);
                cercarRest(view);
            }
        });

        view.findViewById(R.id.modificar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRest.upORadd=0;
                AddRest.id = id;
                Navigation.findNavController(view).navigate(R.id.addRest);
            }
        });

        RecyclerView elementosRecyclerView = view.findViewById(R.id.item_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        elementosRecyclerView.setLayoutManager(layoutManager);
        elementosRecyclerView.addItemDecoration(new DividerItemDecoration(elementosRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        r = queryAllRest();
        elementosAdapter = new ElementosAdapter(r);
        elementosRecyclerView.setAdapter(elementosAdapter);


    }
    //cercar resultat
    private void cercarRest(View view) {
        RealmResults<Restaurante> realmResults = mRealm.where(Restaurante.class)
                .equalTo("nombre", String.valueOf(nameEditText.getText()))
                .findAll();
        //si no buscar nada mostra todas
        if (realmResults.size() == 0 && nameEditText.length() == 0){
            r = queryAllRest();
            elementosAdapter.establecerListaElementos(r);
        } else if (realmResults.size() == 0 && nameEditText.length() != 0) { //si no hay resulta, un mensaje.
            Snackbar.make(view.findViewById(R.id.homefff), "no hay resultat",
                    Snackbar.LENGTH_SHORT)
                    .show();
            r = queryAllRest();
            elementosAdapter.establecerListaElementos(r);
        }else {
            //retorna result
            elementosAdapter.establecerListaElementos(realmResults);
        }
    }


    private void eleminarRest() {
        final RealmResults<Restaurante> realmResults = mRealm.where(Restaurante.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < realmResults.size(); i++) {
                    if (realmResults.get(i).getId().equals(id)){
                        realmResults.get(i).deleteFromRealm();
                    }
                }
            }
        });
        r = queryAllRest();
        elementosAdapter.establecerListaElementos(r);
    }

    private String realmString(Realm realm) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Restaurante restaurante : realm.where(Restaurante.class).findAll()) {
            stringBuilder.append(restaurante.toString()).append("\n");
        }

        return (stringBuilder.length() == 0) ? "<data was deleted>" : stringBuilder.toString();
    }

    private void showStatus(Realm realm) {
        showStatus(realmString(realm));
    }

    private void showStatus(String txt) {
        Log.i("Restaurante", txt);
    }

    private List<Restaurante> queryAllRest() {
        RealmResults<Restaurante> restaurantes = mRealm.where(Restaurante.class).findAll();
        return mRealm.copyFromRealm(restaurantes);
    }

    /**
     * The type Elementos adapter.
     */
    class ElementosAdapter extends RecyclerView.Adapter<ElementosAdapter.ElementoViewHolder>{

        /**
         * The Restaurantes.
         */
        List<Restaurante> restaurantes;
        /**
         * The Selected position.
         */
        int selectedPosition = -5;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_detall_rest, parent, false));
        }

        /**
         * Instantiates a new Elementos adapter.
         *
         * @param restauranteList the restaurante list
         */
        ElementosAdapter(List<Restaurante> restauranteList){
            this.restaurantes = restauranteList;
        }

        @Override
        public void onBindViewHolder(@NonNull final ElementoViewHolder holder, final int position) {

            final Restaurante rest = restaurantes.get(position);

            holder.nombreTextView.setText(rest.getNombre());
            holder.descripcionTextView.setText(rest.getDescripcion());

            holder.itemView.setSelected(selectedPosition == position);
            if (selectedPosition == position) {
                holder.ivSelected.setVisibility(View.VISIBLE);
            } else {
                holder.ivSelected.setVisibility(View.INVISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(selectedPosition);
                    selectedPosition = position;
                    id=rest.getId();
                    notifyItemChanged(selectedPosition);
                }
            });
        }

        @Override
        public int getItemCount() {
            return restaurantes == null ? 0 : restaurantes.size();
        }

        /**
         * Establecer lista elementos.
         *
         * @param elementos the elementos
         */
        void establecerListaElementos(List<Restaurante> elementos){
            this.restaurantes = elementos;
            notifyDataSetChanged();
        }

        /**
         * The type Elemento view holder.
         */
        class ElementoViewHolder extends RecyclerView.ViewHolder {
            /**
             * The Nombre text view.
             */
            TextView nombreTextView, /**
             * The Descripcion text view.
             */
            descripcionTextView;
            /**
             * The Iv selected.
             */
            ImageView ivSelected;

            /**
             * Instantiates a new Elemento view holder.
             *
             * @param itemView the item view
             */
            ElementoViewHolder(@NonNull View itemView) {
                super(itemView);
                nombreTextView = itemView.findViewById(R.id.textview_nombre);
                descripcionTextView = itemView.findViewById(R.id.textview_descripcion);
                ivSelected = itemView.findViewById(R.id.iv_selected);
            }
        }
    }
}
