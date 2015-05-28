package cl.boulder.kart.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLoader {

    private ArrayList<MapItem> data;

    public ArrayList<MapItem> getMapData(String mapName) throws IOException {
        data = new ArrayList<MapItem>();
        FileHandle file = Gdx.files.internal("data/"+mapName+".map");
        BufferedReader buffer =  new BufferedReader(file.reader());
        String line = buffer.readLine();
        while(line!=null && !line.equals("")){
            String[] comps = line.split(" ");
            MapItem item = new MapItem(Float.parseFloat(comps[1]),Integer.parseInt(comps[2]),comps[0].charAt(0));
            data.add(item);
            line = buffer.readLine();
        }
        return data;
    }

}