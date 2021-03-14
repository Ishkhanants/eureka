package com.eureka.capstone.service;

import com.eureka.capstone.domain.product.ReleaseVersion;
import com.eureka.capstone.domain.product.SubSystem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SubsystemService {

    SubSystem createSubSystem(SubSystem releaseVersion);

    List<SubSystem> getSubsystemsByProductId(long id);

    SubSystem getSubSystemById(long id);

    SubSystem extractSubSystemFromRequest(HttpServletRequest request);

    void updateSubSystem(SubSystem urv);

    void deleteSubSystemById(long id);

}
