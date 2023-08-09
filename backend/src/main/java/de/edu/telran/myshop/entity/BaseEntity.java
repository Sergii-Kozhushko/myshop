package de.edu.telran.myshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * An abstract base entity class providing common properties for other entity classes.
 */
@Data
@MappedSuperclass // Allows entity classes to correctly inherit properties from this parent class.
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    /**
     * Executed before persisting the entity to the database.
     * Sets the 'createdAt' and 'updatedAt' timestamps if not already set.
     */
    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) {
            createdAt = new Date();
        }
        if (this.updatedAt == null) {
            updatedAt = new Date();
        }
    }

    /**
     * Executed before updating the entity in the database.
     * Updates the 'updatedAt' timestamp.
     */
    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }
}
