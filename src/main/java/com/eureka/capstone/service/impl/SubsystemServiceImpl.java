package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.product.ReleaseVersion;
import com.eureka.capstone.domain.product.SubSystem;
import com.eureka.capstone.exception.notfound.NotFoundException;
import com.eureka.capstone.repository.SubsystemRepository;
import com.eureka.capstone.service.SubsystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubsystemServiceImpl implements SubsystemService {

    private final SubsystemRepository repository;

    @Override
    public SubSystem createSubSystem(SubSystem subSystem) {
        return repository.save(subSystem);
    }

    @Override
    public List<SubSystem> getSubsystemsByProductId(long id) {
        return repository.findByProduct_Id(id);
    }

    @Override
    public SubSystem getSubSystemById(long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public SubSystem extractSubSystemFromRequest(HttpServletRequest request) {
        var updatedSubsystem = new SubSystem();
        updatedSubsystem.setId(Long.parseLong(request.getParameter("id")));
        updatedSubsystem.setName(request.getParameter("edit-name"));
        updatedSubsystem.setShortName(request.getParameter("edit-shortName"));
        updatedSubsystem.setDescription(request.getParameter("edit-description"));
        return updatedSubsystem;
    }

    @Override
    public void updateSubSystem(SubSystem us) {
        var s = getSubSystemById(us.getId());
        s.setDescription(us.getDescription());
        s.setName(us.getName());
        s.setShortName(us.getShortName());
        s.setProduct(us.getProduct());
        repository.save(s);
    }

    @Override
    public void deleteSubSystemById(long id) {
        repository.deleteById(id);
    }

}
