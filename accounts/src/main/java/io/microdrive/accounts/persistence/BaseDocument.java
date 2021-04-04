package io.microdrive.accounts.persistence;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
public class BaseDocument {
    @MongoId(value = FieldType.OBJECT_ID) protected String id;
    @CreatedDate protected LocalDateTime createdAt;
    @LastModifiedDate protected LocalDateTime updatedAt;
}
