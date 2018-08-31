package com.cegeka.project.project.spec;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectSpecificationRepository extends JpaRepository<ProjectSpecification, UUID> {

    Collection<ProjectSpecification> findByProjectId(UUID projectId);
}
