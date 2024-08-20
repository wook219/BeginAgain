package com.team3.board.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team3.board.test.entity.TestEntity;
import com.team3.board.test.repository.TestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // Create
    public TestEntity createTestEntity(String content) {
        TestEntity entity = new TestEntity(content); // 저장할 testEntity 객체를 생성하는 지점
        return testRepository.save(entity); // 실제로 저장이 일어나는 지점
    }

    // Read (Find by ID)
    public Optional<TestEntity> getTestEntityById(Long id) {
        return testRepository.findById(id);
    }

    // Read (Find all)
    public List<TestEntity> getAllTestEntities() {
        return testRepository.findAll();
    }

    // Update
    public TestEntity updateTestEntity(Long id, String content) {
        Optional<TestEntity> optionalEntity = testRepository.findById(id);
        if (optionalEntity.isPresent()) {
            TestEntity entity = optionalEntity.get();
            entity.setContent(content);
            return testRepository.save(entity);
        } else {
            throw new RuntimeException("Entity not found with id: " + id);
        }
    }

    // Delete
    public void deleteTestEntity(Long id) {
        testRepository.deleteById(id);
    }
}
