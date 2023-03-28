package SQlite.elvis.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button Registrar;
    Button Consultar;

    private EditText etcedula, etnombre, etelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Registrar = findViewById(R.id.registrar);
        Consultar = findViewById(R.id.consultar);

        etcedula=(EditText) findViewById(R.id.etcedula);
        etnombre=(EditText) findViewById(R.id.etnombre);
        etelefono=(EditText) findViewById(R.id.etelefono);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registrar(v);
            }
        });

        Consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar(v);
            }
        });
    }

    public void registrar(View view)
    {
        AdminBD admin = new AdminBD(this,"BaseDatos", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String cedula = etcedula.getText().toString();
        String nombre = etnombre.getText().toString();
        String telefono = etelefono.getText().toString();
        if(!cedula.isEmpty()  && !nombre.isEmpty()  && !telefono.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("cedula", cedula);
            registro.put("nombre", nombre);
            registro.put("telefono", telefono);
            BaseDatos.insert("usario", null, registro);
            BaseDatos.close();
            etcedula.setText("");
            etnombre.setText("");
            etelefono.setText("");
            Toast.makeText(this, "Registro Alamacenado con Exito", Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(this, "Ingrese correctamente todos los datos", Toast.LENGTH_LONG).show();
        }

    }

    public void consultar(View view)
    {
        AdminBD admin = new AdminBD(this, "BaseDatos", null, 1);
        SQLiteDatabase  BasedeDatos = admin.getWritableDatabase();
        String cedula1 = etcedula.getText().toString();
        if (!cedula1.isEmpty())
        {
            Cursor fila = BasedeDatos.rawQuery("select nombre, telefono from usario where cedula="+cedula1,null);
            if (fila.moveToFirst())
            {
                etnombre.setText(fila.getString(0));
                etelefono.setText(fila.getString(1));
                BasedeDatos.close();
            }
            else
            {
                Toast.makeText(this, "No se encontro el usuario", Toast.LENGTH_LONG).show();
            }
        }
    }
}