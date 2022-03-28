package plugins.roguelike.patterns;

public interface Observer {
	public void onNotify(Observable source, String data);
}
