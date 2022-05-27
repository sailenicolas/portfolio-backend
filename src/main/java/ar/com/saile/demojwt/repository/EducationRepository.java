package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<AppEducation, Long> {

}
