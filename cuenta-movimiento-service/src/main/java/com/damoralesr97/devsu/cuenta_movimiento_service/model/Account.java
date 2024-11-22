package com.damoralesr97.devsu.cuenta_movimiento_service.model;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.AccountTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "[0-9]+", message = "El numero de cuenta solo debe contener numeros")
    @NotNull(message = "El numero de cuenta es requerido")
    private String accountNumber;

    @Column(name = "ACT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de cuenta es requerido")
    private AccountTypeEnum typeAccount;

    @Column(name = "ACT_INITIAL_BALANCE", nullable = false, updatable = false)
    @Min(value = 1,message = "El saldo inicial debe ser mayor o igual 1")
    @NotNull(message = "El monto inicial es requerido")
    private BigDecimal initialBalance;

    @Column(name = "ACT_STATUS")
    private Boolean status;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Movement> movements;

    @Column(nullable = false, updatable = false)
    private Long clientId;

    public BigDecimal calculateBalance() {
        return movements.stream().map(Movement::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
