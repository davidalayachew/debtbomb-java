package dev.mccue.debtbomb;

import java.lang.annotation.*;

/// Container for {@link DebtBomb} annotations.
@Retention(RetentionPolicy.CLASS)
@Target({
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.FIELD
})
@Documented
public @interface DebtBombs {
    DebtBomb[] value();
}
