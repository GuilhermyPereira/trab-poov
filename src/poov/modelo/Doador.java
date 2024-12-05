package poov.modelo;

public class Doador {
        private Long codigo;
        private String nome;
        private String cpf;
        private String contato;
        private boolean tipoERHCorretos;
        private RH rh;
        private TipoSanguineo tipoSanguineo;
        private Situacao situacao;

        public Long getCodigo() {
            return codigo;
        }
        public void setCodigo(Long codigo) {
            this.codigo = codigo;
        }
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public String getCpf() {
            return cpf;
        }
        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
        public String getContato() {
            return contato;
        }
        public void setContato(String contato) {
            this.contato = contato;
        }
        public boolean isTipoERHCorretos() {
            return tipoERHCorretos;
        }
        public void setTipoERHCorretos(boolean tipoERHCorretos) {
            this.tipoERHCorretos = tipoERHCorretos;
        }

        public RH getRh() {
            return rh;
        }

        public TipoSanguineo getTipoSanguineo() {
            return tipoSanguineo;
        }

        public TipoSanguineo setTipoSanguineo(TipoSanguineo tipoSanguineo) {
            this.tipoSanguineo = tipoSanguineo;
            return this.tipoSanguineo;
        }

        public RH setRh(RH rh) {
            this.rh = rh;
            return rh;
        }

        public Situacao getSituacao() {
            return situacao;
        }

        public Situacao setSituacao(Situacao situacao) {
            this.situacao = situacao;
            return this.situacao;
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
            result = prime * result + ((nome == null) ? 0 : nome.hashCode());
            result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
            result = prime * result + ((contato == null) ? 0 : contato.hashCode());
            result = prime * result + (tipoERHCorretos ? 1231 : 1237);
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
            Doador other = (Doador) obj;
            if (codigo == null) {
                if (other.codigo != null)
                    return false;
            } else if (!codigo.equals(other.codigo))
                return false;
            if (nome == null) {
                if (other.nome != null)
                    return false;
            } else if (!nome.equals(other.nome))
                return false;
            if (cpf == null) {
                if (other.cpf != null)
                    return false;
            } else if (!cpf.equals(other.cpf))
                return false;
            if (contato == null) {
                if (other.contato != null)
                    return false;
            } else if (!contato.equals(other.contato))
                return false;
            if (tipoERHCorretos != other.tipoERHCorretos)
                return false;
            return true;
        }
        
       
        public Doador (String nome, String cpf, String contato) {
            this.nome = nome;
            this.cpf = cpf;
            this.contato = contato;
            this.tipoERHCorretos = false;
            this.situacao = Situacao.ATIVO;
            this.tipoSanguineo = TipoSanguineo.DESCONHECIDO;
            this.rh = RH.DESCONHECIDO;
        }

        public Doador (Long codigo, String nome, String cpf, String contato, boolean tipoERHCorretos, RH rh, TipoSanguineo tipoSanguineo, Situacao situacao) {
            this.codigo = codigo;
            this.nome = nome;
            this.cpf = cpf;
            this.contato = contato;
            this.tipoERHCorretos = tipoERHCorretos;
            this.rh = rh;
            this.tipoSanguineo = tipoSanguineo;
            this.situacao = situacao;
        }        

        @Override
        public String toString() {
            return "\n\nCodigo = " + codigo + ", \nnome = " + nome + ", \ncpf = " + cpf + ", \ncontato = " + contato + ", \ntipoERHCorretos = " + tipoERHCorretos + ", \nrh = " + rh + ", \ntipoSanguineo = " + tipoSanguineo + ", \nsituacao = " + situacao;
        }

        public Doador() {
        }

}
