package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppEducation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<AppEducation, Long> {

}
