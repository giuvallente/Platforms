package store.auth;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder @Accessors(fluent = true)
public record RegisterIn(
    String name,
    String email,
    String password
) {
    
}
