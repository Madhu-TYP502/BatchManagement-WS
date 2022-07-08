package com.tyss.dashboard.batch.mng.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tyss.dashboard.batch.mng.entity.BatchEntity;

public interface BatchServices {
	public ResponseEntity<BatchEntity> createBatch(BatchEntity batch);

	public ResponseEntity<String> deleteBatch(String batchID);

	public ResponseEntity<BatchEntity> updateBatch(BatchEntity batch);

	public ResponseEntity<BatchEntity> viewBatch(String batchCode);

	public List<BatchEntity> getAllBatches();
}
