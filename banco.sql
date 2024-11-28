
CREATE TABLE IF NOT EXISTS public.doador
(
	codigo bigserial,
    nome text,
    cpf text,
    contato text,
    tipoerhcorretos boolean,
    rh text,
    tiposanguineo text,
    situacao text,
    PRIMARY KEY (codigo)
);

