package com.eureka.capstone.service.product;

import com.eureka.capstone.domain.product.ReleaseVersion;
import com.eureka.capstone.exception.notfound.NotFoundException;
import com.eureka.capstone.repository.product.ReleaseVersionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReleaseVersionServiceImpl implements ReleaseVersionService {

    private final ReleaseVersionRepository repository;

    @Override
    public ReleaseVersion createReleaseVersion(ReleaseVersion releaseVersion) {
        return repository.save(releaseVersion);
    }

    @Override
    public List<ReleaseVersion> getReleaseVersionsByProductId(long id) {
        return repository.findByProduct_Id(id);
    }

    @Override
    public ReleaseVersion getReleaseVersionById(long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ReleaseVersion extractReleaseFromRequest(HttpServletRequest request) {
        var updatedReleaseVersion = new ReleaseVersion();

        updatedReleaseVersion.setId(Long.parseLong(request.getParameter("id")));
        updatedReleaseVersion.setVersion(request.getParameter("editVersion"));
        updatedReleaseVersion.setDescription(request.getParameter("editDescription"));
        updatedReleaseVersion.setStartDate(LocalDate.parse(request.getParameter("edit-date")));

        return updatedReleaseVersion;
    }

    @Override
    public void updateReleaseVersion(ReleaseVersion urv) {
        var rv = getReleaseVersionById(urv.getId());

        rv.setVersion(urv.getVersion());
        rv.setDescription(urv.getDescription());
        rv.setStartDate(urv.getStartDate());
        rv.setProduct(urv.getProduct());

        repository.save(rv);
    }

    @Override
    public void deleteReleaseVersionById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ReleaseVersion> getAllReleaseVersions() {
        return repository.findAll();
    }

}
