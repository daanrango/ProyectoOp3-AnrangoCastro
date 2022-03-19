package com.ticketSpace.ticketpro_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ticketSpace.ticketpro_app.FragmentosAdministrador.InicioAdmin;
import com.ticketSpace.ticketpro_app.FragmentosAdministrador.ListaAdmin;
import com.ticketSpace.ticketpro_app.FragmentosAdministrador.PerfilAdmin;
import com.ticketSpace.ticketpro_app.FragmentosAdministrador.RegistrarAdmin;
import com.ticketSpace.ticketpro_app.FragmentosCliente.InicioCliente;

public class MainActivityAdministrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);

        Toolbar toolbar = findViewById(R.id.toobarA);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_A);

        NavigationView navigationView = findViewById(R.id.nav_viewA);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //fragmento por defauld
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,new InicioCliente()).commit();
            navigationView.setCheckedItem(R.id.InicioAdmin);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.InicioAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,new InicioCliente()).commit();
                break;

            case R.id.PerfilAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,new PerfilAdmin()).commit();
                break;

            case R.id.ListarAdmin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,new ListaAdmin()).commit();
                break;

            case R.id.Salir:
                CerrarSesion();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void comprobandoInicio(){
        if (user!=null){
            //Toast.makeText(this, "Se ha iniciado sesion", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(MainActivityAdministrador.this,InicioSesion.class));
            finish();
        }

    }

    @Override
    protected void onStart() {
        comprobandoInicio();
        super.onStart();
    }

    //CERRAR SESION
    private void CerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityAdministrador.this,InicioSesion.class));
        Toast.makeText(this, "Cerraste session exitosamente", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }
}