package com.tyss.dashboard.batch.mng.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.dashboard.batch.mng.dto.BatchDto;
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
	public ResponseEntity<String> createBatch(BatchEntity batch) {
		if (batchRepository.existsByBatchCode(batch.getBatchCode())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("$Batch already exists with " + batch.getBatchCode() + " already exists!!");
		} else if (!userRepository.existsByPhone(batch.getTrainerID())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("$Trainer " + "'" + batch.getTrainerName() + "'"
					+ " do not exists!! please add the trainer and try again");
		}

		batch.setId(batch.hashCode());
		batch.setTrainerID(userRepository.findByName(batch.getTrainerName()).getPhone());
		System.out.println(batch);
		batchRepository.save(batch);
		

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		BatchDto batchDto = mapper.map(batch, BatchDto.class);
		String batchJson = "";
		try {
			batchJson = new ObjectMapper().writeValueAsString(batchDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(batchJson);
	}

	@Override
	public ResponseEntity<String> updateBatch(BatchEntity batch) {
		BatchEntity dbBatch = batchRepository.findByBatchCode(batch.getBatchCode());

		if (dbBatch != null) {
			if (userRepository.existsByName(batch.getTrainerName())) {
				batch.setId(dbBatch.getId());
				batchRepository.save(batch);

				ModelMapper mapper = new ModelMapper();
				mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

				BatchDto batchDto = mapper.map(batch, BatchDto.class);

				String batchJson = "";
				try {
					batchJson = new ObjectMapper().writeValueAsString(batchDto);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(batchJson);

			} else {
				return ResponseEntity.badRequest().body("$Trainer " + "'" + batch.getTrainerName() + "'"
						+ " do not exists!! please add the trainer and try again");
			}
		}

		return ResponseEntity.badRequest().body("$Batch do not exists with " + batch.getBatchCode());

	}

	@Override
	public ResponseEntity<String> deleteBatch(BatchEntity batch) {

		BatchEntity dbBatch = batchRepository.findByBatchCode(batch.getBatchCode());

		if (dbBatch != null) {
			batch.setId(dbBatch.getId());
			batchRepository.delete(batch);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("$Batch data deleted susccesfully!!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("$Batch code " + "'" + batch.getBatchCode() + "'" + " do not exists!!!");
	}

	@Override
	public ResponseEntity<BatchEntity> viewBatch(String batchCode) {
		BatchEntity batch = batchRepository.findByBatchCode(batchCode);

		if (batch != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(batch);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(batch);
		}
	}
	
	@Override
	public ResponseEntity<List<BatchEntity>> viewBatchByTrainer(String trainerID) {
		
		List<BatchEntity> batch = batchRepository.findAllByTrainerID(trainerID);

		if (batch != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(batch);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(batch);
		}
	}
}
