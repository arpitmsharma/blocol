package io.g33k.blocol.controller;

import io.g33k.blocol.model.ClusterData;
import io.g33k.blocol.service.BlocolService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

	private static final Logger log = LoggerFactory
			.getLogger(ApiController.class);

	private @Autowired BlocolService blocolService;

	@RequestMapping(value = "/url", method = RequestMethod.GET)
	public ResponseEntity<ClusterData> getResponse(
			@RequestParam("q_url") List<String> seedUrls) {
		log.info("Api called");
		log.info(seedUrls.toString());
		ClusterData clusterData = null;
		try {
			clusterData = blocolService.doBlocol(seedUrls);
		} catch (Exception e) {
			log.error("Error", e);
		}
		if (clusterData != null) {
			log.info(clusterData.toString());
			return new ResponseEntity<ClusterData>(clusterData, HttpStatus.OK);
		} else
			return new ResponseEntity<ClusterData>(
					HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
