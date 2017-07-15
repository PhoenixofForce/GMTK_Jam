package game.map;

import de.pof.textures.TextureHandler;

import java.io.*;

public class Map {

	private int[][] map;
	private int width, height;

	public Map(String mapname) {

		try {
			InputStream in = ClassLoader.getSystemResourceAsStream(String.format("res/maps/%s.txt", mapname));
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line = r.readLine();
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}