package com.eureka.capstone.repository;

import com.eureka.capstone.domain.product.SubSystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubsystemRepository extends JpaRepository<SubSystem, Long> {

    List<SubSystem> findByProduct_Id(Long aLong);

}
