package com.eureka.capstone.repository;

import com.eureka.capstone.domain.product.ReleaseVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReleaseVersionRepository extends JpaRepository<ReleaseVersion, Long> {

    List<ReleaseVersion> findByProduct_Id(Long aLong);

}
