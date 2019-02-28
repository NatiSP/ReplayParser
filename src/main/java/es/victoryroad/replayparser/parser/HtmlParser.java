package es.victoryroad.replayparser.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import es.victoryroad.replayparser.model.Mon;
import es.victoryroad.replayparser.model.Replay;
import es.victoryroad.replayparser.model.Team;

public class HtmlParser {

	public static Replay parseReplay(String replayId) throws IOException {
		Replay replay = new Replay();
		Document doc = null;
		Pattern patternPoke = Pattern.compile("\\|poke\\|p\\d\\|\\w+");
		Pattern patternTrainer = Pattern.compile("\\|player\\|p\\d\\|\\w+");
		Pattern patternWinner = Pattern.compile("\\|win\\|\\w+");
		try {
			doc = Jsoup.connect("http://replay.pokemonshowdown.com/gen7vgc2019moonseries-" + replayId + ".log").get();
		} catch (HttpStatusException hse) {
			System.out.println("Replay " + replayId + " no existe.");
			return null;
		}
		Elements newsHeadlines = doc.select("body");
	    Team team1 = new Team();
		Team team2 = new Team();
		for (Element headline : newsHeadlines) {
			try {
				for(Node node : headline.childNodes()) {
					if(node instanceof TextNode) {
						String text = ((TextNode) node).text();
						if(text.contains("|player") && text.contains("|start")) {
							System.out.println(text);
							// create matcher for pattern p and given string
						    Matcher mPoke = patternPoke.matcher(text);
						    // create matcher for pattern p and given string
						    Matcher mTrainer = patternTrainer.matcher(text);
						    // if an occurrence if a pattern was found in a given string...
							List<String> team1List = new ArrayList<String>();
							List<String> team2List = new ArrayList<String>();
							while (mTrainer.find()) {
								String[] splittedData = mTrainer.group(0).replace("|player|p", "").split(Pattern.quote("|"));
								if(splittedData[0].equalsIgnoreCase("1")) {
									team1.setTrainer(splittedData[1]);
								} else {
									team2.setTrainer(splittedData[1]);
								}
							}
						    while (mPoke.find()) {
						        // ...then you can use group() methods.
						        System.out.println(mPoke.group(0)); // whole matched expression
						        String poke = mPoke.group(0);
						        Mon mon = new Mon();
						        
						        String[] splittedData = poke.replace("|poke|p", "").split(Pattern.quote("|"));
						        mon.setName(splittedData[1]);
						        if(splittedData[0].equalsIgnoreCase("1")) {
						        	team1List.add(splittedData[1]);
						        } else {
						        	team2List.add(splittedData[1]);
						        }
						    }
						    Collections.sort(team1List);
						    for(String name : team1List) {
						    	Mon mon = new Mon();
						    	mon.setName(name);
						    	team1.getMons().add(mon);
						    }
						    Collections.sort(team2List);
						    for(String name : team2List) {
						    	Mon mon = new Mon();
						    	mon.setName(name);
						    	team2.getMons().add(mon);
						    }
						    Matcher mWinner = patternWinner.matcher(text);
						    while(mWinner.find()) {
						    	String winner = mWinner.group(0).replace("|win|", "");
						    	if(winner.equalsIgnoreCase(team1.getTrainer())) {
						    		replay.setWinner(1);
						    	} else {
						    		replay.setWinner(2);
						    	}
						    }
							try {
								PrintWriter out = new PrintWriter("gen7vgc2019moonseries-" + replayId + ".log");
							    out.println(text);
								out.close();
								out.flush();
							} catch (Exception e) {
								System.out.println("Ha habido un problema al guardar el archivo");
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error al parsear la replay: " + replayId);
			}
		}
		replay.setTeam1(team1);
		replay.setTeam2(team2);
		return replay;
	}
}
