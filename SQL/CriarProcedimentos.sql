

-- Criar uma função para inserir uma linha em histórico
CREATE FUNCTION inserir_historico(cliente_id BIGINT, livro_id BIGINT, periodo INT, periodo_format CHAR) RETURNS BOOLEAN AS $$
BEGIN
-- Checa se tem o livro solicitado disponivel
	IF (SELECT livros_quantidade.Quantidade_disponivel FROM livros_quantidade WHERE livros_quantidade.Livro_id = livro_id) > 0 THEN
  	INSERT INTO historico (cliente_id, Livro_id, Data_emp, Periodo, Periodo_format, Status)
		VALUES (cliente_id, livro_id, CURRENT_DATE, periodo, periodo_format, 1);
  	
		UPDATE livros_quantidade
		SET Quantidade_disponivel = Quantidade_disponivel-1
		WHERE livros_quantidade.Livro_id = livro_id;
		
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;

END;
$$ LANGUAGE plpgsql;

-- Criar uma função para atualizar o status no historico
CREATE FUNCTION devolver_livro (cliente_id BIGINT, livro_id BIGINT ) RETURNS VOID AS $$
BEGIN
-- muda o status no historico
	UPDATE historico
	SET Status = 2
	WHERE historico.cliente_id = cliente_id and historico.livro_id = livro_id;
-- atualiza a quantidade disponivel
	UPDATE livros_quantidade
	SET Quantidade_disponivel = Quantidade_disponivel+1
	WHERE livros_quantidade.Livro_id = livro_id;

END;
$$ LANGUAGE plpgsql;
