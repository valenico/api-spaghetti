#CONNESSIONE ALLE API

Url base = "https://api-spaghetti.herokuapp.com/"

-> HTTP GET url="/collections"
  return all collections
-> HTTP GET url="/collections/{name}"
  return collection with name {name}
-> HTTP GET url="/collections/{name}/{token_id}"
  return nft with token_id {token_id} of collection with name {name}
-> HTTP GET url="/collections/{name}/nfts"
  return all nft of collection, with no order


-> HTTP GET url="/nfts"
  return all nfts
-> HTTP GET url="/nfts/{id}"
  return nft with id {id} (Database index, NOT token_id)

FILTERS

-> HTTP GET url="/collections/{name}/nfts/higherRarity"
  return all nft of collection, ordered by rarity, higher to lower
-> HTTP GET url="/collections/{name}/nfts/lowerRarity"
  return all nft of collection, ordered by rarity, lower to higher

-> HTTP GET url="/collections/{name}/nfts/lowerTokenId"
  return all nft of collection, ordered by token_id, lower to higher
-> HTTP GET url="/collections/{name}/nfts/higherTokenId"
  return all nft of collection, ordered by token_id, higher to lower

-> HTTP GET url="/collections/{name}/nfts/{order}/{attributes}"
  return all nft of collection, ordered by {order}, filtered with attributes {attributes}
  {order} = {higherRarity, lowerRarity, lowerTokenId, higherTokenId}
  {attributes} FORMAT = "name1:value1&name2value2&name3:value3"
                se nome o valore contengono spazi, fare replace(" ", "-") ("name1-con-spazi:value1-con-spazi")
