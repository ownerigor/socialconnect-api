package br.com.socialconnect.api.produtos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstoqueNaoNegativoValidator implements ConstraintValidator<EstoqueNaoNegativo, Integer> {

    @Override
    public boolean isValid(Integer estoque, ConstraintValidatorContext context) {
        return estoque == null || estoque >= 0;
    }
}
