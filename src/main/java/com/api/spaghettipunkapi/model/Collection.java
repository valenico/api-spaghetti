package com.api.spaghettipunkapi.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Collection {
	private @Id @GeneratedValue Long id;
	
	@Column(unique=true)
	private String name;	
	
	@OneToMany(mappedBy=("collection"), fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Nft> nft_collection;
	
	private int size;
	private String contract_address;
	private String contract_name;
	private String contract_key;
	@Column(columnDefinition="TEXT")
	private String description;
	private String artist;

	
	public Collection() {
		super();
	}

	public Collection(String name, int size, String contract_address, String contract_name,
			String contract_key, String description, String artist) {
		super();
		this.name = name;
		this.size = size;
		this.contract_address = contract_address;
		this.contract_name = contract_name;
		this.contract_key = contract_key;
		this.description = description;
		this.artist = artist;
		this.nft_collection =new LinkedList<Nft>();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Nft> getNft_collection() {
		return nft_collection;
	}
	public void setNft_collection(List<Nft> nft_collection) {
		this.nft_collection = nft_collection;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void incrementSize() {
		this.size++;
	}
	public String getContract_address() {
		return contract_address;
	}
	public void setContract_address(String contract_address) {
		this.contract_address = contract_address;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public String getContract_key() {
		return contract_key;
	}
	public void setContract_key(String contract_key) {
		this.contract_key = contract_key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void addNft(Nft nft) {
		nft.setCollection(this);
		nft_collection.add(nft);
		incrementSize();
	}

}
