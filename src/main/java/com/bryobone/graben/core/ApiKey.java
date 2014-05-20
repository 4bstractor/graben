package com.bryobone.graben.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by jarvis on 5/20/14.
 */

@Entity
@Table(
        name = "api_keys",
        uniqueConstraints = @UniqueConstraint(columnNames = {"key"})
)

public class ApiKey {
}
