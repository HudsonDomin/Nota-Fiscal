CREATE DATABASE db_nota;
USE db_nota;

-- CLIENTE
CREATE TABLE CLIENTE_01 (
                            A01_codigo INT AUTO_INCREMENT PRIMARY KEY,
                            A01_nome VARCHAR(50) NOT NULL,
                            A01_endereco VARCHAR(50) NOT NULL,
                            A01_telefone CHAR(11) NOT NULL,
                            A01_cpf CHAR(11) NOT NULL UNIQUE,
                            A01_credito DECIMAL(10,2) NOT NULL,
                            A01_ativo BOOLEAN DEFAULT TRUE
);

DELIMITER $$

CREATE PROCEDURE Proc_InsCliente(
    IN V_A01_nome VARCHAR(50),
    IN V_A01_endereco VARCHAR(50),
    IN V_A01_telefone CHAR(11),
    IN V_A01_cpf CHAR(11),
    IN V_A01_credito DECIMAL(10,2)
)
BEGIN
INSERT INTO CLIENTE_01 (A01_nome, A01_endereco, A01_telefone, A01_cpf, A01_credito)
VALUES (V_A01_nome, V_A01_endereco, V_A01_telefone, V_A01_cpf, V_A01_credito);
END$$

CREATE PROCEDURE Proc_UpdCliente(
    IN V_A01_codigo INT,
    IN V_A01_nome VARCHAR(50),
    IN V_A01_endereco VARCHAR(50),
    IN V_A01_telefone CHAR(11),
    IN V_A01_cpf CHAR(11),
    IN V_A01_credito DECIMAL(10,2)
)
BEGIN
UPDATE CLIENTE_01 SET
                      A01_nome = V_A01_nome,
                      A01_endereco = V_A01_endereco,
                      A01_telefone = V_A01_telefone,
                      A01_cpf = V_A01_cpf,
                      A01_credito = V_A01_credito
WHERE A01_codigo = V_A01_codigo;
END$$

CREATE PROCEDURE Proc_DelCliente(IN V_A01_codigo INT)
BEGIN
UPDATE CLIENTE_01 SET A01_ativo = FALSE WHERE A01_codigo = V_A01_codigo;
END$$

DELIMITER ;

-- PEDIDO
CREATE TABLE PEDIDO_02 (
                           A02_codigo INT AUTO_INCREMENT NOT NULL,
                           A02_data DATETIME NOT NULL,
                           A02_valorTotal DECIMAL(10,2) NOT NULL,
                           A01_codigo INT NOT NULL,
                           PRIMARY KEY (A02_codigo),
                           FOREIGN KEY (A01_codigo) REFERENCES CLIENTE_01 (A01_codigo)
);
SELECT * FROM CLIENTE_01;
DELIMITER $$

CREATE PROCEDURE Proc_InsPedido(
    IN V_A02_data DATETIME,
    IN V_A02_valorTotal DECIMAL(10,2),
    IN V_A01_codigo INT
)
BEGIN
INSERT INTO PEDIDO_02 (A02_data, A02_valorTotal, A01_codigo)
VALUES (V_A02_data, V_A02_valorTotal, V_A01_codigo);
END$$

CREATE PROCEDURE Proc_DelPedido(IN V_A02_codigo INT)
BEGIN
DELETE FROM PEDIDO_02 WHERE A02_codigo = V_A02_codigo;
END$$

CREATE PROCEDURE Proc_UpdPedido(
    IN V_A02_codigo INT,
    IN V_A02_data DATETIME,
    IN V_A02_valorTotal DECIMAL(10,2)
)
BEGIN
UPDATE PEDIDO_02
SET A02_data = V_A02_data,
    A02_valorTotal = V_A02_valorTotal
WHERE A02_codigo = V_A02_codigo;
END$$

DELIMITER ;

-- PRODUTO
CREATE TABLE PRODUTO_03 (
                            A03_codigo INT AUTO_INCREMENT PRIMARY KEY,
                            A03_descricao VARCHAR(50) NOT NULL,
                            A03_valorUnitario DECIMAL(10,2) NOT NULL,
                            A03_estoque INT NOT NULL
);

DELIMITER $$
CREATE PROCEDURE Proc_InsProduto(
    IN V_A03_descricao VARCHAR(50),
    IN V_A03_valorUnitario DECIMAL(10,2),
    IN V_A03_estoque INT
)
BEGIN
INSERT INTO PRODUTO_03 (A03_descricao, A03_valorUnitario, A03_estoque)
VALUES (V_A03_descricao, V_A03_valorUnitario, V_A03_estoque);
END$$

CREATE PROCEDURE Proc_UpdProduto(
    IN V_A03_codigo INT,
    IN V_A03_descricao VARCHAR(50),
    IN V_A03_valorUnitario DECIMAL(10,2),
    IN V_A03_estoque INT
)
BEGIN
UPDATE PRODUTO_03 SET
                      A03_descricao = V_A03_descricao,
                      A03_valorUnitario = V_A03_valorUnitario,
                      A03_estoque = V_A03_estoque
WHERE A03_codigo = V_A03_codigo;
END$$

CREATE PROCEDURE Proc_DelProduto(IN V_A03_codigo INT)
BEGIN
UPDATE PRODUTO_03
SET A03_estoque = 0
WHERE A03_codigo = V_A03_codigo;
END$$

DELIMITER ;

-- ITEM
CREATE TABLE ITEM_04 (
                         A04_codigo INT AUTO_INCREMENT,
                         A03_codigo INT,
                         A02_codigo INT,
                         A04_quantidade INT,
                         A04_valorItem DECIMAL(10,2),
                         PRIMARY KEY (A04_codigo),
                         FOREIGN KEY (A03_codigo) REFERENCES PRODUTO_03 (A03_codigo),
                         FOREIGN KEY (A02_codigo) REFERENCES PEDIDO_02 (A02_codigo) ON DELETE CASCADE
);

DELIMITER $$

CREATE PROCEDURE Proc_InsItem(
    IN V_A03_codigo INT,
    IN V_A02_codigo INT,
    IN V_A04_quantidade INT,
    IN V_A04_valorItem DECIMAL(10,2)
)
BEGIN
INSERT INTO ITEM_04 (A03_codigo, A02_codigo, A04_quantidade, A04_valorItem)
VALUES (V_A03_codigo, V_A02_codigo, V_A04_quantidade, V_A04_valorItem);
END$$

CREATE PROCEDURE Proc_UpdItem(
    IN V_A04_codigo INT,
    IN V_A04_quantidade INT,
    IN V_A04_valorItem DECIMAL(10,2)
)
BEGIN
UPDATE ITEM_04
SET A04_quantidade = V_A04_quantidade,
    A04_valorItem = V_A04_valorItem
WHERE A04_codigo = V_A04_codigo;
END$$

CREATE PROCEDURE Proc_DelItem(IN V_A04_codigo INT)
BEGIN
DELETE FROM ITEM_04 WHERE A04_codigo = V_A04_codigo;
END$$

CREATE PROCEDURE Proc_UpdValorTotalPedido(IN V_A02_codigo INT)
BEGIN
UPDATE PEDIDO_02
    JOIN (
    SELECT A02_codigo, SUM(A04_valorItem) AS totalPedido
    FROM ITEM_04
    WHERE A02_codigo = V_A02_codigo
    GROUP BY A02_codigo
    ) i ON PEDIDO_02.A02_codigo = i.A02_codigo
    SET PEDIDO_02.A02_valorTotal = i.totalPedido;
END$$

DELIMITER ;