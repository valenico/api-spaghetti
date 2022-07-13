package com.api.spaghettipunkapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.spaghettipunkapi.model.Nft;
import com.api.spaghettipunkapi.repository.NftRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class NftController {
	private final NftRepository repository;

	public NftController(NftRepository repository) {
		this.repository = repository;
	}
	
	@CrossOrigin
	@GetMapping("/nfts")
	List<Nft> findAll() {
		  return repository.findAll();
	}
	
	/*
	@PostMapping("/nfts")
	String addNft(@RequestBody Nft nft) {
		if(nft==null) return "failed";
		Nft res = repository.findByName(nft.getName());
		if(res!=null) return "Failed: nft already exists";
		Collection collection = collectionRepository.findByName(nft.getCollection().getName());
		nft.setCollection(collection);
		System.out.println(asJsonString(nft));
		repository.save(nft);
		return "OK"; 
	}*/
	
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
	@GetMapping("/nfts/{id}")
	Nft findById(@PathVariable Long id) {
		Optional<Nft> nft = repository.findById(id);
		if(nft.isPresent()) return nft.get();
		return null;
	}
}
