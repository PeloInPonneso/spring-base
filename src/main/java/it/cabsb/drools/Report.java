package it.cabsb.drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Report {
	
	private final List<Message> messages = Collections.synchronizedList(new ArrayList<Message>());
	private final List<Object> items = Collections.synchronizedList(new ArrayList<Object>());
	
	private int maxItemsSize = 0;
	
	public Report(){super();}
	
	public Report(int maxItemsSize) {
		super();
		this.maxItemsSize = maxItemsSize;
	}

	public Collection<Message> getMessages() {
		return Collections.unmodifiableCollection(messages);
	}
	 
	public void addMessage(String message) {
		this.messages.add(new Message(message));
	}
	
	public void addItem(Object item) {
		this.items.add(item);
	}
	
	public Object getItem(Object item) {
		int itemIndex = this.items.indexOf(item);
		if(itemIndex != -1) {
			return this.items.get(itemIndex);
		} else {
			return null;
		}
	}
	
	public boolean hasMessages() {
		if (this.messages.size() > 0) {
			return true;
	    }
	    return false;
	}
	
	public boolean hasItems() {
		if (this.items.size() > 0) {
			return true;
	    }
	    return false;
	}

	public List<Object> getItems() {
		return items;
	}

	public int getMaxItemsSize() {
		return maxItemsSize;
	}
	
}
