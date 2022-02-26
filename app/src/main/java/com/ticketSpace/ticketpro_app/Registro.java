package com.ticketSpace.ticketpro_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Registro extends AppCompatActivity {

    TextView FechaRegistro;
    EditText Correo,Password,Nombres,Apellidos, Cedula;
    Button Registrar;

    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        FechaRegistro = findViewById(R.id.FechaRegistro);
        Correo = findViewById(R.id.Correo);
        Password = findViewById(R.id.Password);
        Nombres = findViewById(R.id.Nombres);
        Apellidos = findViewById(R.id.Apellidos);
        Cedula = findViewById(R.id.Cedula);

        Registrar = findViewById(R.id.Registrar);
        auth = FirebaseAuth.getInstance();

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy");
        String Sfecha = fecha.format(date);
        FechaRegistro.setText(Sfecha);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();
                String nombre = Nombres.getText().toString();
                String apellido = Apellidos.getText().toString();
                String cedula = Cedula.getText().toString();

                if (correo.equals("")||pass.equals("")||nombre.equals("")||apellido.equals("")||cedula.equals("")){
                    Toast.makeText(Registro.this,"POrfavor llene todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                        Correo.setError("Correo Invalido");
                        Correo.setFocusable(true);
                    }else if (pass.length()<6){
                        Password.setError("Contarseña debe ser mayor o igual a 6");
                        Password.setFocusable(true);
                    } else {
                        RegistroAdministradores(correo,pass);
                    }
                }
            }
        });
        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setMessage("Registrando, Espere porfavor");
        progressDialog.setCancelable(false);
    }

    private void RegistroAdministradores(String correo, String pass) {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(correo,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;

                            String UID = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Password.getText().toString();
                            String nombre = Nombres.getText().toString();
                            String apellido = Apellidos.getText().toString();
                            String cedula = Cedula.getText().toString();

                            HashMap<Object, Object> Administradores = new HashMap<>();
                            Administradores.put("UID",UID);
                            Administradores.put("CORREO",correo);
                            Administradores.put("PASSWORD",pass);
                            Administradores.put("NOMBRES",nombre);
                            Administradores.put("APELLIDOS",apellido);
                            Administradores.put("CEDULA",cedula);
                            Administradores.put("IMAGEN","");

                            //iniciamos la base
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("BASE DE DATOS ADMINISTRADORES");
                            reference.child(UID).setValue(Administradores);
                            startActivity(new Intent(Registro.this, MainActivityAdministrador.class));
                            Toast.makeText(Registro.this,"Registro Exitoso",Toast.LENGTH_SHORT).show();
                            Registro.this.finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(Registro.this,"HA COURRIDO UN PROBLEMA",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registro.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }

}