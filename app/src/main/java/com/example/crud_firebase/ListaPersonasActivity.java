package com.example.crud_firebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Modelos.Persona;
import Modelos.PersonaAdapter;

public class ListaPersonasActivity extends AppCompatActivity {

    private ListView listViewPersonas;
    private ArrayList<Persona> personasList;
    private PersonaAdapter adapter;
    private DatabaseReference databaseReference;
    Button btnagregar, btnactualizar;
    String selectedId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas);

        listViewPersonas = findViewById(R.id.listViewPersonas);
        personasList = new ArrayList<>();
        adapter = new PersonaAdapter(this, personasList);
        listViewPersonas.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Personas");

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedId = personasList.get(position).getId();

            }
        });

        listViewPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = personasList.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaPersonasActivity.this);
                builder.setMessage("¿Estás seguro de que deseas eliminar esta persona?")
                        .setPositiveButton("Sí", (dialog, which) -> eliminarPersona(selectedId))
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        cargarPersonas();

        //botenos
        btnagregar = (Button) findViewById(R.id.btnagregar);
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaPersonasActivity.this, PersonaActivity.class);
                startActivity(intent);
            }
        });

        btnactualizar = (Button) findViewById(R.id.btnactualizar);
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedId == null){
                    Toast.makeText(ListaPersonasActivity.this,"Selecione un campo",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(ListaPersonasActivity.this, ActualizarPersonaActivity.class);
                    intent.putExtra("idPersona", selectedId);
                    startActivity(intent);
                }
            }
        });

        //fin de botones
    }

    private void cargarPersonas() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personasList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Persona persona = postSnapshot.getValue(Persona.class);
                    if (persona != null) {
                        personasList.add(persona);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListaPersonasActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarPersona(String id) {
        DatabaseReference personaRef = databaseReference.child(id);
        personaRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ListaPersonasActivity.this, "Persona eliminada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ListaPersonasActivity.this, "Error al eliminar persona", Toast.LENGTH_SHORT).show();
            }
        });
    }
}