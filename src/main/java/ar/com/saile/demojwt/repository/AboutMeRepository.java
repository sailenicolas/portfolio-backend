package ar.com.saile.demojwt.repository;

import ar.com.saile.demojwt.domain.AppAboutMe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutMeRepository extends JpaRepository<AppAboutMe, Long> {

}
