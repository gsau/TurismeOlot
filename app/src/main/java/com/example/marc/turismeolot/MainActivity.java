package com.example.marc.turismeolot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final int CODE_CAMERA = 1;

    ImageButton btnImatge, btnEntrar;
    EditText txtNom,txtCognom;
    Boolean foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        foto = false;

        txtNom = (EditText) findViewById(R.id.txtNom);
        txtCognom = (EditText)findViewById(R.id.txtCognom);

        btnImatge = (ImageButton) findViewById(R.id.btnImatge);
        btnImatge.setOnClickListener(this);

        btnEntrar = (ImageButton)findViewById(R.id.imgEntrar);
        btnEntrar.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnImatge:
                obrirCamera();
                break;
            case R.id.imgEntrar:
                login();
                break;
        }
    }

    private void login() {
        if(!txtNom.getText().toString().equals("")&& !txtCognom.getText().toString().equals("")&& foto){
            Intent menuPrincipal = new Intent(this, MenuPrincipal.class);
            startActivity(menuPrincipal);
        }
        else{
            Toast toast = Toast.makeText(getBaseContext(), "S'han d'emplenar tots els camps", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void obrirCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CODE_CAMERA);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_CAMERA) {
            if (resultCode == RESULT_OK ) {
                foto = true;
            }
        }
    }
}
