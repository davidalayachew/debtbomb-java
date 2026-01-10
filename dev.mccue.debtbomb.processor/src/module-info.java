import dev.mccue.debtbomb.processor.DebtBombProcessor;

import javax.annotation.processing.Processor;

module dev.mccue.debtbomb.processor {
    requires dev.mccue.debtbomb;
    requires java.compiler;

    provides Processor with DebtBombProcessor;
}