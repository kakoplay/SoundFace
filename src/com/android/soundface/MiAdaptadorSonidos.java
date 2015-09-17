package com.android.soundface;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.soundface.bean.Sonido;

public class MiAdaptadorSonidos extends BaseAdapter {
	
	Context context;  
    LayoutInflater mInflater;  
    ViewHolder holder;  
    List<Sonido> sonidos;  
    Sonido sonido;  
  
    
	public MiAdaptadorSonidos(Context context) {  
        this.context = context;  
        this.mInflater = LayoutInflater.from(context);  
    }  
  
    public void setList(List<Sonido> Book) {  
        this.sonidos = Book;  
    }  
  
    public void select(int position) {  
        if (!sonidos.get(position).isSelected()) {  
            sonidos.get(position).setSelected(true);  
            for (int i = 0; i < sonidos.size(); i++) {  
                if (i != position) {  
                    sonidos.get(i).setSelected(false);  
                }  
            }  
        }  
        notifyDataSetChanged();  
    }  
  
    @Override  
    public int getCount() {  
        return sonidos.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return sonidos.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = mInflater.inflate(R.layout.lista_sonidos, null);  
            holder.radioBtn = (RadioButton) convertView.findViewById(R.id.radioButton);  
            holder.radioBtn.setClickable(false);  
            holder.textView = (TextView) convertView.findViewById(R.id.texto);
            //holder.imageView = (ImageView) convertView.findViewById(R.id.imagenPlay); 
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        }  
        sonido = (Sonido) getItem(position);  
        holder.radioBtn.setChecked(sonido.isSelected());  
        holder.textView.setText(sonido.getDescSonido());
        return convertView;  
    }  
  
    class ViewHolder {  
        RadioButton radioBtn;  
        TextView textView;
        ImageView imageView;  
    }  
  
    


//    private final Activity actividad;
//    private final List<Sonido> lista;
//
//
//
//    public MiAdaptadorSonidos(Activity actividad, List<Sonido> lista) {
//          super();
//          this.actividad = actividad;
//          this.lista = lista;
//
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//          LayoutInflater inflater = actividad.getLayoutInflater();
//
//          View view = inflater.inflate(R.layout.lista_sonidos, null, true); 
//
//          TextView textView =(TextView)view.findViewById(R.id.texto);
//          textView.setText(lista.get(position).getNombreSonido());
//          
//          ImageView img = (ImageView)view.findViewById(R.id.imagenPlay);
//          //img.setImageDrawable(context.getResources().getDrawable(c.getImagen()));
// 
//
//          return view;
//
//    }
//
//    public int getCount() {
//          return lista.size();
//    }
//
//    public Object getItem(int arg0) {
//          return lista.get(arg0);
//    }
//
//
//    public long getItemId(int position) {
//          return position;
//    }

}