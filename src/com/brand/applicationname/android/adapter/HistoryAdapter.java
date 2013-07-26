package com.brand.applicationname.android.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.brand.applicationname.android.R;
import com.brand.applicationname.android.model.Scanning;
import com.brand.applicationname.android.utils.ExpandAnimation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryAdapter extends ArrayAdapter<Scanning> {

	private static final long ANIMATION_DURATION = 250;

	private int viewResourceId;
	
	private Context context;

	private LayoutInflater inflater;
	
	private int selected;

	private OnAwsomContexMenuListener onAwsomeContextMenuListener;
	
	private OnOpenClickedListner onOpenClickedListner = new OnOpenClickedListner();
	
	private OnShareClickedListner onShareClickedListner = new OnShareClickedListner();
	
	private OnRefreshClickedListner onRefreshClickedListner = new OnRefreshClickedListner();
	
	private OnRemoveClickedListner onRemoveClickedListner = new OnRemoveClickedListner();
	
	public HistoryAdapter(Context context, int viewResourceId,
			List<Scanning> objects) {
		super(context, 0, objects);
		this.context = context;
		this.viewResourceId = viewResourceId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setSelected(int selected){
		this.selected = selected;
		notifyDataSetInvalidated();
	}

    class ViewHolder {
		public ImageView thumbnail;
		public TextView date;
		public TextView barecode;
		public TextView productName;
		public Button openButton;
		public Button refreshButton;
		public Button removeButton;
		public Button shareButton;
		public View contexMenuFrame;
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
		Scanning scanning = getItem(position);
		
		holder.barecode.setText(scanning.getBarecode());
		holder.date.setText(calculateDate(scanning.getDate()));
		if(scanning.getFoundProduct() != null && scanning.getFoundProduct().getName() != null && !scanning.getFoundProduct().getName().isEmpty()){
			holder.productName.setVisibility(View.VISIBLE);
			holder.productName.setText(scanning.getFoundProduct().getName());
		}
		else{
			holder.productName.setVisibility(View.GONE);
		}
		if(scanning.getId() == selected){
			setListeners(holder);
			showContextMenu(holder);
		}
		else{
			hideContextMenu(holder);
			clearListeners(holder);
		}
		return convertView;
	}
	
	private void hideContextMenu(ViewHolder holder) {
		holder.contexMenuFrame.setVisibility(View.GONE);
		
	}

	private void showContextMenu(ViewHolder holder) {
		holder.contexMenuFrame.getLayoutParams().height= 0;
		holder.contexMenuFrame.setVisibility(View.VISIBLE);
		
		ExpandAnimation animation = new ExpandAnimation(holder.contexMenuFrame, 50, true);
		animation.setDuration(ANIMATION_DURATION);
		holder.contexMenuFrame.startAnimation(animation);
	}

	private void clearListeners(ViewHolder holder) {
		holder.openButton.setOnClickListener(null);
		holder.shareButton.setOnClickListener(null);
		holder.refreshButton.setOnClickListener(null);
		holder.removeButton.setOnClickListener(null);
	}

	private void setListeners(ViewHolder holder) {
		
		holder.openButton.setOnClickListener(onOpenClickedListner);
		holder.shareButton.setOnClickListener(onShareClickedListner);
		holder.refreshButton.setOnClickListener(onRefreshClickedListner);
		holder.removeButton.setOnClickListener(onRemoveClickedListner);
	}

	private CharSequence calculateDate(Date date) {
		Date rightNow = new Date();
		if(rightNow.getDay() == date.getDay()){
			String today = context.getString(R.string.today);
			SimpleDateFormat format = new SimpleDateFormat(" HH:mm ");
			return today + format.format(date);
		}
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm ");
		return format.format(date);
	}

	private ViewHolder createViewHolder(View convertView) {
		ViewHolder holder=  new ViewHolder();
		holder.barecode = (TextView)convertView.findViewById(R.id.barecode_view);
		holder.productName = (TextView)convertView.findViewById(R.id.product_name_view);
		holder.date = (TextView)convertView.findViewById(R.id.date_view);
		holder.openButton = (Button)convertView.findViewById(R.id.open_item_button);
		holder.shareButton = (Button)convertView.findViewById(R.id.share_item_button);
		holder.removeButton = (Button)convertView.findViewById(R.id.delete_item_button);
		holder.refreshButton = (Button)convertView.findViewById(R.id.refresh_item_button);
		holder.contexMenuFrame = convertView.findViewById(R.id.context_menu_frame);
		return holder;
	}
	
	public void setOnAwsomContexMenuListener(OnAwsomContexMenuListener listener){
		this.onAwsomeContextMenuListener = listener;
	}
	
	public interface OnAwsomContexMenuListener{
		
		void onRemove(int id);

		void onRefresh(int id);

		void onOpen(int id);

		void onShare(int id);
	}
	
	private class OnRemoveClickedListner implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(onAwsomeContextMenuListener != null){
				onAwsomeContextMenuListener.onRemove(selected);
			}
		}
	}
	private class OnOpenClickedListner implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(onAwsomeContextMenuListener != null){
				onAwsomeContextMenuListener.onOpen(selected);
			}
		}
	}
	private class OnShareClickedListner implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(onAwsomeContextMenuListener != null){
				onAwsomeContextMenuListener.onShare(selected);
			}
		}
	}
	private class OnRefreshClickedListner implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(onAwsomeContextMenuListener != null){
				onAwsomeContextMenuListener.onRefresh(selected);
			}
		}
	}


	public int getSelected() {
		return this.selected;
	}
}
