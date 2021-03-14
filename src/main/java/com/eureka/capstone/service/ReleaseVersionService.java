package com.eureka.capstone.service;

import com.eureka.capstone.domain.product.ReleaseVersion;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ReleaseVersionService {

    ReleaseVersion createReleaseVersion(ReleaseVersion releaseVersion);

    List<ReleaseVersion> getReleaseVersionsByProductId(long id);

    ReleaseVersion getReleaseVersionById(long id);

    ReleaseVersion extractReleaseFromRequest(HttpServletRequest request);

    void updateReleaseVersion(ReleaseVersion urv);

    void deleteReleaseVersionById(long id);

}
