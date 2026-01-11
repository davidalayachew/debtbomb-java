package dev.mccue.debtbomb.processor;

import dev.mccue.debtbomb.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

/// Annotation processor that handles {@link dev.mccue.debtbomb.DebtBomb} annotations.
public final class DebtBombProcessor extends AbstractProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("dev.mccue.debtbomb.DebtBomb", "dev.mccue.debtbomb.DebtBombs");
    }

    private static final DateTimeFormatter EXPIRES_AT
            = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv
    ) {
        var messager = this.processingEnv.getMessager();

        Set<Element> elements = new LinkedHashSet<>(
                roundEnv.getElementsAnnotatedWith(DebtBomb.class)
        );
        elements.addAll(
                roundEnv.getElementsAnnotatedWith(DebtBombs.class)
        );
        for (var element : elements) {
            List<DebtBomb> bombs = Stream.concat(
                        Optional.ofNullable(element.getAnnotation(DebtBombs.class))
                                .stream()
                                .flatMap(debtBombs -> Arrays.stream(debtBombs.value())),
                        Optional.ofNullable(element.getAnnotation(DebtBomb.class)).stream()
                    ).toList();

            for (var bomb : bombs) {
                LocalDate expiresAt;
                try {
                    expiresAt = LocalDate.parse(bomb.expiresAt(), EXPIRES_AT);
                } catch (DateTimeParseException e) {
                    messager.printError("expiresAt should be in YYYY-MM-DD: " + bomb.expiresAt(), element);
                    continue;
                }

                var today = LocalDate.now();

                if (today.isAfter(expiresAt) || today.isEqual(expiresAt)) {
                    messager.printError("Debt Bomb! expired=" + bomb.expiresAt() +
                            (bomb.reason().isBlank() ? "" : ", reason=" + bomb.reason()) +
                            (bomb.owner().isBlank() ? "" : ", owner=" + bomb.owner()) +
                            (bomb.ticket().isBlank() ? "" : ", ticket=" + bomb.ticket()), element
                    );
                }
            }
        }

        return true;
    }
}
