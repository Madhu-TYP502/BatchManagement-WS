package com.tyss.dashboard.batch.mng.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tyss.dashboard.batch.mng.entity.BatchEntity;

public interface BatchServices {
	public ResponseEntity<String> createBatch(BatchEntity batch);

	public ResponseEntity<String> deleteBatch(BatchEntity Batch);

	public ResponseEntity<String> updateBatch(BatchEntity batch);

	public ResponseEntity<BatchEntity> viewBatch(String batchCode);

	public ResponseEntity<List<BatchEntity>> viewBatchByTrainer(String trainerID);
}
