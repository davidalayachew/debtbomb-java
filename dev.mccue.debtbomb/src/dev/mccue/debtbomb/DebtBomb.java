package dev.mccue.debtbomb;

import java.lang.annotation.*;

/// Annotates code as having a TODO or temporary hack
/// that should be cleaned up after a certain point.
@Target({
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD
})
@Retention(RetentionPolicy.CLASS)
@Repeatable(DebtBombs.class)
@Documented
public @interface DebtBomb {
    /// Expiration date in `YYYY-MM-DD` format.
    String expiresAt();

    /// Context on why this debt exists.
    String reason() default "";

    /// Team or Individual responsible
    String owner() default "";

    /// Issue tracker reference (e.g., `JIRA-123`)
    String ticket() default "";
}
