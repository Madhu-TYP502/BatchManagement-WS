package com.tyss.dashboard.batch.mng.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tyss.dashboard.batch.mng.entity.BatchEntity;

@Repository("BatchRepository")
public interface BatchRepository extends MongoRepository<BatchEntity,String> {

	public boolean existsByBatchCode(String batchChode);

	public BatchEntity findByBatchCode(String batchChode);
}
