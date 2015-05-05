package com.example.marc.turismeolot;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.marc.turismeolot.Model.Lloc;


public class LlocsInteres extends ActionBarActivity {
    ListView lvLloc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvLloc=  (ListView)findViewById(R.id.listViewLloc);
        demoLlocs();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void demoLlocs(){
        final Lloc[] dades = new Lloc[]{
                new Lloc("Sant Esteve", "És una església de planta de creu llatina, amb una sola nau de 58 metres de longitud per 37,4 d'amplada i 52 metres de creuer; l'alçada és de 34,5 metres. ",R.drawable.ic_esglsia_de_sant_esteve_dolot),
                new Lloc("Església del carme", "Van sers construïts a finals del segle XVI. Són dos pisos que projectà Llàtzer Cisterna i que s’han convertit en una de les peces arquitectòniques més valuoses de la ciutat d’Olot.",R.drawable.ic_carme_olot),
                new Lloc("Can Solà Morales", "Van sers construïts una fase final del Segle XVI. Són Dos pisos Que Projecta Llàtzer Cisterna i Que S'HAN ConvertIt de l'una de les peixos arquitectòniques més valuoses de la ciutat d'Olot.",R.drawable.ic_solamorales),
                new Lloc("Hospici", "La Sala d’Actes de l’Hospici està ubicada a la planta baixa de l’edifici del Hospici.",R.drawable.ic_hospici),
                new Lloc("Parc nou", "Esplèndida finca, actualment parc municipal, on es troba la Torre Castanys, edifici modernist, un museu i un jardí botànic",R.drawable.ic_parcnou),
                new Lloc("Volcans", "El Parc Natural de la Zona Volcànica de la Garrotxa és un parc natural que es troba a la comarca de la Garrotxa. És el millor exponent de paisatge volcànic de la península Ibèrica. ",R.drawable.ic_volca_olot),
                new Lloc("La fageda", "La Fageda d'en Jordà és una de les 26 reserves naturals que forma part del Parc Natural de la Zona Volcànica de la Garrotxa. ",R.drawable.ic_fageda),
        };
        AdapterLloc adapter = new AdapterLloc(this, dades);
        lvLloc.setAdapter(adapter);
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
}
