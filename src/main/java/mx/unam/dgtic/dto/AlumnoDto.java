package mx.unam.dgtic.dto;

public class AlumnoDto {
    private String matricula;
    private String nombre;
    private String paterno;
    private String fnac;
    private double estatura;
    private String estado;

    public AlumnoDto(){}

    public AlumnoDto(String matricula, String nombre, String paterno, String fnac, double estatura, String estado) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.paterno = paterno; 
        this.fnac = fnac;
        this.estatura = estatura;
        this.estado = estado;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public String getFnac() {
        return fnac;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "AlumnoDto{" +
            "matricula='" + matricula + '\'' +
            ", nombre='" + nombre + '\'' +
            ", paterno='" + paterno + '\'' +
            ", fnac='" + fnac + '\'' +
            ", estatura=" + estatura +
            ", estado='" + estado + '\'' +
            '}';
    }

}
