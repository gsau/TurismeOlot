package com.example.marc.turismeolot;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marc.turismeolot.Model.Lloc;

/**
 * Created by Marc on 27/03/2015.
 */
public class AdapterLloc extends ArrayAdapter<Lloc> {
    private Lloc[] dades;

    public AdapterLloc(Activity context, Lloc[] dades) {
        super(context, R.layout.listitem, dades);
        this.dades = dades;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View element = convertView;
        Vista vista;

        if(element == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            element = inflater.inflate(R.layout.listitem, null);

            vista = new Vista();
            vista.titol = (TextView)element.findViewById(R.id.txtTitol);
            vista.descripcio = (TextView)element.findViewById(R.id.txtDescripcio);
            vista.imatge=(ImageView)element.findViewById(R.id.imgViewLloc);

            ListView list=(ListView) element.findViewById(R.id.listViewLloc);
            element.setTag(vista);
        }
        else {
            vista = (Vista)element.getTag();
        }

        vista.titol.setText(dades[position].getTitol());
        vista.descripcio.setText(dades[position].getDescripcio());
        vista.imatge.setImageResource(dades[position].getImatge());

        return element;
    }

    private class Vista {
        public TextView titol;
        public TextView descripcio;
        public ImageView imatge;
    }
}