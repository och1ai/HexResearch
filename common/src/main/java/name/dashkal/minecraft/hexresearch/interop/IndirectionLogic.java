package name.dashkal.minecraft.hexresearch.interop;

import dev.architectury.platform.Platform;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Mod wrapper to handle the indirection logic for an optional dependency.
 * <p>
 * Interoperability API facades should be backed by this to wrap the actual call to classes owned by optional dependencies.
 */
public class IndirectionLogic<T> {
    private final String modId;

    private final Supplier<T> apiSupplier;

    /**
     * Constructs a new InteropLogic
     *
     * @param modId the id of the mod that owns this class.
     * @param versionPredicate a <code>Predicate</code> to use to validate the version is compatible.  If this predicate
     *                         returns <code>false</code>, the dependency is considered to be absent.
     * @param apiSupplier a <code>Supplier</code> that provides the actual API backed by the mod.
     *                    Any direct references to the optional dependency's classes should be confined to this API.
     */
    public IndirectionLogic(String modId, Predicate<String> versionPredicate, Supplier<T> apiSupplier) {
        this.modId = modId;

        if (Platform.isModLoaded(modId) && versionPredicate.test(Platform.getMod(modId).getVersion())) {
            this.apiSupplier = apiSupplier;
        } else {
            this.apiSupplier = null;
        }
    }

    /** Passes the API object to the consumer if the dependency is present. */
    public void withApi(Consumer<T> consumer) {
        if (apiSupplier != null) {
            consumer.accept(apiSupplier.get());
        }
    }

    /** Passes the API object to the function if the dependency is present.  Returns <code>ifMissing</code> if not. */
    public <A> A withApiFunc(Supplier<A> ifMissing, Function<T, A> func) {
        if (apiSupplier != null) {
            return func.apply(apiSupplier.get());
        } else {
            return ifMissing.get();
        }
    }

    /** Convenience function for withApiFunc where the function would return an Optional. */
    public <A> Optional<A> withApiFuncOpt(Function<T, Optional<A>> func) {
        return withApiFunc(Optional::empty, func);
    }

    /** Returns the mod ID of the dependency. */
    public String getModId() {
        return modId;
    }

    /** Returns <code>true</code> if the dependency is present. */
    public boolean isPresent() {
        return apiSupplier != null;
    }

    /** Predicates for use with the version checker argument to the constructor */
    public static final class Versions {
        /** Predicate that only matches an exact version */
        public static Predicate<String> exact(String version) {
            return (version::equals);
        }

        /** Predicate that matches a regular expression (as backed by java.util.regex.Pattern) */
        public static Predicate<String> regex(String pattern) {
            Pattern p = Pattern.compile(pattern);
            return (v -> p.matcher(v).matches());
        }
    }
}
