package com.api.spaghettipunkapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.api.spaghettipunkapi.model.Collection;
import com.api.spaghettipunkapi.model.Nft;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PostRequest {
	public PostRequest() {
		// TODO Auto-generated constructor stub
	} 
	
	public static void main(String[] args) {
		insertCollection();

	}
	
	static void insertCollection() {
		System.out.println("HELLO");
		JSONParser jsonParser = new JSONParser();
		String url = "C:/Users/valer/Desktop/api-spaghetti/data/minotauri_metadata.json";
		
		int count = 0;
		
		Collection collection = new Collection();
		collection.setName("MinoTauri");
		//collection.setDescription("From the ancient island of Crete, minotauris were captured and locked up by the gods. Because of fear, they were treated like simple war machines, but now, the minotaurs plan to escape from the labyrinth... After thousands of years of accumulated fury and rage, they are ready to strike back against the gods who betrayed them");
		collection.setArtist("MinoTauri");
		collection.setDescription("");
		
		try {
			FileReader reader_first = new FileReader(url);
			//System.out.println(aux + "/_bitcoin_" + fileEntry.getName().split("_")[1] + "_collection_.json");
			Object obj = jsonParser.parse(reader_first);
			JSONArray json = (JSONArray) obj;
			LinkedList<Nft> nfts = new LinkedList<Nft>();
			
			for(int i=0;i<json.size();i++) {
				count = count +1;
				JSONObject aux = (JSONObject) json.get(i);
				int tokenId = Integer.parseInt(aux.get("token_id").toString());
				String name = aux.get("name").toString();
				String image = aux.get("image").toString();
				String imageHash = aux.get("imageHash").toString();
				//byte[] imageBytes = (byte[]) aux.get("imageBytes");
				int rarity = Integer.parseInt(aux.get("rarity").toString());
				
				JSONObject traits = (JSONObject) aux.get("attributes");
				int trait_count = Integer.parseInt(traits.get("trait_count").toString());
				//int trait_count = Integer.parseInt(aux.get("trait_count").toString());
				
				HashMap<String,String> attributes = new HashMap<String,String>();
				
				Set<String> keys = traits.keySet();
				Iterator<String> it = keys.iterator();
				while(it.hasNext()) {
				    String key = it.next();
				    //System.out.println(key + " " + traits.get(key).toString());
				    if(!key.equals("trait_count")) attributes.put(key, traits.get(key).toString());
				}
				
				Nft nft = new Nft(tokenId, collection,name, attributes, image, imageHash, trait_count, rarity);
				nft.setImageBytes(java.util.Base64.getDecoder().decode(aux.get("imageBytes").toString()));
				nfts.add(nft);
				//System.out.println(asJsonString(nft));
			}			
			collection.setSize(count);
			collection.setNft_collection(nfts);
			
			//URL posturl = new URL("https://spaghettipunk-api.herokuapp.com/collections");
			URL posturl = new URL("https://minotauri.herokuapp.com/collections");
			HttpURLConnection con = (HttpURLConnection)posturl.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			
			String jsonInputString = asJsonString(collection);
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			BufferedReader br = new BufferedReader(
					  new InputStreamReader(con.getInputStream(), "utf-8"));
					    StringBuilder response = new StringBuilder();
					    String responseLine = null;
					    while ((responseLine = br.readLine()) != null) {
					        response.append(responseLine.trim());
					    }
					    System.out.println(response.toString());

			
			
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}	
		System.out.println("DONE");
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	private static Nft parseNftObject(JSONObject json) {
		Map<String,String> attributes = new HashMap<String,String>();
		int trait_count = Integer.parseInt(json.get("trait_count").toString());
		int rarity = Integer.parseInt(json.get("rarity").toString());
		JSONObject obj = (JSONObject) json.get("metadata");
		int token_id = Integer.parseInt(json.get("token_id").toString());
		String name = obj.get("name").toString();
		String image = obj.get("image").toString();
		String imageHash = obj.get("imageHash").toString();
		JSONArray array = (JSONArray) obj.get("attributes");
		for(int i=0;i<array.size();i++) {
			JSONObject attr = (JSONObject) array.get(i);
			String type = attr.get("type").toString();
			String value = attr.get("value").toString();
			attributes.put(type, value);
		}
		
		return new Nft(name,token_id, attributes, image, imageHash, trait_count,rarity);
	}

	private static Collection parseCollectionObject(JSONObject elem) {
		String collection_name = elem.get("collection_name").toString().replace(" ", "");
		//int collection_size = Integer.parseInt(elem.get("collection_size").toString());
		String contract_address = elem.get("contract_address").toString();
		String contract_name = elem.get("contract_name").toString();
		String contract_key = elem.get("contract_key").toString();
		String description = ((JSONObject) elem.get("metadata")).get("description").toString();
		String artist = ((JSONObject) elem.get("metadata")).get("artist").toString();
		return new Collection(collection_name, 0, contract_address, contract_name, 
				contract_key, description,artist);
	}
}
