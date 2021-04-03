package com.eureka.capstone.repository.product;

import com.eureka.capstone.domain.product.ReleaseVersion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseVersionRepository extends JpaRepository<ReleaseVersion, Long> {

    List<ReleaseVersion> findByProduct_Id(Long aLong);

}
