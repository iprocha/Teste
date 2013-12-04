-- STEMS

DROP TABLE IF EXISTS im_lattes.iae_tab05s;

CREATE  TABLE im_lattes.iae_tab05s (
  `seq1` INT(11) NOT NULL,
  `seq2` INT(11) NOT NULL,
  `similarity` DOUBLE NOT NULL,
  PRIMARY KEY (`seq1`, `seq2`));

INSERT im_lattes.iae_tab05s
SELECT FIRST_ID AS seq1, SECOND_ID AS seq2, SIMILARITY AS similarity
FROM im_lattes.iae_tab04s
WHERE FIRST_ID <> SECOND_ID AND SIMILARITY > 0;

DROP TABLE IF EXISTS im_lattes.iae_tab06s;

CREATE  TABLE im_lattes.iae_tab06s (
  `seq` INT(11) NOT NULL,
  `id_pesquisador` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`seq`, `id_pesquisador`));

INSERT im_lattes.iae_tab06s
SELECT @row := @row+1 as seq, id_pesquisador
FROM im_lattes.iae_tab03s A, (SELECT @row := 0) r;




-- STEMS

DROP TABLE IF EXISTS im_lattes.iae_tab07s;

CREATE  TABLE im_lattes.iae_tab07s (
  `id_pesquisador1` VARCHAR(16) NOT NULL,
  `seq2` INT(11) NOT NULL,
  `similarity` DOUBLE NOT NULL,
  PRIMARY KEY (`id_pesquisador1`, `seq2`));

INSERT im_lattes.iae_tab07s
SELECT id_pesquisador AS id_pesquisador1, seq2, similarity 
FROM im_lattes.iae_tab05s A, im_lattes.iae_tab06s B
WHERE seq = seq1;

DROP TABLE IF EXISTS im_lattes.iae_tab08s;

CREATE  TABLE im_lattes.iae_tab08s (
  `id_pesquisador1` VARCHAR(16) NOT NULL,
  `id_pesquisador2` VARCHAR(16) NOT NULL,
  `similarity_s` DOUBLE NOT NULL,
  PRIMARY KEY (`id_pesquisador1`, `id_pesquisador2`));

INSERT im_lattes.iae_tab08s
SELECT id_pesquisador1, id_pesquisador AS id_pesquisador2, 
       similarity AS similarity_s
FROM im_lattes.iae_tab06s, im_lattes.iae_tab07s
WHERE seq = seq2;


DROP TABLE IF EXISTS im_lattes.iae_tab09s;

CREATE  TABLE im_lattes.iae_tab09s (
  `id_pesquisador1` VARCHAR(16) NOT NULL,
  `id_pesquisador2` VARCHAR(16) NOT NULL,
  `similarity_s` DOUBLE NOT NULL,
  PRIMARY KEY (`id_pesquisador1`, `id_pesquisador2`));

INSERT im_lattes.iae_tab09s
SELECT * FROM im_lattes.iae_tab08s
ORDER BY id_pesquisador1, id_pesquisador2;


