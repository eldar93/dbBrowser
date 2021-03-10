package com.dbbrowser.repository.connection;

import com.dbbrowser.entity.connection.ConnectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends CrudRepository<ConnectionEntity, String> {

}
