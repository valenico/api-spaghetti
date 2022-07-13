package com.api.spaghettipunkapi.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.spaghettipunkapi.model.Collection;
import com.api.spaghettipunkapi.model.Nft;
import com.api.spaghettipunkapi.repository.CollectionRepository;
import com.api.spaghettipunkapi.repository.NftRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class CollectionController {
	private final CollectionRepository repository;
	
	@Autowired
	NftRepository nftRepository;

	public CollectionController(CollectionRepository repository) {
		this.repository = repository;
	}
	
	@CrossOrigin
	@GetMapping("/collections")
	List<Collection> findAll() {
		  return repository.findAll();
	}
	
	//@PostMapping("/collections")
	String addCollection(@RequestBody Collection collection) {
		if(collection==null) return "failed";
		Collection res = repository.findByName(collection.getName());
		if(res!=null) return "Failed: collection already exists";
		//System.out.println(asJsonString(collection));
		List<Nft> nfts = collection.getNft_collection();
		collection.setNft_collection(null);
		repository.save(collection);
		Collection c = repository.findByName(collection.getName());
		for(int i=0; i<nfts.size();i++) {
			Nft aux = nfts.get(i);
			aux.setCollection(c);
			//System.out.println(asJsonString(aux.getCollection()));
			nftRepository.save(aux);
		}
		return "OK"; 
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

	@CrossOrigin
	@GetMapping("/collections/{name}")
	Collection findById(@PathVariable String name) {
		Collection collection = repository.findByName(name);
		if(collection!=null) return collection;
		return null;
	}
	
	
	
	@CrossOrigin
	@GetMapping("/collections/{name}/{token_id}")
	Nft findNFTByTokenIdAndCollection(@PathVariable String name, @PathVariable int token_id) {
		Collection collection = repository.findByName(name);
		if(collection!=null) {
			Nft nft = nftRepository.findByToken_Id(name, token_id);
			if(nft!=null) return nft;
			return null;
		}
		return null;
	}
	
	@CrossOrigin
	@GetMapping("/collections/{name}/nfts")
	List<Nft> findNFTByCollectionName(@PathVariable String name) {
		Collection collection = repository.findByName(name);
		if(collection!=null) return collection.getNft_collection();
		return null;
	}
	

	@CrossOrigin
	@GetMapping("/collections/{name}/nfts/{order}")
	List<Nft> filterBy(@PathVariable String name, @PathVariable String order) {
		if(order.equals("lowerTokenId")) return repository.filterNftByLowerTokenId(name);
		if(order.equals("higherTokenId")) return repository.filterNftByHigherTokenId(name);
		if(order.equals("lowerRarity")) return repository.filterNftByLeastRare(name);
		if(order.equals("higherRarity")) return repository.filterNftByMostRare(name);
		return null;
	}
	
	@CrossOrigin
	@GetMapping("/collections/{name}/nfts/{order}/page/{pageIndex}/{pageSize}")
	List<Nft> pageableFilterBy(@PathVariable String name, @PathVariable String order, @PathVariable String pageIndex, @PathVariable String pageSize) {
		Pageable page = PageRequest.of(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		if(order.equals("lowerTokenId")) return repository.filterNftByLowerTokenIdPageable(name,page);
		if(order.equals("higherTokenId")) return repository.filterNftByHigherTokenIdPageable(name,page);
		if(order.equals("lowerRarity")) return repository.filterNftByLeastRarePageable(name,page);
		if(order.equals("higherRarity")) return repository.filterNftByMostRarePageable(name,page);
		return null;
	}
	
	
	@CrossOrigin
	@GetMapping("/collections/{name}/nfts/{order}/{attributes}")
	List<Nft> filterByAttributes(@PathVariable String name, @PathVariable String order, @PathVariable String attributes) {
		List<Nft> nfts = new LinkedList<Nft>();
		
		HashMap<String,String> attr = getAttrFilters(attributes);
		System.out.println(attr);
		System.out.println(attr.get("tokenId"));
		if(attr.get("tokenId")!=null) {
			System.out.println(attr.get("tokenId"));
			nfts.add(nftRepository.findByToken_Id(name, Integer.parseInt(attr.get("tokenId"))));
			return nfts;
		}
		if(attr.get("trait count")!=null) {
			int tc = Integer.parseInt(attr.get("trait count"));
				if(order.equals("lowerTokenId")) nfts = repository.filterNftByLowerTokenIdAndTraitCount(name,tc);
				if(order.equals("higherTokenId")) nfts = repository.filterNftByHigherTokenIdLowerTraitCount(name,tc);
				if(order.equals("lowerRarity")) nfts = repository.filterNftByLeastRareAndTraitCount(name,tc);
				if(order.equals("higherRarity")) nfts = repository.filterNftByMostRareAndTraitCount(name,tc);

				attr.remove("trait count");
		}	
		else {
			if(order.equals("lowerTokenId")) nfts = repository.filterNftByLowerTokenId(name);
			if(order.equals("higherTokenId")) nfts = repository.filterNftByHigherTokenId(name);
			if(order.equals("lowerRarity")) nfts = repository.filterNftByLeastRare(name);
			if(order.equals("higherRarity")) nfts = repository.filterNftByMostRare(name);
		}
		System.out.println(attr.toString());
		
		if(nfts==null || attr==null) return null;
		
		List<Nft> res = new LinkedList<Nft>();
		for(int i=0;i<nfts.size();i++) {
			boolean contained = subsetOf(nfts.get(i).getAttributes(), attr);
			if(contained==true) res.add(nfts.get(i));
		}
		return res;
	}
	
	
	@CrossOrigin
	@GetMapping("/collections/{name}/nfts/{order}/{attributes}/page/{pageIndex}/{pageSize}")
	List<Nft> pageableFilterByAttributes(@PathVariable String name, @PathVariable String order, @PathVariable String attributes, @PathVariable String pageIndex, @PathVariable String pageSize) {
		List<Nft> nfts = new LinkedList<Nft>();
		Pageable page = PageRequest.of(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		
		HashMap<String,String> attr = getAttrFilters(attributes);
		if(attr.get("tokenId")!=null) {
			System.out.println(attr.get("tokenId"));
			nfts.add(nftRepository.findByToken_Id(name, Integer.parseInt(attr.get("tokenId"))));
			return nfts;
		}
		if(attr.get("trait count")!=null) {
			int tc = Integer.parseInt(attr.get("trait count"));
				if(order.equals("lowerTokenId")) nfts = repository.filterNftByLowerTokenIdAndTraitCountPageable(name,tc,page);
				if(order.equals("higherTokenId")) nfts = repository.filterNftByHigherTokenIdLowerTraitCountPageable(name,tc,page);
				if(order.equals("lowerRarity")) nfts = repository.filterNftByLeastRareAndTraitCountPageable(name,tc,page);
				if(order.equals("higherRarity")) nfts = repository.filterNftByMostRareAndTraitCountPageable(name,tc,page);

				attr.remove("trait count");
		}	
		else {
			if(order.equals("lowerTokenId")) nfts = repository.filterNftByLowerTokenIdPageable(name,page);
			if(order.equals("higherTokenId")) nfts = repository.filterNftByHigherTokenIdPageable(name,page);
			if(order.equals("lowerRarity")) nfts = repository.filterNftByLeastRarePageable(name,page);
			if(order.equals("higherRarity")) nfts = repository.filterNftByMostRarePageable(name,page);
		}
		System.out.println(attr.toString());
		
		if(nfts==null || attr==null) return null;
		
		List<Nft> res = new LinkedList<Nft>();
		for(int i=0;i<nfts.size();i++) {
			boolean contained = subsetOf(nfts.get(i).getAttributes(), attr);
			if(contained==true) res.add(nfts.get(i));
		}
		return res;
	}
	
	
	private boolean subsetOf(Map<String,String> first, HashMap<String,String> second) {
		
		Set<Entry<String,String>> filter = second.entrySet();
		Set<Entry<String,String>> data = first.entrySet();
		if(data.containsAll(filter)) return true;
		else return false;
	}
	
	
	private HashMap<String,String> getAttrFilters(String attributes){		
		try {
			HashMap<String,String> res = new HashMap<String,String>();
			String[] aux = attributes.split("&");
			for(int i=0; i<aux.length;i++) {
				String[] attr = aux[i].split(":");
				attr[0] = attr[0].replaceAll("_", " ");
				attr[1] = attr[1].replaceAll("_", " ");
				res.put(attr[0], attr[1]);
			}
			return res;
	      } catch(ArrayIndexOutOfBoundsException e) {
	    	  return null;
	      }		
	}
}
