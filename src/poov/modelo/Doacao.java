package poov.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Doacao {

    private long codigo;
    private LocalDate data;
    private LocalTime hora;
    private double volume;
    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Doador getDoador() {
        return null;
    }

    public Doador setDoador(Doador doador) {
        return doador;
    }

    public Situacao getSituacao() {
        return Situacao.ATIVO;
    }

    public Situacao setSituacao(Situacao situacao) {
        return situacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((hora == null) ? 0 : hora.hashCode());
        long temp;
        temp = Double.doubleToLongBits(volume);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Doacao other = (Doacao) obj;
        if (codigo != other.codigo)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (hora == null) {
            if (other.hora != null)
                return false;
        } else if (!hora.equals(other.hora))
            return false;
        if (Double.doubleToLongBits(volume) != Double.doubleToLongBits(other.volume))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Doacao [codigo=" + codigo + ", data=" + data + ", hora=" + hora + ", volume=" + volume + "]";
    }

        
}
