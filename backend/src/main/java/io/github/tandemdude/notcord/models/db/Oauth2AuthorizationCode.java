package io.github.tandemdude.notcord.models.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table(name = "oauth2_authorization_codes", schema = "notcord")
public class Oauth2AuthorizationCode implements Persistable<String> {
    @Id
    private String code = null;
    private String userId;
    private String clientId;
    private Instant expiresAt;
    private long scope;

    public Oauth2AuthorizationCode(String userId, String clientId, Instant expiresAt, long scope) {
        this.userId = userId;
        this.clientId = clientId;
        this.expiresAt = expiresAt;
        this.scope = scope;
    }

    public Oauth2AuthorizationCode(String userId, String clientId, long scope) {
        this(userId, clientId, Instant.now().plusSeconds(60 * 15), scope);
    }

    @Override
    public String getId() {
        return this.code;
    }

    @Override
    public boolean isNew() {
        var isNew = this.code == null;
        this.code = isNew ? UUID.randomUUID().toString().replace("-", "") : this.code;
        return isNew;
    }
}
