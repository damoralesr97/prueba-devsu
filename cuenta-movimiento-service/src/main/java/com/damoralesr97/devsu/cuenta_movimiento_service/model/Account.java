package com.damoralesr97.devsu.cuenta_movimiento_service.model;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.AccountTypeEnum;
import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.MovementTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "T_ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACT_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ACT_NUMBER", unique = true, nullable = false, updatable = false)
    private String accountNumber;

    @Column(name = "ACT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum typeAccount;

    @Column(name = "ACT_INITIAL_BALANCE", nullable = false, updatable = false)
    @Min(value = 1,message = "El saldo inicial debe ser mayor o igual 1")
    private BigDecimal initialBalance;

    @Column(name = "ACT_STATUS")
    private Boolean status;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Movement> movements;

    @Column(nullable = false, updatable = false)
    private Long clientId;

    public BigDecimal calculateBalance() {
        return movements.stream().map(Movement::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
