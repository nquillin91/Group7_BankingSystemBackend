package com.group7.banking.model.nosql;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Document(collection = "names")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class NameEntity implements Serializable {
	private static final long serialVersionUID = 6225816862643566384L;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String lastName;


	@Getter
	@Setter
	private String middleName;

	@Getter
	private Long userId;

	public NameEntity(Long userId) {
        this.userId = userId;

	}
}