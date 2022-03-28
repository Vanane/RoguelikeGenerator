package plugins.roguelike.patterns;

import plugins.roguelike.FieldChangedEventArgs;

public interface Observer {
	public void onFieldChanged(Observable source, FieldChangedEventArgs args);
}
