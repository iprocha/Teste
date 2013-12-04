-- Antes de executar este programa, executar Pivot em educ_tab13s:
--    Criar tabela_resultado
--    No prompt de comando do terminal, entrar no diretório: cd c:\K\D\Dados\MySQL\Pivot\
--    E executar: java -jar Pivot.jar (informar tabela educ_tab13s e tabela_resultado)

DROP TABLE IF EXISTS im_lattes.iae_tab14s;

ALTER TABLE im_lattes.tabela_resultado 
RENAME TO  im_lattes.iae_tab14s ;

-- STEMS

DROP TABLE IF EXISTS im_lattes.iae_tab15s;

CREATE  TABLE im_lattes.iae_tab15s (
  `id_pesquisador1` VARCHAR(16) NOT NULL,
  `stems` VARCHAR(25) NOT NULL,
  `occur` INT(11));

INSERT im_lattes.iae_tab15s
SELECT id AS id_pesquisador1, words AS stems, value AS occur 
FROM im_lattes.iae_tab14s
ORDER BY id_pesquisador1, stems;


