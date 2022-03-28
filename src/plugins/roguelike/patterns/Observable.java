package plugins.roguelike.patterns;

import plugins.roguelike.FieldChangedEventArgs;

public interface Observable {
	public void notifyObject(FieldChangedEventArgs data);
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
}
