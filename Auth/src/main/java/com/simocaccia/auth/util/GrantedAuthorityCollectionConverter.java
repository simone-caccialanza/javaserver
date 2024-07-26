package com.simocaccia.auth.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter
public class GrantedAuthorityCollectionConverter implements AttributeConverter<Set<GrantedAuthority>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(Set<GrantedAuthority> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public Set<GrantedAuthority> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Set.of();
        }
        return Stream.of(dbData.split(SPLIT_CHAR))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
