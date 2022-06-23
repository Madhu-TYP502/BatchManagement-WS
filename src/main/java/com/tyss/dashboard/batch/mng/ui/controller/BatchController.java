package com.tyss.dashboard.batch.mng.ui.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.dashboard.batch.mng.dto.BatchDto;
import com.tyss.dashboard.batch.mng.entity.BatchEntity;
import com.tyss.dashboard.batch.mng.services.BatchServicesImpl;

@RestController
@RequestMapping("batchmng/")
public class BatchController {

	@Autowired
	BatchServicesImpl batchServicesImpl;

	ModelMapper mapper;

	{
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@GetMapping("get/status")
	public ResponseEntity<String> status() {

		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Workking...");
	}
	
	@PostMapping("create/batch")
	public ResponseEntity<String> createBatch(@RequestBody BatchDto batchDto) {

		return batchServicesImpl.createBatch(mapper.map(batchDto, BatchEntity.class));
	}

	@GetMapping("get/batch")
	public ResponseEntity<BatchEntity> viewBatch(@RequestParam String batchCode) {

		return batchServicesImpl.viewBatch(batchCode);
	}

	@GetMapping("get/trainer/batch")
	public ResponseEntity<List<BatchEntity>> viewBatchByTrainer(@RequestParam String trainerID) {

		return batchServicesImpl.viewBatchByTrainer(trainerID);
	}

	@PostMapping("update/batch")
	public ResponseEntity<String> updateBatch(@RequestBody BatchEntity batch) {

		return batchServicesImpl.updateBatch(batch);
	}

	@DeleteMapping("delete/batch")
	public ResponseEntity<String> deleteBatch(@RequestBody BatchEntity batch) {

		return batchServicesImpl.deleteBatch(batch);
	}
}
