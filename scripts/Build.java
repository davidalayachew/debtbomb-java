import module dev.mccue.tools.jdk;

Path build = Path.of("out");

void main() throws Exception {
    Javac.run(args -> {
       args.__module_source_path("./*/src")
               .__module("dev.mccue.debtbomb", "dev.mccue.debtbomb.processor")
               ._g()
               ._d(build.resolve("javac"));
    });

    Jar.run(args -> {
        args.__create()
                .__file(build.resolve("jar").resolve("dev.mccue.debtbomb.jar"))
                ._C(build.resolve("javac").resolve("dev.mccue.debtbomb"), ".");
    });

    Jar.run(args -> {
        args.__create()
                .__file(build.resolve("jar").resolve("dev.mccue.debtbomb.processor.jar"))
                ._C(build.resolve("javac").resolve("dev.mccue.debtbomb.processor"), ".");
    });

    Javac.run(args -> {
        args.__processor_module_path(build.resolve("jar"))
                .__module_path(build.resolve("jar"))
                .__add_modules("ALL-MODULE-PATH")
                ._g()
                ._d(build.resolve("javac"))
                .argument(Path.of("dev.mccue.debtbomb.example", "src", "Example.java"));
    });
}