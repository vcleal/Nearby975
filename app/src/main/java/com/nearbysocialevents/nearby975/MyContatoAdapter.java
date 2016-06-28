package com.nearbysocialevents.nearby975;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by root on 6/28/16.
 */
public class MyContatoAdapter  extends ArrayAdapter<Usuario> {

    private Context context;
    private ArrayList<Usuario> allEvents;

    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public MyContatoAdapter(Context context, ArrayList<Usuario> mPersons) {
        super(context, R.layout.layout_contato_list_item);
        this.context = context;
        this.allEvents = new ArrayList<Usuario>(mPersons);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allEvents.size();
    }

    @Override
    public Usuario getItem(int position) {
        return allEvents.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    public int getPosition(Evento item) {
        return allEvents.indexOf(item);
    }



    @Override
    public int getViewTypeCount() {
        return 1; //Number of types + 1 !!!!!!!!
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        int type = getItemViewType(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case 1:
                    convertView = mInflater.inflate(R.layout.layout_contato_list_item,parent, false);
                    holder.foto = (ImageView) convertView.findViewById(R.id.imageContato);
                    holder.nome = (TextView) convertView.findViewById(R.id.text_nome_contato);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.foto.setImageBitmap(allEvents.get(position).foto);
        holder.nome.setText(allEvents.get(position).nome);

        holder.pos = position;
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }


    //---------------static views for each row-----------//
    static class ViewHolder {


        ImageView foto;
        TextView nome;
        int pos; //to store the position of the item within the list
    }




}
