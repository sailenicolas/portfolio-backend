package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<AppExperience, Long> {

}
