package io.bmeurant.bookordermanager.domain.exception;

import org.springframework.util.Assert;

public class ValidationException extends DomainException {
    private final Class<?> domainClass;

    public ValidationException(String message, Class<?> domainClass) {
        super(message);
        Assert.notNull(domainClass, "Domain class must not be null");
        this.domainClass = domainClass;
    }

    public ValidationException(String message, Throwable cause, Class<?> domainClass) {
        super(message, cause);
        Assert.notNull(domainClass, "Domain class must not be null");
        this.domainClass = domainClass;
    }

    public String getDomainClassName() {
        return domainClass.getSimpleName();
    }
}
