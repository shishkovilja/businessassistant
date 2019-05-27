package io.khasang.ba.controller;

import io.khasang.ba.entity.PointOfInterest;
import io.khasang.ba.service.PointOfInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pointOfInterest")
public class PointOfInterestController {

    @Autowired
    PointOfInterestService pointOfInterestService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    public PointOfInterest addPointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        pointOfInterestService.addPointOfInterest(pointOfInterest);
        return pointOfInterest;
    }

    @GetMapping(value = "/get/{id}", produces = "application/json;charset=utf-8")
    public ResponseEntity<PointOfInterest> getPointOfInterestById(@PathVariable(value = "id") long id) {
        PointOfInterest pointOfInterest = pointOfInterestService.getPointOfInterestById(id);
        return pointOfInterest == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(pointOfInterest);
    }

    @PutMapping(value = "/update", produces = "application/json;charset=utf-8")
    public PointOfInterest updatePointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        return pointOfInterestService.updatePointOfInterest(pointOfInterest);
    }

    @GetMapping(value = "/get/all", produces = "application/json;charset=utf-8")
    public List<PointOfInterest> getAllPointOfInterests() {
        return pointOfInterestService.getAllPointOfInterest();
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/delete/{id}", produces = "application/json;charset=utf-8")
    public PointOfInterest deletePointOfInterest(@PathVariable(value = "id") long id) {
        return pointOfInterestService.deletePointOfInterest(id);
    }
}
