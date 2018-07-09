package if1001.watchtime.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import if1001.watchtime.Entities.Annotations;
import if1001.watchtime.R;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Annotations> myannotations;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);
        this.context= context;
        myannotations=objects;

    }

    private class ViewHolder
    {
        TextView note_title;
        TextView note_deadline;
        TextView note_datetime;
        TextView note_priority;
        TextView note_category;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.annotation_row, null);

            holder = new ViewHolder();
            holder.note_title = (TextView) convertView.findViewById(R.id.note_title);
            holder.note_datetime = (TextView) convertView.findViewById(R.id.note_datetime);
            holder.note_deadline = (TextView) convertView.findViewById(R.id.note_deadline);
            holder.note_priority = (TextView) convertView.findViewById(R.id.note_priority);
            holder.note_category = (TextView) convertView.findViewById(R.id.note_category);

            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Annotations note_received= myannotations.get(position);
        holder.note_title.setText("Título: " +  note_received.getTitle() + "");
        holder.note_datetime.setText("Data de Criação: "+ note_received.getDatetime()+"");
        holder.note_priority.setText("Prioridade: "+ note_received.getPriority()+"");
        holder.note_deadline.setText("Deadline: "+ note_received.getDeadline()+"");
        holder.note_category.setText("Categoria: "+ note_received.getCategory()+"");

        return convertView;


    }

}
