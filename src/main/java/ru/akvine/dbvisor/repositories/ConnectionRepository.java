package ru.akvine.dbvisor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.akvine.dbvisor.repositories.entities.ConnectionEntity;

import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<ConnectionEntity, Long> {
    @Query("from ConnectionEntity ce where " +
            "ce.connectionName = :name " +
            "and " +
            "ce.deleted = false")
    Optional<ConnectionEntity> findByConnectionName(@Param("name") String connectionName);
}
