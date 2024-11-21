package com.damoralesr97.devsu.cuenta_movimiento_service.dto.client.response;

import com.damoralesr97.devsu.cuenta_movimiento_service.utils.enums.GenreEnum;
import lombok.Data;

@Data
public class ClientResponse {

    private Long id;
    private String name;
    private GenreEnum genre;
    private int age;
    private String dni;
    private String address;
    private String phoneNumber;
    private Boolean status;

}
