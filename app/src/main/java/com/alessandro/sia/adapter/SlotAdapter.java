package com.alessandro.sia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alessandro.sia.R;
import com.alessandro.sia.model.Slot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SlotAdapter extends ArrayAdapter<Slot>{

    private ArrayList<Slot> slotArrayList;
    private Context context;

    public SlotAdapter(ArrayList<Slot> slotArrayList, Context context){
        super(context, R.layout.adapter_slot, slotArrayList);
        this.slotArrayList = slotArrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_slot, parent, false);
        Slot slot = slotArrayList.get(position);

        if (slot.getReservation()!=null)
        slot.setStatus(slot.getReservation().getStatus());
        else{
            slot.setStatus("Libero");
        }
        TextView name       = (TextView)view.findViewById(R.id.adp_slot_name);
        TextView startAt    = (TextView)view.findViewById(R.id.adp_slot_hourStart);
        TextView endAt      = (TextView)view.findViewById(R.id.adp_slot_hourEnd);
        TextView status     = (TextView)view.findViewById(R.id.adp_slot_tv_status);

        status.setText(slot.getStatus());
        name.setText("Slot "+String.valueOf(position+1)+": ");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        try {
            cal.setTime(sdf.parse(slot.getDataStart()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startAt.setText(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));

        Calendar calEnd = Calendar.getInstance();
        SimpleDateFormat sdfEnd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALIAN);
        try {
            calEnd.setTime(sdfEnd.parse(slot.getDataEnd()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endAt.setText(calEnd.get(Calendar.HOUR_OF_DAY)+":"+calEnd.get(Calendar.MINUTE));

        RelativeLayout bottomRelativeLayout = (RelativeLayout)view.findViewById(R.id.adp_slot_bottomlayout);
        if (slot.getStatus().equals("CONFERMATO"))
            bottomRelativeLayout.setBackgroundColor(view.getResources().getColor(R.color.red));
        else if (slot.getStatus().equals("Non Confermato"))
            bottomRelativeLayout.setBackgroundColor(view.getResources().getColor(R.color.waiting));
        else
            bottomRelativeLayout.setBackgroundColor(view.getResources().getColor(R.color.green));
        return view;
    }
}
