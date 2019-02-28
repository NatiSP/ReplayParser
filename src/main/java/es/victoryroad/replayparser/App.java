package es.victoryroad.replayparser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import es.victoryroad.replayparser.model.Replay;
import es.victoryroad.replayparser.parser.HtmlParser;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
    	List<Replay> replayList = new ArrayList<Replay>();
        System.out.println( "Bienvenido!" );
        System.out.println( "Selecciona el id de la replay inicial:" );
        Scanner scanner = new Scanner(System.in); 
        String input = scanner.nextLine();
        int startId = Integer.parseInt(input);
        System.out.println( "Selecciona el id de la replay final:" );
        input = scanner.nextLine();
        int endId = Integer.parseInt(input);
        scanner.close();
        try {
        	for(int index = startId; index < endId; index++) {
        		Replay replay = HtmlParser.parseReplay(String.valueOf(index));
        		if(replay != null) {
        			replayList.add(replay);
        		}
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
        Gson gson = new Gson();
        try {
			PrintWriter out = new PrintWriter("gen7vgc2019moonseries-" + startId + "-to-" + endId +".log");
		    out.println(gson.toJson(replayList));
			out.close();
			out.flush();
		} catch (Exception e) {
			System.out.println("Ha habido un problema al guardar el archivo");
		}
        System.out.println( "Exportación finalizada. Pulse ENTER para salir" );
        scanner.nextLine();
    }
}
