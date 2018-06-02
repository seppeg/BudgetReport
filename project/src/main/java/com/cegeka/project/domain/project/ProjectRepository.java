package com.cegeka.project.domain.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Optional<Project> findByWorkOrdersWorkOrderContains(String workorder);

    boolean existsProjectByName(String name);
}
