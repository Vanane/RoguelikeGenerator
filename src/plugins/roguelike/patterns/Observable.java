package plugins.roguelike.patterns;

public interface Observable {
	public void notifyObject(String data);
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
}
