package com.crio.api.domain.usuario;

public record LoginResponse(String accessToken, Long expiresIn) {
}
