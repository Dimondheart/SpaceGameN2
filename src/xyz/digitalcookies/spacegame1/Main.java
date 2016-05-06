package xyz.digitalcookies.spacegame1;

/** Sets up and starts the game.
 * @author Bryan Charles Bettis
 */
public class Main extends xyz.digitalcookies.objective.Game
{
	/** The hull data file resource manager. */
	public static final HullResources HULL_RESOURCES = new HullResources();
	/** An instance of this class (only needed until the engine fully transfers
	 * over to JavaFX.)
	 */
	public static final Main MAIN = new Main();
	
	static
	{
		HULL_RESOURCES.initialize(HullResources.DATA_LOCATION, ".txt");
	}
	
	/** Standard constructor. */
	public Main()
	{
		super(
				Main.class.getProtectionDomain().getCodeSource().getLocation(),
				xyz.digitalcookies.spacegame1.gamestate.MainMenu.class
				);
	}
	
	/** Launches the game.
	 * @param args the program arguments
	 */
	public static void main(String[] args)
	{
		MAIN.init();
		MAIN.start();
	}
}
