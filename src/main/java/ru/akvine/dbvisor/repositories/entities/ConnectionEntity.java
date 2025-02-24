package ru.akvine.dbvisor.repositories.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.akvine.dbvisor.enums.DatabaseType;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "CONNECTION_ENTITY")
public class ConnectionEntity extends BaseEntity<Long> {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connectionEntitySeq")
    @SequenceGenerator(name = "connectionEntitySeq", sequenceName = "SEQ_CONNECTION_ENTITY", allocationSize = 1000)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "CONNECTION_NAME", nullable = false, unique = true)
    private String connectionName;

    @Nullable
    @Column(name = "DATABASE_NAME")
    private String databaseName;

    @Nullable
    @Column(name = "SCHEMA")
    private String schema;

    @NotNull
    @Column(name = "HOST", nullable = false)
    private String host;

    @NotNull
    @Column(name = "PORT", nullable = false)
    private String port;

    @ToString.Exclude
    @Column(name = "USERNAME")
    private String username;

    @ToString.Exclude
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "DATABASE_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private DatabaseType databaseType;
}
