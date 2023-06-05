package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getTask(){
        return ResponseEntity.ok(new ResponseWrapper("ALL Tasks Retrieved", taskService.listAllTasks(), HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getTasksByTaskName(@PathVariable("id") Long id){
        return ResponseEntity.ok(new ResponseWrapper("Retrieved Task by GIVEN task-Name", taskService.findById(id), HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("New Task Created", HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task -> " + task.getTaskSubject() + " <- Updated", HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("id") Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("Task -> " + taskService.findById(id).getTaskSubject() + " <- Deleted", HttpStatus.OK));
    }

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> empPendingTasks(){
        var taskDTO= taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks Are Successfully Retrieved", taskDTO, HttpStatus.OK));
    }

    @PutMapping("/employee/update")
    public ResponseEntity<ResponseWrapper> empUpdateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task -> " + task.getTaskSubject() + " <- Updated", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper> empArchivedTasks(){
        var taskDTO = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Archived Tasks Are Successfully Retrieved", taskDTO, HttpStatus.OK));
    }

}
