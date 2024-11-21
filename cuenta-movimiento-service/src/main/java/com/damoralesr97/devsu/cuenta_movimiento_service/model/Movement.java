package com.damoralesr97.devsu.cuenta_movimiento_service.model;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.MovementTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "T_MOVEMENTS")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOV_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "MOV_DATE", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Timestamp movementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOV_TYPE", nullable = false, updatable = false)
    private MovementTypeEnum movementType;

    @Column(name = "MOV_VALUE", nullable = false, updatable = false)
    private BigDecimal value;

    @Column(name = "MOV_BALANCE", nullable = false, updatable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOV_ACT_ID", referencedColumnName = "ACT_ID", nullable = false, updatable = false)
    @JsonIgnore
    private Account account;

}
