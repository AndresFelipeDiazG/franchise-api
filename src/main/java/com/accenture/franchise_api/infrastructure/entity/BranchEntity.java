package com.accenture.franchise_api.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("branches")
public class BranchEntity {
    @Id
    private Long id;
    @Column("franchise_id")
    private Long franchiseID;
    private String name;

    public BranchEntity() {
    }

    public BranchEntity(Long id, Long franchiseID, String name) {

        this.id = id;
        this.franchiseID = franchiseID;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFranchiseID() {
        return franchiseID;
    }

    public void setFranchiseID(Long franchiseID) {
        this.franchiseID = franchiseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
