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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.dashboard.batch.mng.dto.BatchDto;
import com.tyss.dashboard.batch.mng.entity.BatchEntity;
import com.tyss.dashboard.batch.mng.services.BatchServicesImpl;

@RestController
@RequestMapping("batch/")
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
	
	@PostMapping("add")
	public ResponseEntity<BatchEntity> createBatch(@RequestBody BatchDto batchDto) {

		System.out.println("BatchController : inside create batch");
		return batchServicesImpl.createBatch(mapper.map(batchDto, BatchEntity.class));
	}

	@GetMapping("get")
	public ResponseEntity<BatchEntity> getBatch(@RequestParam String batchCode) {

		return batchServicesImpl.viewBatch(batchCode);
	}
	
	@GetMapping("get/all")
	public List<BatchEntity> getAllBatches() {

		return batchServicesImpl.getAllBatches();
	}


	@PutMapping("put")
	public ResponseEntity<BatchEntity> updateBatch(@RequestBody BatchEntity batch) {

		return batchServicesImpl.updateBatch(batch);
	}

	@DeleteMapping("delete")
	public ResponseEntity<String> deleteBatch(@RequestParam String batchID) {

		return batchServicesImpl.deleteBatch(batchID);
	}
}
