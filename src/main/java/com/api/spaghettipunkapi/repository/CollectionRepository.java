package com.api.spaghettipunkapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.spaghettipunkapi.model.Collection;
import com.api.spaghettipunkapi.model.Nft;


public interface CollectionRepository extends JpaRepository<Collection, Long>{
	Collection findByName(String name);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.token_id asc")
	List<Nft> filterNftByLowerTokenId(@Param(value = "collection_name") String collection_name);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.token_id desc")
	List<Nft> filterNftByHigherTokenId(@Param(value = "collection_name") String collection_name);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.rarity desc")
	List<Nft> filterNftByMostRare(@Param(value = "collection_name") String collection_name);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.rarity asc")
	List<Nft> filterNftByLeastRare(@Param(value = "collection_name") String collection_name);
	
	
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.token_id asc")
	List<Nft> filterNftByLowerTokenIdAndTraitCount(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.token_id desc")
	List<Nft> filterNftByHigherTokenIdLowerTraitCount(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.rarity desc")
	List<Nft> filterNftByMostRareAndTraitCount(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.rarity asc")
	List<Nft> filterNftByLeastRareAndTraitCount(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count);
	
	// ******  PAGEABLE  *********
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.token_id asc")
	List<Nft> filterNftByLowerTokenIdPageable(@Param(value = "collection_name") String collection_name, Pageable pageable);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.token_id desc")
	List<Nft> filterNftByHigherTokenIdPageable(@Param(value = "collection_name") String collection_name, Pageable pageable);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.rarity desc")
	List<Nft> filterNftByMostRarePageable(@Param(value = "collection_name") String collection_name, Pageable pageable);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name order by t.rarity asc")
	List<Nft> filterNftByLeastRarePageable(@Param(value = "collection_name") String collection_name, Pageable pageable);
	
	
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.token_id asc")
	List<Nft> filterNftByLowerTokenIdAndTraitCountPageable(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count, Pageable pageable);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.token_id desc")
	List<Nft> filterNftByHigherTokenIdLowerTraitCountPageable(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count, Pageable pageable);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.rarity desc")
	List<Nft> filterNftByMostRareAndTraitCountPageable(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count, Pageable pageable);
	
	@Query("select  t from Nft t where t.collection.name = :collection_name and t.trait_count = :trait_count order by t.rarity asc")
	List<Nft> filterNftByLeastRareAndTraitCountPageable(@Param(value = "collection_name") String collection_name, @Param(value = "trait_count") int trait_count, Pageable pageable);
	
}
