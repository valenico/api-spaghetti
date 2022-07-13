package com.api.spaghettipunkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.spaghettipunkapi.model.Nft;

public interface NftRepository extends JpaRepository<Nft, Long> {
	Nft findByName(String name);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.token_id = :token_id")
	Nft findByToken_Id(@Param(value = "collection_name") String collection_name, @Param(value = "token_id") int token_id);
}
