package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<AppProject, Long> {

    Optional<AppProject> findById(Long id);

}
