package com.tyss.dashboard.batch.mng.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tyss.dashboard.batch.mng.entity.BatchEntity;
import com.tyss.dashboard.batch.mng.repository.BatchRepository;
import com.tyss.dashboard.batch.mng.repository.UserRepository;

@Service
public class BatchServicesImpl implements BatchServices {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<BatchEntity> createBatch(BatchEntity batch) {
		System.out.println("BatchServicesImpl : inside create batch");

		if (batchRepository.existsByBatchCode(batch.getBatchCode())) {
			System.out.println("BatchServicesImpl : batch exists");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(batch);
		}
		batch.setId(UUID.randomUUID().toString());
		System.out.println(batch);

		return ResponseEntity.status(HttpStatus.OK).body(batchRepository.save(batch));
	}

	@Override
	public ResponseEntity<BatchEntity> updateBatch(BatchEntity batch) {
		BatchEntity dbBatch = batchRepository.findById(batch.getId()).get();

		if (dbBatch != null) {
			if (userRepository.existsByName(batch.getTrainerName())) {
				batch.setId(dbBatch.getId());
				return ResponseEntity.status(HttpStatus.OK).body(batchRepository.save(batch));
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		}
		return ResponseEntity.badRequest().body(null);

	}

	@Override
	public ResponseEntity<String> deleteBatch(String batchID) {

		BatchEntity dbBatch = batchRepository.findById(batchID).get();

		if (dbBatch != null) {
			batchRepository.delete(dbBatch);
			return ResponseEntity.status(HttpStatus.OK).body("Batch data deleted susccesfully!!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Batch code do not exists");
	}

	@Override
	public ResponseEntity<BatchEntity> viewBatch(String batchCode) {

		BatchEntity batch = batchRepository.findByBatchCode(batchCode);

		if (batch != null) {
			return ResponseEntity.status(HttpStatus.OK).header("batchID", batch.getId()).body(batch);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(batch);
		}
	}

	@Override
	public List<BatchEntity> getAllBatches() {

		return batchRepository.findAll();
	}

}
