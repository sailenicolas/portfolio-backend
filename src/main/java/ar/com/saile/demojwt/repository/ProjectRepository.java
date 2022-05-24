package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<AppProject, Long> {

}
