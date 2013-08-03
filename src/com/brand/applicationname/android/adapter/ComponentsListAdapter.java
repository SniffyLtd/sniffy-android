package com.brand.applicationname.android.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brand.applicationname.android.R;
import com.brand.applicationname.android.model.Component;

public class ComponentsListAdapter extends ArrayAdapter<Component>{
	
	private int viewResourceId;
	
	private LayoutInflater inflater;

	public ComponentsListAdapter(Context context, int viewResourceId,
			List<Component> objects) {
		super(context, 0, objects);
		this.viewResourceId = viewResourceId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	private class ViewHolder {
		View colorRectangle;
		TextView name;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(viewResourceId, null);
			holder = createViewHolder(convertView);
			convertView.setTag(holder);
		}
		else if(convertView.getTag() == null || !(convertView.getTag() instanceof ViewHolder)){
			holder = createViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		Component component = getItem(position);
		
		holder.name.setText(component.getName());
		holder.colorRectangle.setBackgroundColor(Color.parseColor(component.getRating().getColor()));
		
		return convertView;
	}

	private ViewHolder createViewHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.name = (TextView)convertView.findViewById(R.id.component_name_label);
		holder.colorRectangle = convertView.findViewById(R.id.component_rating_color);
		return holder;
	}
	
	
}

