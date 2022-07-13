package com.api.spaghettipunkapi.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Nft {
	private @Id @GeneratedValue Long id;
	
	private int token_id;
	
	@ManyToOne
	@JoinColumn(name="collection_name", nullable=false)
	private Collection collection;
	
	@Column(unique=true)
	private String name;
	
	@ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name = "nft_attributes", joinColumns = {@JoinColumn(name = "nft_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "type")
    @Column(name = "value")
	private Map<String,String> attributes;
	
	private String image;
	private String imageHash;
	private int trait_count;
	private int rarity;
	
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
    private byte[] imageBytes;

	public Nft() {
		super();
	}

	public Nft(int token_id, Collection collection, String name, HashMap<String, String> attributes, String image, String imageHash,
			int trait_count, int rarity) {
		super();
		this.token_id = token_id;
		this.collection = collection;
		this.name = name;
		this.attributes = attributes;
		this.image = image;
		this.imageHash = imageHash;
		this.trait_count = trait_count;
		this.rarity = rarity;
	}
	

	public Nft(String name,int token_id, Map<String, String> attributes, String image, String imageHash, int trait_count,
			int rarity) {
		super();
		this.token_id = token_id;
		this.name = name;
		this.attributes = attributes;
		this.image = image;
		this.imageHash = imageHash;
		this.trait_count = trait_count;
		this.rarity = rarity;
	}




	public Nft(String name, int token_id, Map<String, String> attributes, String image,
			String imageHash, int trait_count, int rarity, byte[] imageBytes) {
		super();
		this.token_id = token_id;
		this.name = name;
		this.attributes = attributes;
		this.image = image;
		this.imageHash = imageHash;
		this.trait_count = trait_count;
		this.rarity = rarity;
		this.imageBytes = imageBytes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageHash() {
		return imageHash;
	}

	public void setImageHash(String imageHash) {
		this.imageHash = imageHash;
	}

	public int getTrait_count() {
		return trait_count;
	}

	public void setTrait_count(int trait_count) {
		this.trait_count = trait_count;
	}

	public int getRarity() {
		return rarity;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public int getToken_id() {
		return token_id;
	}

	public void setToken_id(int token_id) {
		this.token_id = token_id;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	
}
