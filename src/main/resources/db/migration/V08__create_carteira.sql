CREATE TABLE carteira(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_proposta BIGINT(20),
	resultado VARCHAR(200),
	id_carteira VARCHAR(200),
	criacao datetime not null
) ENGINE=InnoDB DEFAULT charset=utf8;

ALTER TABLE `carteira` ADD CONSTRAINT `fk_codigo_cartao_carteira` FOREIGN KEY ( `codigo_proposta` ) 
REFERENCES `proposta` ( `id` );