package Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String nombre;
    private String pass;
    private String correo;
    private String codigo;
    private int activado;
}
