package com.brand.applicationname.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brand.applicationname.android.R;
import com.brand.applicationname.android.command.Command;

public class ProductMenuAdapter extends ArrayAdapter<Command>{

	//private Context context;
	
	private int viewResourceId;
	
	private LayoutInflater inflater;

	public ProductMenuAdapter(Context context, int viewResourceId,
			List<Command> objects) {
		super(context, 0, objects);
		//this.context = context;
		this.viewResourceId = viewResourceId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	private class ViewHolder {
		ImageView icon;
		TextView header;
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
		Command command = getItem(position);
		
		holder.header.setText(command.getHeader());
		holder.icon.setImageDrawable(command.getIcon());
		
		return convertView;
	}

	private ViewHolder createViewHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.header = (TextView)convertView.findViewById(R.id.menu_item_header);
		holder.icon = (ImageView)convertView.findViewById(R.id.menu_item_icon);
		return holder;
	}
	
	
}
