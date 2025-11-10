package com.tls.LiarWire.repositories;

import com.tls.LiarWire.entity.MockApiConfig;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MockRepository extends ReactiveMongoRepository<MockApiConfig, String> {

    @Query("{ '_id': ?0, 'httpMethod': ?1 }")
    Mono<MockApiConfig> findByEndpointAndMethod(String endpoint, String method);

    @Cacheable(value = "mockConfigCache", key = "#id")
    @Query("{ '_id': ?0 }")
    Mono<MockApiConfig> findByObjectId(ObjectId id);

}
