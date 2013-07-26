package com.brand.applicationname.android.command;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class ProductCommandFactory {

	private Context context;

	public ProductCommandFactory(Context context){
		this.context = context;
	}
	
	public List<Command> createComands(){
		List<Command> commands = new ArrayList<Command>();
		commands.add(new CommentsCommand(context));
		commands.add(new ReportCommand(context));
		commands.add(new ShowSimilarsCommand(context));
		return commands;
	}
}
