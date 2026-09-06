package com.istad.chanda.payroll.features.componenttype;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "component_type")
public class ComponentType {
    @Id
    private String id; // Keycloak User ID
}
