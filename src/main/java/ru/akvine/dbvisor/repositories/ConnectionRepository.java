package ru.akvine.dbvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akvine.dbvisor.repositories.entities.ConnectionEntity;

@Repository
public interface ConnectionRepository extends JpaRepository<ConnectionEntity, Long> {

}
