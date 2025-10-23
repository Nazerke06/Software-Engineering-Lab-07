package com.example.LAB_05.repository;

import com.example.LAB_05.entity.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operators, Long> {
}
