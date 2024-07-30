package Modelos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crud_firebase.R;

import java.util.List;

public class PersonaAdapter extends ArrayAdapter<Persona> {

    public PersonaAdapter(@NonNull Context context, @NonNull List<Persona> personas) {
        super(context, 0, personas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_persona, parent, false);
        }

        Persona persona = getItem(position);

        TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
        TextView textViewApellido = convertView.findViewById(R.id.textViewApellido);

        textViewNombre.setText(persona.getNombres()+" "+persona.getApellidos());
        textViewApellido.setText(persona.getFechanac());

        return convertView;
    }
}
