package com.bryobone.graben.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by jarvis on 5/20/14.
 */

@Entity
@Table(
    name = "access_tokens",
    uniqueConstraints = @UniqueConstraint(columnNames = {"token"})
)

// Join between ApiKey and a User

public class AccessToken {
}
