-- A tabela doacoes ja e criada e populada pela migration V2.
CREATE INDEX idx_doacoes_data ON doacoes(data_doacao);
CREATE INDEX idx_doacoes_tipo ON doacoes(tipo);
