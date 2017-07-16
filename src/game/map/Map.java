package game.map;

import de.pof.GUIConstants;
import de.pof.game.entities.Entity;
import de.pof.game.entities.Player;
import de.pof.game.entities.Spider;
import de.pof.game.entities.Spike;
import de.pof.textures.TextureHandler;
import de.pof.window.Window;
import de.pof.window.views.VictoryView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Map {

	private int[][] map;
	private int width, height;

	private List<Entity> entities;

	Window window;
	public Map(String mapname, Window window) {
		this.window = window;
		try {
			InputStream in = ClassLoader.getSystemResourceAsStream(String.format("res/maps/%s.txt", mapname));
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line = r.readLine();

			entities = new ArrayList<>();
			int entites = Integer.parseInt(line);

			for(int i = 0; i < entites; i++) {
				line = r.readLine();
				if(line.startsWith("s ")) entities.add(new Spider(Integer.parseInt(line.split(" ")[1]) * GUIConstants.TILE_SIZE, Integer.parseInt(line.split(" ")[2]) * GUIConstants.TILE_SIZE));
				if(line.startsWith("p ")) entities.add(new Spike(Integer.parseInt(line.split(" ")[1]) * GUIConstants.TILE_SIZE, Integer.parseInt(line.split(" ")[2]) * GUIConstants.TILE_SIZE));

			}

			line = r.readLine();
			this.width = Integer.parseInt(line.split(" ")[0]);
			this.height = Integer.parseInt(line.split(" ")[1]);

			this.map = new int[width][ height];

			for(int y = 0; y < height; y ++) {
				String[] cont = r.readLine().split(" ");

				for(int x = 0; x < width; x++) {
					map[x][y] = Integer.parseInt(cont[x]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		TextureHandler.loadImagePng("grass", "grass");
		TextureHandler.loadImagePng("dirt", "dirt");
	}

	public int[][] getMap() {
		return map;
	}

	public int getTile(int x, int y) {
		return map[x][y];
	}

	public boolean isSolid(int x, int y) {
		return getTile(x, y) == 1 || getTile(x, y) == 2;
	}

	public void onPlayerMove(Player player) {
		if(getTile((int) Math.floor(player.getPosition().x/GUIConstants.TILE_SIZE), (int) Math.floor(player.getPosition().y / GUIConstants.TILE_SIZE)) == 3) {
			window.updateView(new VictoryView());
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Entity> getEntities() {
		return entities;
	}
}