package com.example.crud_firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modelos.Persona;

public class PersonaActivity extends AppCompatActivity {

    private EditText editTextNombres, editTextApellidos, editTextCorreo, editTextFechanac, editTextFoto;
    private Button buttonGuardar;
    private DatabaseReference databaseReference;
    private String idPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_persona);

        editTextNombres = findViewById(R.id.editTextNombres);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextFechanac = findViewById(R.id.editTextFechanac);
        editTextFoto = findViewById(R.id.editTextFoto);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        databaseReference = FirebaseDatabase.getInstance().getReference("Personas");

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPersona();
            }
        });

        // Obtener datos si es una edición
        idPersona = getIntent().getStringExtra("idPersona");
        if (idPersona != null) {
            //cargarPersona(idPersona);
        }

    }

    private void guardarPersona() {
        String nombres = editTextNombres.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();
        String fechanac = editTextFechanac.getText().toString().trim();
        String foto = editTextFoto.getText().toString().trim();

        if (idPersona == null) {
            idPersona = databaseReference.push().getKey();
        }

        Persona persona = new Persona(idPersona, nombres, apellidos, correo, fechanac, foto);
        databaseReference.child(idPersona).setValue(persona);

        finish();
    }

    private void cargarPersona(String id) {
        // Código para cargar los datos de Firebase y mostrarlos en los campos.
    }

}