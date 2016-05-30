package com.nearbysocialevents.nearby975;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyListAdapter extends ArrayAdapter<Evento> {

    private Context context;
    private ArrayList<Evento> allEvents;

    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public MyListAdapter(Context context, ArrayList<Evento> mPersons) {
        super(context, R.layout.layout_evento_agenda);
        this.context = context;
        this.allEvents = new ArrayList<Evento>(mPersons);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allEvents.size();
    }

    @Override
    public Evento getItem(int position) {
        return allEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
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
                    convertView = mInflater.inflate(R.layout.layout_evento_agenda,parent, false);
                    holder.name = (TextView) convertView.findViewById(R.id.text_nome_evento_agenda);
                    holder.data = (TextView) convertView.findViewById(R.id.text_date_evento_agenda);
                    holder.preco = (TextView) convertView.findViewById(R.id.text_preco_evento_agenda);
                    holder.distancia = (TextView) convertView.findViewById(R.id.text_distancia_evento_agenda);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(allEvents.get(position).nome);
        holder.data.setText(df.format(allEvents.get(position).data));
        holder.preco.setText(Float.toString(allEvents.get(position).preco));
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

        TextView name;
        TextView data;
        TextView preco;
        TextView distancia;
        int pos; //to store the position of the item within the list
    }
}