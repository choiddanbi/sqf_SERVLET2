package entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class User {
	private int userId;
	private String username;
	private String password;
	private String name;
	private String email;
	private LocalDateTime registerdate;
}
