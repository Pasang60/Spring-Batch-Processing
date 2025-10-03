package com.batch.batchProcessing.student.service;

import com.batch.batchProcessing.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
}
