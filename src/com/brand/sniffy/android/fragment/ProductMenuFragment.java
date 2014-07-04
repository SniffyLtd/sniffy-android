package com.brand.sniffy.android.fragment;
import java.util.List;

import com.brand.sniffy.android.R;
import com.brand.sniffy.android.adapter.ProductMenuAdapter;
import com.brand.sniffy.android.command.Command;
import com.brand.sniffy.android.utils.ExpandHorizontalAnimation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ProductMenuFragment extends ListFragment  implements OnItemClickListener{

	private boolean menuOpened = false;
	
	private final Handler executor = new Handler();
	
	private ProductMenuAdapter menuAdapter;
	
	private View menuContainer;
	
	private View pageContainer;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
		hide();
		Runnable runable = new Runnable() {
			@Override
			public void run() {
				Command command = menuAdapter.getItem(position);
				command.execute(getActivity());
			}
		};
		executor.postDelayed(runable, 300);
	}
	
	public void exand() {
		ExpandHorizontalAnimation animation =  new ExpandHorizontalAnimation(menuContainer, pageContainer, 250, !menuOpened);
		animation.setDuration(200);
		menuContainer.startAnimation(animation);
		menuOpened= !menuOpened;
	}

	public void hide() {
		if(menuOpened){
			exand();	
		}
	}

	public void setMenuContainer(View menuContainer) {
		this.menuContainer = menuContainer;
	}

	public void setPageContainer(View pageContainer) {
		this.pageContainer = pageContainer;
	}
	
	public void setCommands(List<Command> commands){
		this.menuAdapter = new ProductMenuAdapter(getActivity(), R.layout.item_product_menu, commands );
		setListAdapter(menuAdapter);
	}
}
