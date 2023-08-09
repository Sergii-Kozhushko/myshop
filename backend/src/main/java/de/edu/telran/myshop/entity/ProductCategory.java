package de.edu.telran.myshop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class representing a product category.
 * We don't extend from BaseEntity here because Id has another type
 */
@Entity
@Table(name = "product_category")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "parent_category_id")
    private Integer parentId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @EqualsAndHashCode.Exclude
    @Column(name = "is_active")
    private Boolean active = true;

    // Constructors for tests
    public ProductCategory(String name, Integer parentId, Boolean active) {
        this.name = name;
        this.parentId = parentId;
        this.active = active;
    }

    public ProductCategory(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public ProductCategory(String name) {
        this.name = name;
    }
}
