package com.team3.board.test.controller;

import com.team3.board.test.entity.TestEntity;
import com.team3.board.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {

        this.testService = testService;
    }

    // Create form 페이지로 이동
    @GetMapping("/create")
    public String showCreateForm() {

        return "createTestEntity";  // createTestEntity.html로 이동
    }

    // Create
    @PostMapping()
    public String createTestEntity(@RequestParam("content") String content, Model model) {
//        testService.createTestEntity(content);
//        return "redirect:/api/test";  // 엔티티 생성 후 목록 페이지로 리다이렉트

        TestEntity createdEntity = testService.createTestEntity(content); // 서비스로 넘겨서 엔티티 만들어서 DB에 저장까지 여기서
        model.addAttribute("testEntity", createdEntity); // 모델 : 뷰랑 컨트롤러 사이에서 왔다갔다하는 데이터
        return "createdEntity";
    }

    // Read (Find by ID)
    @GetMapping("/{id}")
    public String getTestEntityById(@PathVariable Long id, Model model) {
        Optional<TestEntity> entity = testService.getTestEntityById(id);
        if (entity.isPresent()) {
            model.addAttribute("testEntity", entity.get());
            return "testEntityDetail";  // 상세 페이지로 이동
        } else {
            return "notFound";  // 엔티티를 찾지 못했을 때
        }
    }

    // Read (Find all)
    @GetMapping
    public String getAllTestEntities(Model model) {
        List<TestEntity> entities = testService.getAllTestEntities();
        model.addAttribute("testEntities", entities);
        return "testEntityList";  // 목록 페이지로 이동
    }

    // Update
    @PostMapping("/update/{id}")
    public String updateTestEntity(@PathVariable Long id, @RequestParam String content, Model model) {
        try {
            TestEntity updatedEntity = testService.updateTestEntity(id, content);
            model.addAttribute("testEntity", updatedEntity);
            return "redirect:/api/test";  // 업데이트 후 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            return "notFound";  // 엔티티를 찾지 못했을 때
        }
    }

    // Delete
    @PostMapping("/delete/{id}")
    public String deleteTestEntity(@PathVariable Long id) {
        testService.deleteTestEntity(id);
        return "redirect:/api/test";  // 삭제 후 목록 페이지로 리다이렉트
    }
}
