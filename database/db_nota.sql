CREATE DATABASE db_nota;
USE db_nota;

/*
Composto por 4 entidades principais
Cliente, Pedido, Produto e Item

Relacionadas de forma lógica
Um cliente pode ter vários pedidos
Um pedido pode ter vários itens
Cada item se refere a um produto específico
*/

/* CLIENTE */
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
    UPDATE CLIENTE_01
    SET A01_nome = V_A01_nome,
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

/* Soft delete preserva histórico: A01_ativo = FALSE */

DELIMITER ;

/* PEDIDO */
CREATE TABLE PEDIDO_02 (
    A02_codigo INT AUTO_INCREMENT PRIMARY KEY,
    A02_data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    A02_valorTotal DECIMAL(10,2) NOT NULL,
    A01_codigo INT NOT NULL,
    FOREIGN KEY (A01_codigo) REFERENCES CLIENTE_01 (A01_codigo)
);

DELIMITER $$

CREATE PROCEDURE Proc_InsPedido(
    IN V_A02_valorTotal DECIMAL(10,2),
    IN V_A01_codigo INT
)
BEGIN
    INSERT INTO PEDIDO_02 (A02_valorTotal, A01_codigo)
    VALUES (V_A02_valorTotal, V_A01_codigo);
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

/* PRODUTO */
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
    UPDATE PRODUTO_03
    SET A03_descricao = V_A03_descricao,
        A03_valorUnitario = V_A03_valorUnitario,
        A03_estoque = V_A03_estoque
    WHERE A03_codigo = V_A03_codigo;
END$$

CREATE PROCEDURE Proc_DelProduto(IN V_A03_codigo INT)
BEGIN
    UPDATE PRODUTO_03 SET A03_estoque = 0 WHERE A03_codigo = V_A03_codigo;
END$$

DELIMITER ;

/* ITEM */
CREATE TABLE ITEM_04 (
    A04_codigo INT AUTO_INCREMENT PRIMARY KEY,
    A03_codigo INT,
    A02_codigo INT,
    A04_quantidade INT,
    A04_valorItem DECIMAL(10,2),
    FOREIGN KEY (A03_codigo) REFERENCES PRODUTO_03 (A03_codigo),
    FOREIGN KEY (A02_codigo) REFERENCES PEDIDO_02 (A02_codigo) ON DELETE CASCADE
);

/* ON DELETE CASCADE remove itens quando o pedido é excluído */

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

CREATE PROCEDURE Proc_UpdPedidoValorTotal(IN V_A02_codigo INT)
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

/* Procedure adicional para recalcular o valor total do pedido */

-- TRIGGERS

CREATE TRIGGER Trg_AntesInsCliente
BEFORE INSERT ON CLIENTE_01
FOR EACH ROW
BEGIN
    IF NEW.A01_telefone NOT REGEXP '^[0-9]{11}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Telefone inválido';
    END IF;
    IF NEW.A01_cpf NOT REGEXP '^[0-9]{11}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'CPF inválido';
    END IF;
END$$

CREATE TRIGGER Trg_AntesUpdCliente
BEFORE UPDATE ON CLIENTE_01
FOR EACH ROW
BEGIN
    IF NEW.A01_telefone NOT REGEXP '^[0-9]{11}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Telefone inválido';
    END IF;
    IF NEW.A01_cpf NOT REGEXP '^[0-9]{11}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'CPF inválido';
    END IF;
END$$

CREATE TRIGGER Trg_AntesInsPedido
BEFORE INSERT ON PEDIDO_02
FOR EACH ROW
BEGIN
    DECLARE V_A01_credito DECIMAL(10,2);
    DECLARE V_A01_ativo BOOLEAN;
    SELECT A01_credito, A01_ativo INTO V_A01_credito, V_A01_ativo
      FROM CLIENTE_01
     WHERE A01_codigo = NEW.A01_codigo;
    IF V_A01_ativo = FALSE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente inativo';
    END IF;
    IF V_A01_credito < NEW.A02_valorTotal THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Crédito insuficiente';
    END IF;
END$$

CREATE TRIGGER Trg_AntesUpdPedido
BEFORE UPDATE ON PEDIDO_02
FOR EACH ROW
BEGIN
    DECLARE V_A01_credito DECIMAL(10,2);
    DECLARE V_A01_ativo BOOLEAN;
    SELECT A01_credito, A01_ativo INTO V_A01_credito, V_A01_ativo
      FROM CLIENTE_01
     WHERE A01_codigo = OLD.A01_codigo;
    IF V_A01_ativo = FALSE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente inativo';
    END IF;
    IF V_A01_credito + OLD.A02_valorTotal < NEW.A02_valorTotal THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Crédito insuficiente';
    END IF;
END$$

CREATE TRIGGER Trg_DepoisInsPedido
AFTER INSERT ON PEDIDO_02
FOR EACH ROW
BEGIN
    UPDATE CLIENTE_01
    SET A01_credito = A01_credito - NEW.A02_valorTotal
    WHERE A01_codigo = NEW.A01_codigo;
END$$

CREATE TRIGGER Trg_DepoisDelPedido
AFTER DELETE ON PEDIDO_02
FOR EACH ROW
BEGIN
    UPDATE CLIENTE_01
    SET A01_credito = A01_credito + OLD.A02_valorTotal
    WHERE A01_codigo = OLD.A01_codigo;
END$$

CREATE TRIGGER Trg_AntesInsItem
BEFORE INSERT ON ITEM_04
FOR EACH ROW
BEGIN
    DECLARE V_A03_estoque INT;
    IF NEW.A04_quantidade <= 0 OR NEW.A04_valorItem < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade e valor devem ser positivos';
    END IF;
    SELECT A03_estoque INTO V_A03_estoque
      FROM PRODUTO_03
     WHERE A03_codigo = NEW.A03_codigo;
    IF V_A03_estoque < NEW.A04_quantidade THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Sem estoque suficiente';
    END IF;
END$$

CREATE TRIGGER Trg_AntesUpdItem
BEFORE UPDATE ON ITEM_04
FOR EACH ROW
BEGIN
    IF NEW.A04_quantidade <= 0 OR NEW.A04_valorItem < 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantidade e valor devem ser positivos';
    END IF;
END$$

CREATE TRIGGER Trg_DepoisInsItem
AFTER INSERT ON ITEM_04
FOR EACH ROW
BEGIN
    CALL Proc_UpdPedidoValorTotal(NEW.A02_codigo);
END$$

CREATE TRIGGER Trg_DepoisUpdItem
AFTER UPDATE ON ITEM_04
FOR EACH ROW
BEGIN
    CALL Proc_UpdPedidoValorTotal(NEW.A02_codigo);
END$$

CREATE TRIGGER Trg_DepoisDelItem
AFTER DELETE ON ITEM_04
FOR EACH ROW
BEGIN
    CALL Proc_UpdPedidoValorTotal(OLD.A02_codigo);
END$$

DELIMITER ;
