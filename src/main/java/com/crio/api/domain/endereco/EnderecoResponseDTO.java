package com.crio.api.domain.endereco;

public record EnderecoResponseDTO(
        long id,
        String city,
        String uf
) {
    public Object getUf() {
        return null;
    }

    public Object getCity() {
        return null;
    }
}
