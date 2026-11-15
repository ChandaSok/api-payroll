package com.istad.chanda.payroll.common;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@NoRepositoryBean
public interface CrudRepository<E extends BaseEntity> extends JpaRepository<E, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from #{#entityName} e where e.id = :id")
    Optional<E> findLockedById(@Param("id") String id);
}
