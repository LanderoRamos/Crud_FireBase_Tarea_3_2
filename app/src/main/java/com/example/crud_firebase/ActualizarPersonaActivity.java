package com.example.crud_firebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modelos.Persona;

public class ActualizarPersonaActivity extends AppCompatActivity {

    private EditText editTextNombres, editTextApellidos, editTextCorreo, editTextFechanac, editTextFoto;
    private Button buttonActualizar;
    private DatabaseReference databaseReference;
    private String idPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_persona);

        editTextNombres = findViewById(R.id.editTextNombres);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextFechanac = findViewById(R.id.editTextFechanac);
        editTextFoto = findViewById(R.id.editTextFoto);
        buttonActualizar = findViewById(R.id.buttonActualizar);

        databaseReference = FirebaseDatabase.getInstance().getReference("Personas");

        idPersona = getIntent().getStringExtra("idPersona");

        cargarPersona(idPersona);

        buttonActualizar.setOnClickListener(v -> actualizarPersona());
    }

    private void cargarPersona(String id) {
        // Aquí puedes agregar código para cargar los datos de Firebase
        databaseReference.child(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Persona persona = task.getResult().getValue(Persona.class);
                if (persona != null) {
                    editTextNombres.setText(persona.getNombres());
                    editTextApellidos.setText(persona.getApellidos());
                    editTextCorreo.setText(persona.getCorreo());
                    editTextFechanac.setText(persona.getFechanac());
                    editTextFoto.setText(persona.getFoto());
                }
            } else {
                Toast.makeText(ActualizarPersonaActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarPersona() {
        String nombres = editTextNombres.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();
        String fechanac = editTextFechanac.getText().toString().trim();
        String foto = editTextFoto.getText().toString().trim();

        if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || fechanac.isEmpty() || foto.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Persona persona = new Persona(idPersona, nombres, apellidos, correo, fechanac, foto);
        databaseReference.child(idPersona).setValue(persona).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ActualizarPersonaActivity.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ActualizarPersonaActivity.this, "Error al actualizar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}